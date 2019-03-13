import com.softage.task.Application
import com.softage.task.models.builders.ActivityModelBuilder
import com.softage.task.models.builders.ArticleModelBuilder
import com.softage.task.models.builders.ImageModelBuilder
import com.softage.task.repository.ActivityModelRepository
import com.softage.task.repository.ArticleModelRepository
import com.softage.task.repository.ImageModelRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootContextLoader
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@ContextConfiguration( classes = Application, loader = SpringBootContextLoader )
@SpringBootTest
class SimpleIntegrationTest extends Specification{

    @Autowired
    private ArticleModelRepository articleModelRepository

    @Autowired
    private ActivityModelRepository activityModelRepository

    @Autowired
    private ImageModelRepository imageModelRepository

    def 'simpleTest'() {

        given:
        def model = new ArticleModelBuilder().build()

        when:
        def result = articleModelRepository.save( model )

        then:
        model == result
    }

    def 'simpleTest remove'() {

        given:
        def model = new ArticleModelBuilder().build()

        when:
        def result = articleModelRepository.save( model )

        and:
        result.image = null

        then:
        articleModelRepository.save( result )

        and:
        def updated = articleModelRepository.findById( model.id ).orElse( null )
        updated.image == null
    }

    @Transactional
    def 'test with last parent'() {

        given:
        def image = new ImageModelBuilder().build()

        and:
        def article = new ArticleModelBuilder().build().with { it.image = image; it }
        def activity = new ActivityModelBuilder().build().with { it.image = image; it }

        when:
        def savedActivity = activityModelRepository.save( activity )
        def savedArticle = articleModelRepository.save( article )

        then:
        savedActivity == activity
        savedArticle == article

        and:
        articleModelRepository.delete( article )

        and:
        def storedActivity = activityModelRepository.findById( savedActivity.id ).orElse( null )
        storedActivity == savedActivity

        and:
        activityModelRepository.delete( storedActivity )

        and:
        !imageModelRepository.findAll().find{ it.uuid == image.uuid }
    }


}
