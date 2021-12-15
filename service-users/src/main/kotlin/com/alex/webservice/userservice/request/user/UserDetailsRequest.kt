package com.alex.webservice.userservice.request.user

import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class UserDetailsRequest(
    @NotNull(message = "First name can't be null")
    @Size(min = 2, message = "First name must be longer than two characters")
    val firstName: String,
    @NotNull(message = "Last name can't be null")
    @Size(min = 2, message = "Last name must be longer than two characters")
    val lastName: String,
    @NotNull(message = "Password can't be null")
    @Size(min = 8, max = 16, message = "Password must be longer than 8 and less than 16 characters")
    val password: String,
    @NotNull(message = "Email can't be null")
    @Email
    val email: String
)


