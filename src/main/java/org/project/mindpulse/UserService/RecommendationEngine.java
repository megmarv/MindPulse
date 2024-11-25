package org.project.mindpulse.UserService;

import org.project.mindpulse.CoreModules.Article;
import org.project.mindpulse.CoreModules.Category;
import org.project.mindpulse.CoreModules.User;
import org.project.mindpulse.SystemManagement.DatabaseHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecommendationEngine extends DatabaseHandler {

    private static final int FETCH_THRESHOLD = 5; // Number of articles read before fetching new ones

    /**
     * Fetch recommended articles for the user.
     *
     * @param user The user for whom we want to fetch recommended articles.
     * @return A list of recommended articles.
     */
    public List<Article> getRecommendedArticlesForUser(User user) {
        List<Article> recommendedArticles = new ArrayList<>();

        // Get the most favorite category
        Category mostFavouriteCategory = getMostFavouriteCategory(user);
        if (mostFavouriteCategory == null) {
            System.out.println("No favorite category found for the user.");
            return recommendedArticles;
        }

        String categoryName = mostFavouriteCategory.getCategoryName();
        if (categoryName == null || categoryName.isBlank()) {
            System.out.println("Invalid category name for fetching articles.");
            return recommendedArticles;
        }

        int categoryId = mostFavouriteCategory.getCategoryId();

        // Check if enough articles are available for the category
        int unreadArticleCount = getUnreadArticleCount(user, categoryId);
        System.out.println("Unread articles for category: " + categoryName + " = " + unreadArticleCount);

        if (unreadArticleCount < FETCH_THRESHOLD) {
            System.out.println("Fetching more articles for category: " + categoryName);
            APIFetcher apiFetcher = new APIFetcher();
            apiFetcher.fetchArticles(categoryName); // Fetch articles for the category
        }

        // Fetch recommended articles for the user
        recommendedArticles = getArticlesForCategory(user, categoryId);
        return recommendedArticles;
    }


    /**
     * Get the most favorite category based on user interactions.
     *
     * @param user The user for whom we determine the favorite category.
     * @return The most favorite category.
     */
    public Category getMostFavouriteCategory(User user) {
        String query = """
        SELECT c.CategoryID, c.CategoryName, COUNT(ai.CategoryID) AS like_count
        FROM ARTICLEINTERACTIONS ai
        JOIN Categories c ON ai.CategoryID = c.CategoryID
        WHERE ai.UserID = ? AND ai.Rating = 'like'
        GROUP BY c.CategoryID, c.CategoryName
        ORDER BY like_count DESC
        LIMIT 1
    """;

        try (Connection conn = DatabaseHandler.connect();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, user.getUserId());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int categoryId = rs.getInt("CategoryID");
                    String categoryName = rs.getString("CategoryName");
                    return new Category(categoryId, categoryName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // No favorite category found
    }


    /**
     * Get the count of unread articles for a category.
     *
     * @param user      The user for whom we check unread articles.
     * @param categoryId The category ID to check.
     * @return The count of unread articles.
     */
    private int getUnreadArticleCount(User user, int categoryId) {
        String query = """
            SELECT COUNT(*)
            FROM Articles a
            LEFT JOIN ARTICLEINTERACTIONS ai
            ON a.ArticleID = ai.ArticleID AND ai.UserID = ?
            WHERE a.CategoryID = ? AND a.Fetched = TRUE AND ai.ArticleID IS NULL
        """;

        try (Connection conn = DatabaseHandler.connect();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, user.getUserId());
            ps.setInt(2, categoryId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Default to 0 if no unread articles found
    }

    /**
     * Fetch articles for a specific category.
     *
     * @param user      The user for whom we fetch articles.
     * @param categoryId The category ID to fetch articles for.
     * @return A list of articles for the category.
     */
    private List<Article> getArticlesForCategory(User user, int categoryId) {
        List<Article> articles = new ArrayList<>();

        String query = """
            SELECT * FROM Articles a
            LEFT JOIN ARTICLEINTERACTIONS ai
            ON a.ArticleID = ai.ArticleID AND ai.UserID = ?
            WHERE a.CategoryID = ? AND a.Fetched = TRUE AND ai.ArticleID IS NULL
            ORDER BY a.DateOfPublish DESC
            LIMIT 10
        """;

        try (Connection conn = DatabaseHandler.connect();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, user.getUserId());
            ps.setInt(2, categoryId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int articleId = rs.getInt("ArticleID");
                    String title = rs.getString("Title");
                    String author = rs.getString("AuthorName");
                    String content = rs.getString("Content");
                    Date dateOfPublish = rs.getDate("DateOfPublish");

                    articles.add(new Article(articleId, categoryId, title, author, content, dateOfPublish));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return articles;
    }
}
