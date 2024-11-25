package org.project.mindpulse.Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.project.mindpulse.CoreModules.Article;
import org.project.mindpulse.CoreModules.ArticleInteractions;
import org.project.mindpulse.CoreModules.User;
import org.project.mindpulse.SystemManagement.ArticleHandler;
import org.project.mindpulse.SystemManagement.UserHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomeController extends ArticleHandler implements GeneralFeatures{

    @FXML private Label contentHeader;
    @FXML private WebView webview;
    @FXML private Button refreshButton;

    @FXML private Button sports;
    @FXML private Button entertainment;
    @FXML private Button business;
    @FXML private Button politics;
    @FXML private Button health;
    @FXML private Button education;

    @FXML private Button home; // Added button for Home category
    @FXML private Button profile;

    private int currentArticleIndex = 0; // To track the current article
    private List<Article> filteredArticles = new ArrayList<>(); // Temporary list for filtered articles

    @FXML
    public void initialize() {
        // Retrieve all articles from the database when the controller is initialized
        retrieveAllArticles();
    }

    @FXML
    public void handleCategorySelection(ActionEvent event) {
        Button selectedButton = (Button) event.getSource();
        String categoryText = selectedButton.getText().toLowerCase();

        // Map the button text to category IDs or "recommended" for the Home button
        int categoryId = categoryText.equals("home") ? 0 : getCategoryIdByName(categoryText);

        if (categoryId != -1) {
            // Filter the articles by the selected category or fetch recommended articles for Home
            if (categoryId == 0) {
                filterRecommendedArticles(); // Home category logic for recommended articles
            } else {
                filterArticlesByCategory(categoryId);
            }

            if (filteredArticles.isEmpty()) {
                contentHeader.setText("No articles found for " + categoryText);
                webview.getEngine().loadContent("<html><body><p>No content available</p></body></html>");
            } else {
                displayArticle(filteredArticles.get(currentArticleIndex)); // Display first article
            }
        }
    }

    private int getCategoryIdByName(String categoryName) {
        switch (categoryName) {
            case "sports":
                return 5;
            case "entertainment":
                return 4;
            case "education":
                return 2;
            case "politics":
                return 3;
            case "health":
                return 1;
            case "business":
                return 6;
            default:
                return -1; // Invalid category
        }
    }

    @FXML
    private void filterArticlesByCategory(int categoryId) {
        // Clear the previous filtered articles and filter by categoryId
        filteredArticles.clear();
        for (Article article : Article.articleList) {
            if (article.getCategoryId() == categoryId) {
                filteredArticles.add(article);
            }
        }

        currentArticleIndex = 0; // Reset to the first article
    }

    @FXML
    private void filterRecommendedArticles() {
        // For now, we'll display some random articles as "recommended"
        filteredArticles.clear();
        for (Article article : Article.articleList) {
            // Assuming we are selecting articles based on some logic for recommendations
            // Here we just use a simple condition for the demonstration (e.g., top 5 articles)
            if (filteredArticles.size() < 5) {
                filteredArticles.add(article);
            }
        }
        currentArticleIndex = 0; // Start from the first recommended article
    }

    @FXML private Button thumbsUp;  // Thumbs Up button
    @FXML private Button thumbsDown; // Thumbs Down button

    // Handle Thumbs Up action
    @FXML
    private void handleThumbsUp(ActionEvent event) {
        System.out.println("Thumbs Up clicked for article: " + filteredArticles.get(currentArticleIndex).getTitle());

        // Disable Thumbs Down button to ensure only one choice is made
        thumbsDown.setDisable(true);

        // Optionally, you can log this choice or save it in the database
        saveUserFeedback(filteredArticles.get(currentArticleIndex), true);
    }

    // Handle Thumbs Down action
    @FXML
    private void handleThumbsDown(ActionEvent event) {
        System.out.println("Thumbs Down clicked for article: " + filteredArticles.get(currentArticleIndex).getTitle());

        // Disable Thumbs Up button to ensure only one choice is made
        thumbsUp.setDisable(true);

        // Optionally, you can log this choice or save it in the database
        saveUserFeedback(filteredArticles.get(currentArticleIndex), false);
    }

    // Save the user's feedback (optional)
    private void saveUserFeedback(Article article, boolean liked) {
        // This is a placeholder method where you can save the feedback
        // Example: Save to a database or a list in memory
        String feedback = liked ? "Liked" : "Disliked";
        System.out.println("Feedback for article '" + article.getTitle() + "': " + feedback);

        // Example: Save to the database (not implemented here)
        // database.saveFeedback(article.getArticleId(), liked);
    }

    // Reset thumbs buttons when a new article is displayed
    private void resetThumbsButtons() {
        thumbsUp.setDisable(false);
        thumbsDown.setDisable(false);
    }

    // Update displayArticle to reset buttons
    @FXML
    private void displayArticle(Article article) {
        int userId = UserHandler.getLoggedInUser().getUserId();

        // Check if the user has already interacted with this article
        if (hasUserInteracted(userId, article.getArticleId())) {
            System.out.println("User has already interacted with this article. Skipping...");
            // Move to the next article or show a message
            return;
        }

        // If the user hasn't interacted with the article, display it
        contentHeader.setText(article.getCategoryById(article.getCategoryId()));

        URL cssUrl = getClass().getResource("/org/project/mindpulse/articleStyles.css");

        // HTML content with CSS link
        String articleContent = "<html><head>" +
                "<link rel='stylesheet' type='text/css' href='" + cssUrl.toExternalForm() + "' />" +
                "</head><body>" +
                "<h1>" + article.getTitle() + "</h1>" +
                "<p><strong>Author :</strong> " + article.getAuthorName() + "</p>" +
                "<p><strong>Published on :</strong> " + article.getDateOfPublish() + "</p>" +
                "<p class='content'>" + article.getContent() + "</p>" +
                "</body></html>";

        // Load content into WebView
        webview.getEngine().loadContent(articleContent);
    }

    @FXML
    private void loadNextArticle() {
        int userId = UserHandler.getLoggedInUser().getUserId();

        // Filter out already interacted articles
        List<Article> availableArticles = new ArrayList<>();
        for (Article article : Article.articleList) {
            if (!hasUserInteracted(userId, article.getArticleId())) {
                availableArticles.add(article);
            }
        }

        // If there are any available articles left, display the next one
        if (!availableArticles.isEmpty()) {
            currentArticleIndex = (currentArticleIndex + 1) % availableArticles.size();
            displayArticle(availableArticles.get(currentArticleIndex));
        } else {
            System.out.println("No more articles available for this user.");
        }
    }


    @FXML
    public void refreshArticle(ActionEvent event) {
        if (!filteredArticles.isEmpty()) {
            // Record interaction for the current article
            Article currentArticle = filteredArticles.get(currentArticleIndex);
            recordInteraction(currentArticle);

            // Move to the next article in the filtered list
            currentArticleIndex = (currentArticleIndex + 1) % filteredArticles.size(); // Loop back to the first article if at the end
            displayArticle(filteredArticles.get(currentArticleIndex)); // Display next article

            // Reset thumbs buttons for the new article
            resetThumbsButtons();
        }
    }

    private void recordInteraction(Article article) {
        // Determine the interaction type (default to 'none')
        String rating = "none";
        if (thumbsUp.isDisabled() && !thumbsDown.isDisabled()) {
            rating = "like";
        } else if (thumbsDown.isDisabled() && !thumbsUp.isDisabled()) {
            rating = "dislike";
        }

        // Get the logged-in user
        User loggedInUser = UserHandler.getLoggedInUser();
        if (loggedInUser == null) {
            System.out.println("No user logged in. Interaction not recorded.");
            return; // Exit if no user is logged in
        }

        // Ensure the user hasn't already interacted with this article
        if (hasUserInteracted(loggedInUser.getUserId(), article.getArticleId())) {
            System.out.println("User has already interacted with this article.");
            return;
        }

        // Capture the time taken in milliseconds
        long timeTakenMillis = System.currentTimeMillis() % 100000; // Example duration

        // Prepare the interaction object
        ArticleInteractions interaction = new ArticleInteractions(
                article.getArticleId(),
                0, // Interaction ID (auto-generated in DB)
                article.getCategoryId(),
                loggedInUser.getUserId(),
                rating.equals("like"),
                rating.equals("dislike"),
                timeTakenMillis
        );

        // Log interaction details for debugging
        System.out.println("Recording interaction: " + interaction);

        // Save the interaction to the database
        saveInteractionToDatabase(interaction);
    }

    @FXML
    public void displayConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    public void displayError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    public void Exit(ActionEvent exit) throws IOException{

        Platform.exit();

    }

    @FXML
    public void visitProfile(ActionEvent event) throws IOException {

        UserProfileController userProfileController = new UserProfileController();
        userProfileController.redirectToProfile(event);

    }
}
