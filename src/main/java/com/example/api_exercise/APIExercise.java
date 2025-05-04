package com.example.api_exercise;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

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
    void getResponseCode() {
        try {
            int responseCode = conn.getResponseCode();
            System.out.println(responseCode);
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    void getForecast() {

    }

    public static void main(String[] args) {
        APIExercise exercise = new APIExercise();
        exercise.getResponseCode();
    }
}
