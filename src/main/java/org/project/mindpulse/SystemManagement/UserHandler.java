package org.project.mindpulse.SystemManagement;

import org.project.mindpulse.CoreModules.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserHandler {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/MindPulse";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "aaqib2004";

    private List<User> ListOfUsersInDatabase = new ArrayList();

    public List<User> getListOfUsersInDatabase() {

        ListOfUsersInDatabase.clear(); //clear any elements already residing within the list for clarity

        String query = "SELECT * FROM Users";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // iterate through the users in the database
            while (rs.next()) {
                int userID = rs.getInt("UserID");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String username = rs.getString("username");
                String password = rs.getString("password");

                // Creating a new user object
                User user = new User(userID, name, email, username, password);
                ListOfUsersInDatabase.add(user);
            }

        } catch (SQLException e) {
            System.out.println("Error while loading users: " + e.getMessage());
            e.printStackTrace();
        }

        return ListOfUsersInDatabase;
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

}
