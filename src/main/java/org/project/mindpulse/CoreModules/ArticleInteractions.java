package org.project.mindpulse.CoreModules;

import java.util.ArrayList;
import java.util.List;

public class ArticleInteractions {

    private Article article; // Reference to an Article object

    private int interactionId;
    private int articleID;
    private int categoryId;
    private int userId;
    private boolean liked;
    private boolean disliked;
    private long timeTakenMillis; // Store duration in milliseconds


    public ArticleInteractions(int articleID, int interactionId, int categoryId, int userId, boolean liked, boolean disliked, long timeTakenMillis) {
        this.articleID = articleID;
        this.interactionId = interactionId;
        this.categoryId = categoryId;
        this.userId = userId;
        this.liked = liked;
        this.disliked = disliked;
        this.timeTakenMillis = timeTakenMillis;
    }

    public ArticleInteractions(int interactionId) {
        this.interactionId = interactionId;
        this.articleID = 0;
        this.liked = false;
        this.disliked = false;
        this.timeTakenMillis = 0;
        this.categoryId = 0;
        this.userId = 0;
    }

    public int getArticleID() {
        return articleID;
    }

    public void setArticleID(int articleID) {
        this.articleID = articleID;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public int getInteractionId() {
        return interactionId;
    }

    public void setInteractionId(int interactionId) {
        this.interactionId = interactionId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public boolean isDisliked() {
        return disliked;
    }

    public void setDisliked(boolean disliked) {
        this.disliked = disliked;
    }

    public long getTimeTakenMillis() {
        return timeTakenMillis;
    }

    public void setTimeTakenMillis(long timeTakenMillis) {
        this.timeTakenMillis = timeTakenMillis;
    }

    public String getTimeTakenAsInterval() {
        long seconds = timeTakenMillis / 1000;
        return seconds + " seconds";
    }
}
