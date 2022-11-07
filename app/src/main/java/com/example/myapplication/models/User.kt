package com.example.myapplication.models

import java.util.Date

data class User(
    var id: Int,
    var email: String,
    var password: String,
    var age: Int,
    var firstname: String,
    var lastname: String,
    var role: String,
    var statut: Boolean,
    var is_verified: Int,
    var avatar: String,
    var createdAt: Date,
    var updatedAt: Date

)
