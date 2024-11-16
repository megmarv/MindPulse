package org.project.mindpulse.CoreModules;


public class Article {

    private int articleId;
    private int categoryId;
    private String title;
    private String authorName;
    private String content;

    public Article(int articleId){
        this.articleId = articleId;
    }

    // Constructor with all fields
    public Article(int articleId, int categoryId, String title, String authorName, String content) {
        this.articleId = articleId;
        this.categoryId = categoryId;
        this.title = title;
        this.authorName = authorName;
        this.content = content;
    }

    // Constructor without articleId (for inserts where ID is auto-generated)
    public Article(int categoryId, String title, String authorName, String content) {
        this.categoryId = categoryId;
        this.title = title;
        this.authorName = authorName;
        this.content = content;
    }


    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // Optional: Override toString for easy debugging
    @Override
    public String toString() {
        return "Article{" +
                "articleId=" + articleId +
                ", categoryId=" + categoryId +
                ", title='" + title + '\'' +
                ", authorName='" + authorName + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
