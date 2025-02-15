package com.aeroclubcargo.warehouse.data.remote.dto

import com.aeroclubcargo.warehouse.domain.model.AuthenticateResponse

data class AuthenticateResponseDto(
    var id:String,
    var firstName: String?,
    var lastName : String?,
    var username : String,
    var jwtToken : String?,
    var refreshToken:String?
)

fun AuthenticateResponseDto.toAuthenticateResponse() : AuthenticateResponse{
    return AuthenticateResponse(id, firstName, lastName, username, jwtToken,refreshToken
    )
}