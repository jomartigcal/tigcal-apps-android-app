package com.tigcal.apps

import androidx.annotation.DrawableRes

class App {
    var name: String? = null
    @DrawableRes
    var icon: Int = 0
    var link: String? = null
    var packageName = ""
    var isAndroid = false
    var isInstalled = false
}
