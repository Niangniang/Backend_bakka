package org.bakka.userservice.RecodDtos;

public record ClientPasswordUpdate(String otp , String newPassword , String confirmPassword) {
}
