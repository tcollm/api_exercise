package com.example.api_exercise;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Locale;
import java.util.Scanner;

public class APIExercise {
    String urlString = "https://api.weather.gov";
    URL url = null;
    HttpURLConnection conn = null;

//    establish connection in constructor
    APIExercise() {
//      create url
        try {
//			URL is deprecated, use URI
//			URL url = new URL(urlString);
            url = new URI(urlString).toURL();
        } catch (Exception e) {
            System.out.println(e);
            return; // exit early if url fails (would lead to a NullPointerException on conn below)
        }

//		create connection
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    //	for testing connection
    int getResponseCode() {
        int responseCode = -1;
        try {
            responseCode =  conn.getResponseCode();
        } catch(Exception e) {
            System.out.println(e);
        }
        return responseCode;
    }

    void getForecast() {

    }

    public static void main(String[] args) {
        APIExercise exercise = new APIExercise();
//        exercise.getResponseCode(); // for testing
        int rCode = exercise.getResponseCode();
        if (rCode != 200) {
            System.out.println("Error connecting to API, Response Code: " + rCode);
        } else if (rCode == -1) {
            System.out.println("Error getting response code");
        } else {
            System.out.println("Successfully connected to API...");

//          get user input
            Scanner scanner = new Scanner(System.in);
            String userInput;
            do {
                System.out.println("Enter a state (q to quit): ");
                userInput = scanner.nextLine();
//                TODO: format user input to ensure that states are correctly formatted
//                TODO: api call on given state
                System.out.println(userInput);
            } while ( !(userInput.toLowerCase(Locale.ROOT).equals("q")) );
            scanner.close();
        }
    }
}
