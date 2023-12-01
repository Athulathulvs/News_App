package com.athulvs987.myapplication.ui

import android.widget.ImageView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.athulvs987.myapplication.R

fun ImageView.loadImage(url: String){
    this.load(url) {
        crossfade(true)
        placeholder(R.mipmap.ic_launcher)
        transformations(RoundedCornersTransformation(10f))
    }
}