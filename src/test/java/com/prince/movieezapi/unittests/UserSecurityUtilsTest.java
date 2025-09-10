package com.prince.movieezapi.unittests;

import com.prince.movieezapi.shared.utilities.UserSecurityUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserSecurityUtilsTest {

    @Test
    void testIsPasswordValid() {
        assertTrue(UserSecurityUtils.isPasswordValid("Password1"));
        assertFalse(UserSecurityUtils.isPasswordValid("pass1")); // too short
        assertFalse(UserSecurityUtils.isPasswordValid("passwordwithoutnumber")); // no number
        assertFalse(UserSecurityUtils.isPasswordValid("PASSWORDWITHOUTLOWERCASE1")); // no lowercase
        assertFalse(UserSecurityUtils.isPasswordValid("password1")); // no uppercase
        assertFalse(UserSecurityUtils.isPasswordValid(null));
        assertFalse(UserSecurityUtils.isPasswordValid("P1")); // too short
        assertFalse(UserSecurityUtils.isPasswordValid("P1".repeat(13))); // too long
    }

    @Test
    void testIsEmailValid() {
        assertTrue(UserSecurityUtils.isEmailValid("test@example.com"));
        assertFalse(UserSecurityUtils.isEmailValid("invalid-email"));
        assertFalse(UserSecurityUtils.isEmailValid("test@.com"));
        assertFalse(UserSecurityUtils.isEmailValid(null));
        assertFalse(UserSecurityUtils.isEmailValid("test@domain"));
    }
}