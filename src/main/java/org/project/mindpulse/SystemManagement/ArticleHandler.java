package org.project.mindpulse.SystemManagement;

import org.project.mindpulse.CoreModules.Article;
import org.project.mindpulse.CoreModules.ArticleInteractions;
import org.project.mindpulse.CoreModules.Category;
import org.project.mindpulse.CoreModules.User;
import org.project.mindpulse.UserService.APIFetcher;
import org.project.mindpulse.UserService.RecommendationEngine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleHandler extends DatabaseHandler {

    // Static method to retrieve all articles and add them to the static articleList in Article class
    public static void retrieveAllArticles() {
        System.out.println("Retrieving all articles from the database...");

        String query = "SELECT * FROM articles";

        try (Connection conn = DatabaseHandler.connect();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            // Clear the existing list to avoid duplicates
            Article.articleList.clear();

            while (rs.next()) {
                int articleId = rs.getInt("articleid");
                int categoryId = rs.getInt("categoryid");
                String title = rs.getString("title");
                String authorName = rs.getString("authorname");
                String content = rs.getString("content");
                Date dateOfPublish = rs.getDate("dateofpublish");
                boolean fetched = rs.getBoolean("fetched");

                // Create a new Article object
                Article article = new Article(articleId, categoryId, title, authorName, content, dateOfPublish,fetched);

                // Add the article to the static list
                Article.articleList.add(article);

                System.out.println("Added Article: " + article);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Total articles retrieved: " + Article.articleList.size());
    }

    // Method to save an article interaction into the database
    public void saveInteractionToDatabase(ArticleInteractions interaction) {
        String query = """
        INSERT INTO ARTICLEINTERACTIONS(articleid, categoryid, userid, rating, timetaken)
        VALUES (?, ?, ?, ?, ?::interval)
    """;

        try (Connection conn = DatabaseHandler.connect();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, interaction.getArticleID());
            ps.setInt(2, interaction.getCategoryId());
            ps.setInt(3, interaction.getUserId());
            ps.setString(4, interaction.isLiked() ? "like" : interaction.isDisliked() ? "dislike" : "none");

            // Use the formatted interval string
            ps.setString(5, interaction.getTimeTakenAsInterval());

            int rowsInserted = ps.executeUpdate();
            System.out.println("Interaction recorded: " + rowsInserted + " row(s) affected.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean hasUserInteracted(int userId, int articleId) {
        String query = "SELECT COUNT(*) FROM ARTICLEINTERACTIONS WHERE userid = ? AND articleid = ?";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, userId);
            ps.setInt(2, articleId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    // If the count is greater than 0, the user has already interacted with the article
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking user interaction: " + e.getMessage());
            e.printStackTrace();
        }

        return false; // User has not interacted with the article
    }

    public boolean insertFetchedArticle(Article article, int categoryId) {
        String query = """
        INSERT INTO Articles (CategoryID, Title, AuthorName, Content, DateOfPublish, Fetched)
        VALUES (?, ?, ?, ?, ?, TRUE)
    """;

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, categoryId);
            ps.setString(2, article.getTitle());
            ps.setString(3, article.getAuthorName());
            ps.setString(4, article.getContent());
            ps.setDate(5, new java.sql.Date(article.getDateOfPublish().getTime()));

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0; // Return true if insertion is successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
