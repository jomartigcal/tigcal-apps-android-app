package com.tigcal.apps

import androidx.annotation.DrawableRes

data class App(
        var name: String? = null,
        @DrawableRes
        var icon: Int = 0,
        var description: String = "",
        var link: String = "",
        var packageName: String = "",
        var isAndroid: Boolean = false,
        var isInstalled: Boolean = false
)
