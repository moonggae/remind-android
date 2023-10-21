package com.ccc.remind.domain.entity.mind

data class MindCardTag(
    val id: Long,
    val indicator: Double?,
    val tag: MindTag
) {
    val displayName: String
        get() = when {
            indicator != null && (tag.name == "energy" || tag.name == "pleasantness") -> {
                if (indicator.compareTo(0) > 0) {
                    "${tag.displayName} ↑"
                } else if (indicator.compareTo(0) < 0) {
                    "${tag.displayName} ↓"
                } else {
                    tag.displayName
                }
            }
            else -> this.tag.displayName
        }

}