package com.prince.movieezapi.user.inputs;

public record UpdatePasswordInput(String oldPassword, String newPassword) {
}
