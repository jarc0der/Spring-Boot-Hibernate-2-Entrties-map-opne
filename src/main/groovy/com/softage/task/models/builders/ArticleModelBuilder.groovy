package com.softage.task.models.builders

import com.softage.task.models.ArticleImageModel
import com.softage.task.models.ArticleModel

class ArticleModelBuilder extends Builder<ArticleModel> {

    def images = new TreeSet<ArticleImageModel>( ( 0..2 ).collect { new ArticleImageModelBuilder().build() } )

    @Override
    ArticleModel build() {
        new ArticleModel().with {
            images.every { image ->
                it.addArticleImageModel( image )
            }

            it
        }
    }

    ArticleModelBuilder withImages(Collection<ArticleImageModel> articleImageModels) {
        images = articleImageModels
        this
    }
}
