package org.project.mindpulse.UserService;

import java.net.http.*;
import java.net.URI;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ArticleFetcher {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3); // For 3 APIs

        Runnable fetchFromAPI1 = () -> fetchArticles("https://api.source1.com");
        Runnable fetchFromAPI2 = () -> fetchArticles("https://api.source2.com");
        Runnable fetchFromAPI3 = () -> fetchArticles("https://api.source3.com");

        executor.submit(fetchFromAPI1);
        executor.submit(fetchFromAPI2);
        executor.submit(fetchFromAPI3);

        executor.shutdown();
    }

    private static void fetchArticles(String apiUrl) {
        // Same HTTP request logic as before
        System.out.println("Fetching from: " + apiUrl);
    }
}
