package org.project.mindpulse.SystemManagement;

import java.sql.*;

public class DatabaseHandler {
    private static final String URL = "jdbc:postgresql://localhost:5432/MindPulse";
    private static final String USER = "postgres";
    private static final String PASSWORD = "aaqib2004";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void createTables() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            // Create Users table if it doesn't exist
            String createUserTable = """
            CREATE TABLE IF NOT EXISTS Users (
                UserID SERIAL PRIMARY KEY,
                name VARCHAR(20) NOT NULL,
                email VARCHAR(50),
                username VARCHAR(20),
                password VARCHAR(30)
            );
        """;
            stmt.execute(createUserTable);

            // Create Categories table if it doesn't exist
            String createCategoryTable = """
            CREATE TABLE IF NOT EXISTS Categories (
                CategoryID SERIAL PRIMARY KEY,
                CategoryName VARCHAR(30),
                Description VARCHAR(100)
            );
        """;
            stmt.execute(createCategoryTable);

            // Create Articles table if it doesn't exist
            String createArticlesTable = """
            CREATE TABLE IF NOT EXISTS Articles (
                            ArticleID SERIAL PRIMARY KEY,
                            CategoryID INT,
                            Title VARCHAR(100),
                            AuthorName VARCHAR(100),
                            content TEXT,
            				DateOfPublish Date,
                            FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID)
                        );
        """;
            stmt.execute(createArticlesTable);

            // Create ArticleInteractions table if it doesn't exist
            String createArticleInteractionsTable = """
            CREATE TABLE IF NOT EXISTS ArticleInteractions (
                InteractionID SERIAL PRIMARY KEY,
                ArticleID INT,
                UserID INT,
                Rating VARCHAR(10) CHECK (Rating IN ('like', 'dislike')),
                TimeTaken INTERVAL,
                FOREIGN KEY (ArticleID) REFERENCES Articles(ArticleID),
                FOREIGN KEY (UserID) REFERENCES Users(UserID)
            );
        """;
            stmt.execute(createArticleInteractionsTable);

            System.out.println("Tables created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}