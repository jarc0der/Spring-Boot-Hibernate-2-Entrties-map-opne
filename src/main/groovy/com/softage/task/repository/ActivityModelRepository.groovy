package com.softage.task.repository

import com.softage.task.models.ActivityModel
import org.springframework.data.repository.CrudRepository

interface ActivityModelRepository extends CrudRepository<ActivityModel, Long> {
}
