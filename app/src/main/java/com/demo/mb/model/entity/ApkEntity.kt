package com.demo.mb.model.entity

import android.content.pm.ApplicationInfo
import android.graphics.drawable.Drawable

data class ApkEntity(
    var name: String? = "",
    var size: String? = "",
    var path: String? = "",
    var icon: Drawable? = null,
    var info: ApplicationInfo? = null
)