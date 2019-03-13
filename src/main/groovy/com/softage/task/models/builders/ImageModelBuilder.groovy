package com.softage.task.models.builders

import com.softage.task.models.ImageModel

class ImageModelBuilder extends Builder<ImageModel> {

    @Override
    ImageModel build() {
        new ImageModel().with {
            uuid = UUID.randomUUID()
            it
        }
    }
}
