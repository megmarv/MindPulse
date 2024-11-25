package org.project.mindpulse.UserService;

import org.json.JSONArray;
import org.json.JSONObject;
import org.project.mindpulse.CoreModules.Article;
import org.project.mindpulse.SystemManagement.ArticleHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class APIFetcher extends ArticleHandler {

    private static final String API_KEY = "7e39c5f09e2f4e31a6d1328bcb3cf902"; // Replace with your API key
    private static final String NEWS_API_URL = "https://newsapi.org/v2/everything?q=";

    /**
     * Fetches articles from the NewsAPI based on a keyword.
     *
     * @param keyword The keyword to search for articles.
     * @return A list of fetched articles.
     */
    public List<Article> fetchArticles(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            System.out.println("Cannot fetch articles. Keyword is null or empty.");
            return new ArrayList<>(); // Return empty list
        }

        List<Article> articles = new ArrayList<>();
        int categoryId = getCategoryIdByKeyword(keyword);

        if (categoryId == -1) {
            System.out.println("Invalid category. Skipping fetch.");
            return articles; // No valid category, skip fetching
        }

        int page = 1; // Start pagination

        try {
            while (articles.size() < 10) { // Stop once 10 articles are fetched
                String apiUrl = NEWS_API_URL + keyword + "&pageSize=10&page=" + page + "&apiKey=" + API_KEY;

                HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder responseBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        responseBuilder.append(line);
                    }

                    JSONObject responseJson = new JSONObject(responseBuilder.toString());
                    JSONArray articlesJsonArray = responseJson.getJSONArray("articles");

                    if (articlesJsonArray.isEmpty()) {
                        break; // Stop if no articles are returned
                    }

                    for (int i = 0; i < articlesJsonArray.length(); i++) {
                        JSONObject articleJson = articlesJsonArray.getJSONObject(i);
                        String title = articleJson.getString("title");
                        String author = articleJson.optString("author", "Unknown Author");

                        // Try to fetch longer content
                        String content = articleJson.optString("content", "");
                        String description = articleJson.optString("description", "");

                        // Combine content and description for better article preview
                        String fullContent = content.length() > description.length() ? content : description;
                        if (fullContent.contains("[+")) {
                            fullContent = fullContent.replaceAll("\\[\\+.*?\\]", ""); // Remove [+...] suffix
                        }

                        // Extend the content if the API provides a URL for the article
                        String url = articleJson.optString("url", ""); // Use the URL field if full content is needed

                        // Use `url` for scraping longer content if necessary (not implemented here)

                        String publishedAt = articleJson.getString("publishedAt");
                        Date dateOfPublish = Date.valueOf(publishedAt.substring(0, 10));

                        Article article = new Article(0, categoryId, title, author, fullContent, dateOfPublish);
                        insertFetchedArticle(article, categoryId);
                        articles.add(article);
                    }
                }

                page++; // Increment the page for the next batch
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error fetching articles from NewsAPI: " + e.getMessage());
        }

        return articles;
    }



    private int getCategoryIdByKeyword(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return -1; // Invalid keyword
        }

        switch (keyword.toLowerCase()) {
            case "sports":
                return 5;
            case "business":
                return 6;
            case "entertainment":
                return 4;
            case "health":
                return 1;
            case "education":
                return 2;
            case "politics":
                return 3;
            default:
                System.out.println("No category found for keyword: " + keyword);
                return -1; // Invalid category
        }
    }

}
