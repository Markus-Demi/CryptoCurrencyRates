package com.gmail.demidovich.cryptotracker.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(layout: Int): View = LayoutInflater.from(this.context)
        .inflate(layout, this, false)