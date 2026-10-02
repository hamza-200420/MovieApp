package com.example.movieapp.data.remote.mapper

import com.example.movieapp.data.remote.dto.UserDto
import com.example.movieapp.domain.model.User
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser.toDto(): UserDto {
    return UserDto(
        uid = uid,
        email = email,
        displayName = displayName
    )
}

fun UserDto.toDomain(): User {
    return User(
        uid = uid,
        email = email.orEmpty(),
        displayName = displayName
    )
}