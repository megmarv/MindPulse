package org.project.mindpulse.SystemManagement;

import org.project.mindpulse.SystemManagement.DatabaseHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ArticleHandler {

    public void fetchArticles() {
        try (Connection connection = DatabaseHandler.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM articles")) {

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                System.out.println("Title: " + title + ", Content: " + content);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
