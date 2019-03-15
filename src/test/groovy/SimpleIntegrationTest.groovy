import com.softage.task.Application
import com.softage.task.models.ArticleImageModel
import com.softage.task.models.builders.ActivityImageModelBuilder
import com.softage.task.models.builders.ArticleImageModelBuilder
import com.softage.task.models.builders.ArticleModelBuilder
import com.softage.task.models.builders.ImageModelBuilder
import com.softage.task.repository.ActivityImageModelRepository
import com.softage.task.repository.ArticleImageModelRepository
import com.softage.task.repository.ArticleModelRepository
import com.softage.task.repository.ImageModelRepository
import org.junit.Ignore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootContextLoader
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@ContextConfiguration( classes = Application, loader = SpringBootContextLoader )
@SpringBootTest
@Transactional
class SimpleIntegrationTest extends Specification {

    @Autowired
    private ArticleImageModelRepository articleImageRepository

    @Autowired
    private ActivityImageModelRepository activityImageRepository

    @Autowired
    private ArticleModelRepository articleRepository

    @Autowired
    private ImageModelRepository imageModelRepository

    @spock.lang.Ignore
    def 'exercise saving a article'() {

        given: 'valid article'
        def model = new ArticleImageModelBuilder().build()

        when:
        def result = articleImageRepository.save( model )

        then:
        model == result
    }

    /**
     * We have article as parent, image as child.
     * When we remove parent the child should be removed as well, as it's the last parent.
     */
    @spock.lang.Ignore
    def 'exercise removing image from the article - image should be removed'() {

        given: 'a valid article model'
        def model = new ArticleImageModelBuilder().build()

        when: 'save model'
        def savedArticle = articleImageRepository.save( model )

        and: 'remove image from the article'
        def imageUUID = model.image.uuid
        savedArticle.image = null

        then: 'update article'
        articleImageRepository.save( savedArticle )

        and: 'check if we have correct article'
        def articleModel = articleImageRepository.findById( model.id ).orElse( null )
        articleModel.image == null

        and: 'image removed with the last parent'
        def image = imageModelRepository.findByUuid( imageUUID )
        !image
    }

    /**
     * We have article and activity as parents, image as child. Flow ->
     * 1. Remove article, image should exists as it has activity parent.
     * 2. Remove activity, image should be removed as it has't any parents.
     */

    def 'exercise all parents are removed - image should be removed as well'() {

        given: 'a valid image model'
        def image = new ImageModelBuilder().build()

        and: 'a valid article image model'
        def articleImageModel = new ArticleImageModelBuilder().build().with { it.image = image; it }

        and: 'a valid article and activity'
        def article = new ArticleModelBuilder().withImages( [articleImageModel] ).build()
        def activity = new ActivityImageModelBuilder().build().with { it.image = image; it }

        when: 'save models'
        def savedActivity = activityImageRepository.save( activity )
        def savedArticle = articleRepository.save( article )

        then: 'validate'
        savedActivity == activity
        savedArticle == article

        and: 'remove article at first'
//        articleImageRepository.delete( article )
//        articleRepository.save( article.with { articleImages = null; it } )

        articleRepository.save( article.with { articleImages = null; it } )

        !article.articleImages

//        articleRepository.save( savedArticle )

        and: 'image should exists as we have 1 parent( activity )'
        imageModelRepository.findByUuid( image.uuid )

        and: 'remove activity'
        activityImageRepository.delete( savedActivity )

        and: 'double check if it was removed'
        !activityImageRepository.findById( savedActivity.id ).isPresent()

        and: 'last parent was removed, image should not exist anymore'
        !imageModelRepository.findByUuid( image.uuid )
    }

    /**
     * We have 2 parents, and 2 images.
     * Let's reassign an image from the article to the activity.
     * It means activity image has't parent so it should be removed.
     *
     */
    @spock.lang.Ignore
    def 'exercise updating when 2 parents, one of them reassign to another image'() {

        given: 'a valid article and activity'
        def article = new ArticleImageModelBuilder().build()
        def activity = new ActivityImageModelBuilder().build()

        and: 'store activity image to use it data'
        def activityImage = activity.image

        and: 'save article and activity'
        def savedArticle = articleImageRepository.save( article )
        def savedActivity = activityImageRepository.save( activity )

        and: 'two images are in the db'
        def images = [savedActivity.image, savedArticle.image]
        images.each { img ->
            imageModelRepository.findByUuid( img.uuid )
        }

        when: 'change image in the activity'
        savedActivity.image = article.image
        def updateActivity = activityImageRepository.save( savedActivity )

        then: 'article image belongs to activity too'
        updateActivity.image == savedArticle.image

        and: 'activity image does not exist'
        !imageModelRepository.findByUuid( activityImage.uuid )
    }

}
