package com.mycompany.quickchatapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {
//test file for login validation
    @Test
    public void testValidUsername() {
        Login login = new Login();
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    public void testInvalidUsername() {
        Login login = new Login();
        assertFalse(login.checkUserName("kyle!!!!!!!")); // Invalid chars
        assertFalse(login.checkUserName("kyle"));        // Too short
    }

    @Test
    public void testValidPassword() {
        Login login = new Login();
        assertTrue(login.checkPasswordComplexity("Ch&sec@ke99!"));
    }

    @Test
    public void testInvalidPassword() {
        Login login = new Login();
        assertFalse(login.checkPasswordComplexity("password")); // No caps, no num, no special
    }

    @Test
    public void testValidCellPhone() {
        Login login = new Login();
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testInvalidCellPhone() {
        Login login = new Login();
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }

    @Test
    public void testSuccessfulRegistrationAndLogin() {
        Login login = new Login();
        String response = login.registerUser("Kyle", "Smith", "kyl_1", "Ch&sec@ke99!", "+27838968976");
        assertEquals("User registered successfully.", response);
        
        assertTrue(login.loginUser("kyl_1", "Ch&sec@ke99!"));
    }
}