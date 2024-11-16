package org.project.mindpulse.UserService;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ArticleParser {
    public static void main(String[] args) {
        String jsonResponse = "{...}"; // Replace with actual API response

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);

        System.out.println("Status: " + jsonObject.get("status").getAsString());
        System.out.println("Articles: " + jsonObject.get("articles").toString());
    }
}
