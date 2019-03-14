package com.softage.task.repository

import com.softage.task.models.ImageModel
import org.springframework.data.repository.CrudRepository

interface ImageModelRepository extends CrudRepository<ImageModel, Integer> {

    ImageModel findByUuid(final UUID uuid )

}
