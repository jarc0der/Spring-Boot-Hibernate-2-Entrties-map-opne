package com.softage.task.repository

import com.softage.task.models.ArticleModel
import org.springframework.data.repository.CrudRepository

interface ArticleModelRepository extends CrudRepository<ArticleModel, Long> {
}
