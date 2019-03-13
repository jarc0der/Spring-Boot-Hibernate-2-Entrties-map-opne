package com.softage.task.models.builders

import com.softage.task.models.ActivityModel

class ActivityModelBuilder extends Builder<ActivityModel> {

    @Override
    ActivityModel build() {
        new ActivityModel().with {
            image = new ImageModelBuilder().build()
            it
        }
    }
}
