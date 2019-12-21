package com.example.truthordare.json

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.truthordare.api.JsonModel

@BindingAdapter("jsonId")
fun TextView.setJsonId(item : JsonModel) {
    text = item.id
}

@BindingAdapter("jsonUserId")
fun TextView.setUserId(item : JsonModel){
    text=item.userId
}


@BindingAdapter("jsonTitle")
fun TextView.jsonTitle(item : JsonModel){
    text=item.title
}

@BindingAdapter("jsonBody")
fun TextView.jsonBody(item : JsonModel){
    text=item.body
}
