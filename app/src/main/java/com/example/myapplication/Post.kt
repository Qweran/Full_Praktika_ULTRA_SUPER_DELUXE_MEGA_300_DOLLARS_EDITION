package com.example.myapplication
data class Post(
    val id: Int,
    val author:String,
    val content:String,
    val published:String,
    val likeByMe:Boolean,
    var likeCount:Int,
    var repByMe:Boolean,
    var repCount:Int,
    var link:String

)