package com.calmperson.goalachievementtree.model.source.local.goal

import androidx.room.TypeConverter
import com.calmperson.goalachievementtree.model.goal.Step

class StepsListConverter {

    companion object {
        private const val STORAGE_STRING_TEMPLATE = "%s|%s"
    }

    @TypeConverter
    fun toStorageString(steps: List<Step>): String {
        return steps.joinToString(";") {
            STORAGE_STRING_TEMPLATE.format(it.text, it.isDone.toString())
        }
    }

    @TypeConverter
    fun toStep(storageString: String): List<Step> {
        return storageString.split(';').map {
            with(it.split('|')) { Step(first(), last().toBoolean()) }
        }
    }

}