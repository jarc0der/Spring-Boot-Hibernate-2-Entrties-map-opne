package com.softage.task.models.builders

import com.softage.task.models.ActivityModel
import com.softage.task.models.ArticleModel

class ArticleModelBuilder extends Builder<ArticleModel> {

    @Override
    ArticleModel build() {
        new ArticleModel().with {
            image = new ImageModelBuilder().build()
            it
        }
    }
}
