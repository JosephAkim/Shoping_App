package com.kim.shopingapp.data
//data of what the user need to have
data class User(
    val firstName : String,
    val lastName: String,
    val email: String,
    val imagePath: String = ""
){
    //fireBase will use this constructor
    constructor() : this("", "", "", "")
}
