package com.softage.task.models.builders

import com.softage.task.models.ActivityImageModel

class ActivityImageModelBuilder extends Builder<ActivityImageModel> {

    @Override
    ActivityImageModel build() {
        new ActivityImageModel().with {
            image = new ImageModelBuilder().build()
            it
        }
    }
}
