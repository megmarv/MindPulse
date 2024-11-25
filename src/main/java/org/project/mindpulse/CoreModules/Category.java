package org.project.mindpulse.CoreModules;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private String categoryName;
    private int categoryID;
    private String categoryDescription;

    // Static list to hold predefined categories
    private static final List<Category> predefinedCategories = new ArrayList<>();

    // Static block to initialize predefined categories
    static {
        predefinedCategories.add(new Category("Health", 1, "Latest information on global health"));
        predefinedCategories.add(new Category("Education", 2, "News and insights on the education sector."));
        predefinedCategories.add(new Category("Politics", 3, "Political news and discussions."));
        predefinedCategories.add(new Category("Entertainment", 4, "Movies, music, and celebrity news."));
        predefinedCategories.add(new Category("Sports", 5, "Sports news, scores, and events."));
        predefinedCategories.add(new Category("Business", 6, "Business news, trends, and economy."));
    }


    public Category(String categoryName, int categoryID, String categoryDescription) {
        this.categoryName = categoryName;
        this.categoryID = categoryID;
        this.categoryDescription = categoryDescription;
    }

    public Category(int categoryId){
        this.categoryID = categoryId;
        this.categoryName = null;
        this.categoryDescription = null;
    }

    // Getter for the predefined categories
    public static List<Category> getPredefinedCategories() {
        return predefinedCategories;
    }

    // Getters for category properties
    public String getCategoryName() { return categoryName; }
    public int getCategoryID() { return categoryID; }
    public String getCategoryDescription() { return categoryDescription; }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

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
