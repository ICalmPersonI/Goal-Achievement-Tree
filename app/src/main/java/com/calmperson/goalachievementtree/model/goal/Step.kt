package com.calmperson.goalachievementtree.model.goal

data class Step(val text: String, var isDone: Boolean) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Step

        if (text != other.text) return false
        if (isDone != other.isDone) return false

        return true
    }

    override fun hashCode(): Int {
        var result = text.hashCode()
        result = 31 * result + isDone.hashCode()
        return result
    }
}
