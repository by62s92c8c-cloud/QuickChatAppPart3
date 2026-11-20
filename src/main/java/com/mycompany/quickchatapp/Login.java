package com.mycompany.quickchatapp;

import java.util.regex.Pattern;

public class Login {
    private String storedFirstName;
    private String storedLastName;
    private String storedUsername;
    private String storedPassword;
    private String storedCellPhone;

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[A-Za-z0-9_]{5,}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$");
    private static final Pattern CELL_PATTERN = Pattern.compile("^\\+27\\d{9}$");

    public boolean checkUserName(String username) {
        return username != null && USERNAME_PATTERN.matcher(username).matches();
    }

    public boolean checkPasswordComplexity(String password) {
        return password != null && PASSWORD_PATTERN.matcher(password).matches();
    }

    public boolean checkCellPhoneNumber(String cell) {
        return cell != null && CELL_PATTERN.matcher(cell).matches();
    }

    public String registerUser(String firstName, String lastName, String username, String password, String cellPhone) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains at least five characters and only letters, digits, or underscores.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber(cellPhone)) {
            return "Cell phone number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        }
        this.storedFirstName = firstName;
        this.storedLastName  = lastName;
        this.storedUsername  = username;
        this.storedPassword  = password;
        this.storedCellPhone = cellPhone;
        return "User registered successfully.";
    }

    public boolean loginUser(String username, String password) {
        if (storedUsername == null) {
            return "user".equals(username) && "pass".equals(password); // Demo fallback
        }
        return storedUsername.equals(username) && storedPassword.equals(password);
    }

    public String returnLoginStatus(boolean loggedIn) {
        if (loggedIn) {
            String first = storedFirstName == null ? "User" : storedFirstName;
            String last  = storedLastName  == null ? ""     : storedLastName;
            return "Welcome " + first + " " + last + ", it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }
}