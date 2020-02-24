package com.xxxxx.sampleapplicationtmdb.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.xxxxx.sampleapplicationtmdb.BR
import com.xxxxx.sampleapplicationtmdb.data.*


@BindingAdapter("android:src")
fun setImageUrl(view: ImageView, url: String?) {
    if (url != null) {
        Glide.with(view.context).load(API_IMAGE_URL + url).into(view)
    }
}
@BindingAdapter("app:isVisible")
fun isVisible(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("app:production_companies")
fun setProductionCompanies(parent: LinearLayout, productionCompanies: List<ProductionCompanies>?) {
    productionCompanies?.let {
        for (i in it.indices) {
            val tv = TextView(parent.context)
            tv.text = it[i].name + ", " + it[i].originCountry
            parent.addView(tv)
        }
    }
}
@BindingAdapter("app:genres")
fun setGenres(parent: LinearLayout, genres: List<Genres>?) {
    genres?.let {
        for (i in it.indices) {
            val tv = TextView(parent.context)
            tv.text = it[i].name
            parent.addView(tv)
        }
    }
}
@BindingAdapter("app:spoken_languages")
fun setSpokenLanguages(parent: LinearLayout, spokenLanguages: List<SpokenLanguages>?) {
    spokenLanguages?.let {
        for (i in it.indices) {
            val tv = TextView(parent.context)
            tv.text = it[i].name
            parent.addView(tv)
        }
    }
}
@BindingAdapter("app:production_countries")
fun setProductionCountries(parent: LinearLayout, productionCountries: List<ProductionCountries>?) {
    productionCountries?.let {
        for (i in it.indices) {
            val tv = TextView(parent.context)
            tv.text = it[i].name
            parent.addView(tv)
        }
    }
}