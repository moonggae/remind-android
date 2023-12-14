package com.ccc.remind.presentation.util.extensions.domain

import com.ccc.remind.domain.entity.mind.ImageFile
import com.ccc.remind.presentation.util.Constants

val ImageFile.url
    get() = "${Constants.BASE_URL}/image/${this.id}"