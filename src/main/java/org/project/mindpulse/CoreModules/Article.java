package org.project.mindpulse.CoreModules;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.project.mindpulse.CoreModules.Category.getPredefinedCategories;

public class Article {

    private int articleId;
    private int categoryId;
    private String title;
    private String authorName;
    private String content;
    private Date dateOfPublish;

    private ArticleInteractions articleInteraction;

    public static List<ArticleInteractions> articleInteractions = new ArrayList<>();

    public static List<Article> articleList = new ArrayList<Article>();

    public void addInteraction(ArticleInteractions articleInteraction) {
        articleInteractions.add(articleInteraction);
        articleInteraction.setArticle(this);
    }

    public Article(int articleId){
        this.articleId = articleId;
        this.categoryId = 0;
        this.title = null;
        this.authorName = null;
        this.content = null;
        this.dateOfPublish = null;
    }

    // Constructor with all fields
    public Article(int articleId, int categoryId, String title, String authorName, String content, Date dateOfPublish) {
        this.articleId = articleId;
        this.categoryId = categoryId;
        this.title = title;
        this.authorName = authorName;
        this.content = content;
        this.dateOfPublish = dateOfPublish;
    }

    // Constructor without articleId (for inserts where ID is auto-generated)
    public Article(int categoryId, String title, String authorName, String content, Date dateOfPublish) {
        this.categoryId = categoryId;
        this.title = title;
        this.authorName = authorName;
        this.content = content;
        this.dateOfPublish = dateOfPublish;
    }

    public String getCategoryById(int id){
        for(Category category : getPredefinedCategories()){
            if (category.getCategoryID()==id){
                return category.getCategoryName();
            }

        }
        return null;
    }

    public Date getDateOfPublish() {
        return dateOfPublish;
    }

    public void setDateOfPublish(Date dateOfPublish) {
        this.dateOfPublish = dateOfPublish;
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
