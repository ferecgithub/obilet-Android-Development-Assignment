package com.ferechamitebeyli.ui.util

import android.widget.ImageView
import coil.load
import com.ferechamitebeyli.ui.R

object UiHelpers {

    fun ImageView.loadPartnerLogo(partnerId: Int) {
        this.load(" https://s3.eu-central-1.amazonaws.com/static.obilet.com/images/partner/$partnerId-sm.png") {
            crossfade(true)
            placeholder(R.drawable.ic_bus)
        }
    }
}