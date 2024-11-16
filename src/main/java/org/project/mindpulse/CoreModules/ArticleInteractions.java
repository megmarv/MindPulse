package org.project.mindpulse.CoreModules;

import java.sql.Time;

public class ArticleInteractions {
    private Article article;  // Reference to an Article object
    private int interactionId;
    private int categoryId;
    private int userId;
    private boolean liked;
    private boolean disliked;
    private Time timeTaken;

    public ArticleInteractions(Article article, int interactionId, int categoryId, int userId, boolean liked, boolean disliked, Time timeTaken) {
        this.article = article;
        this.interactionId = interactionId;
        this.categoryId = categoryId;
        this.userId = userId;
        this.liked = liked;
        this.disliked = disliked;
        this.timeTaken = timeTaken;
    }

    public ArticleInteractions(int interactionId) {
        this.interactionId = interactionId;
        this.article = null;
        this.liked = false;
        this.disliked = false;
        this.timeTaken = null;
        this.categoryId = 0;
        this.userId = 0;
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

    public Time getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(Time timeTaken) {
        this.timeTaken = timeTaken;
    }
}
