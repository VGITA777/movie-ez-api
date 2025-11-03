package com.prince.movieezapi.shared.utilities;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class UserSecurityUtils {

    public static int MIN_PASSWORD_LENGTH = 8;
    public static int MAX_PASSWORD_LENGTH = 24;

    public static boolean isPasswordValid(String password) {
        if (password == null || password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
            return false;
        }
        // At least one upper-case character, one lower-case character, and one number
        return password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).*$");
    }

    public static boolean isEmailValid(String email) {
        return email != null && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }
}
