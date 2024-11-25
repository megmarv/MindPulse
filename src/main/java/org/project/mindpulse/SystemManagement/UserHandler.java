package org.project.mindpulse.SystemManagement;

import org.project.mindpulse.CoreModules.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserHandler extends DatabaseHandler {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/MindPulse";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "aaqib2004";


    // Static field to hold the currently logged-in user
    private static User loggedInUser = null;

    // Getter for the logged-in user
    public static User getLoggedInUser() {
        return loggedInUser;
    }

    // Method to authenticate and log in a user
    public static boolean loginUser(String username, String password) {
        String query = "SELECT * FROM Users WHERE username = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("UserID");
                    String name = rs.getString("name");
                    String email = rs.getString("email");

                    // Set the logged-in user
                    loggedInUser = new User(userId, name, email, username, password);
                    System.out.println("User logged in: " + loggedInUser);
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error logging in user: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    // Method to log out the user
    public static void logoutUser() {
        loggedInUser = null;
        System.out.println("User logged out.");
    }

    public static boolean createNewUser(String name, String email, String username, String password) {
        // Check if the email or username already exists
        String emailQuery = "SELECT COUNT(*) FROM Users WHERE email = ?";
        String usernameQuery = "SELECT COUNT(*) FROM Users WHERE username = ?";
        String insertQuery = "INSERT INTO Users (name, email, username, password) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Check email
            try (PreparedStatement emailStmt = conn.prepareStatement(emailQuery)) {
                emailStmt.setString(1, email);
                try (ResultSet rs = emailStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        System.out.println("Error: Email already exists.");
                        return false;
                    }
                }
            }

            // Check username
            try (PreparedStatement usernameStmt = conn.prepareStatement(usernameQuery)) {
                usernameStmt.setString(1, username);
                try (ResultSet rs = usernameStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        System.out.println("Error: Username already exists.");
                        return false;
                    }
                }
            }

            // Insert new user
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setString(1, name);
                pstmt.setString(2, email);
                pstmt.setString(3, username);
                pstmt.setString(4, password);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("User successfully created!");
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public static boolean userExists(String username, String password) {
        String query = "SELECT COUNT(*) FROM Users WHERE username = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Set the query parameters
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            // Execute the query
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    // If the count is greater than 0, the user exists
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while checking if user exists: " + e.getMessage());
            e.printStackTrace();
        }

        // Return false if the user does not exist or an error occurs
        return false;
    }

    public User getUser(String username, String password) {
        String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
        User user = null;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("UserID");
                    String name = rs.getString("name");
                    String email = rs.getString("email");

                    // Create a new User object with all details
                    user = new User(userId, name, email, username, password);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving user details: " + e.getMessage());
            e.printStackTrace();
        }

        return user;
    }


}
