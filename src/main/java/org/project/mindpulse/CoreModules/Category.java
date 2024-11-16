package org.project.mindpulse.CoreModules;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private String categoryName;
    private String categoryID;
    private String categoryDescription;

    // Static list to hold predefined categories
    private static final List<Category> predefinedCategories = new ArrayList<>();

    // Static block to initialize predefined categories
    static {
        predefinedCategories.add(new Category("Artificial Intelligence", "1", "All about AI advancements and trends."));
        predefinedCategories.add(new Category("Stock Market", "2", "Market trends, stocks, and trading insights."));
        predefinedCategories.add(new Category("Health", "3", "Health tips, news, and medical research."));
        predefinedCategories.add(new Category("Automobile", "4", "Cars, bikes, and everything automotive."));
        predefinedCategories.add(new Category("Education", "5", "News and insights on the education sector."));
        predefinedCategories.add(new Category("Politics", "6", "Political news and discussions."));
        predefinedCategories.add(new Category("Entertainment", "7", "Movies, music, and celebrity news."));
        predefinedCategories.add(new Category("Sports", "8", "Sports news, scores, and events."));
        predefinedCategories.add(new Category("Business", "9", "Business news, trends, and economy."));
    }

    // Constructor is private to prevent direct instantiation
    private Category(String categoryName, String categoryID, String categoryDescription) {
        this.categoryName = categoryName;
        this.categoryID = categoryID;
        this.categoryDescription = categoryDescription;
    }

    // Getter for the predefined categories
    public static List<Category> getPredefinedCategories() {
        return predefinedCategories;
    }

    // Getters for category properties
    public String getCategoryName() { return categoryName; }
    public String getCategoryID() { return categoryID; }
    public String getCategoryDescription() { return categoryDescription; }

    // Optional: Override toString for easy debugging
    @Override
    public String toString() {
        return "Category{" +
                "categoryName='" + categoryName + '\'' +
                ", categoryID='" + categoryID + '\'' +
                ", categoryDescription='" + categoryDescription + '\'' +
                '}';
    }
}
