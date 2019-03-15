package com.softage.task.models.builders

import com.softage.task.models.ArticleImageModel

class ArticleImageModelBuilder extends Builder<ArticleImageModel> {

    @Override
    ArticleImageModel build() {
        new ArticleImageModel().with {
            image = new ImageModelBuilder().build()
            it
        }
    }
}
