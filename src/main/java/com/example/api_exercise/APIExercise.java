package com.example.api_exercise;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Locale;
import java.util.Scanner;

public class APIExercise {

    public static void main(String[] args) {
        String urlString = "https://api.weather.gov";
        URL url = null;
        HttpURLConnection conn = null;
//        create url
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

        // get response code
        int responseCode = -1;
        try {
            responseCode =  conn.getResponseCode();
        } catch(Exception e) {
            System.out.println(e);
        }
        if (responseCode != 200) {
            System.out.println("Error connecting to API, Response Code: " + responseCode);
        } else if (responseCode == -1) {
            System.out.println("Error getting response code");
        } else {
            System.out.println("Successfully connected to API...");

//          get user input
            Scanner scanner = new Scanner(System.in);
            String userInput;
            do {
                System.out.println("Enter a latitude and longitude (00.0000,00.0000), or press 'q' to quit: ");
                userInput = scanner.nextLine();
//                TODO: parse input to get lat and long as doubles
                double lat;
                double lon;
                if (lat > 90 || lat < -90 || lon > 180 || lon < -180) {
                    System.out.println("Invalid coordinates");
                } else {
//                  ensure that lat and lon are two digits and four decimal places (required by the api)
//                  https://www.geeksforgeeks.org/how-to-set-precision-for-double-values-in-java/

                    String sLat = String.format("%.4f", lat);
                    String sLon = String.format("%.4f", lon);
//                  retrieve metadata for location based on lat and lon
                    try {
                        String inline = "";
//                        TODO: create url here and give it lat and lon
                        //                TODO: api call on given coords
                        Scanner scanner = new Scanner(url.openStream());
                        while (scanner.hasNext()) {
                            inline += scanner.nextLine();
                        }
                        scanner.close();
                        System.out.println(inline);
                    } catch (Exception e){
                        System.out.println(e);
                    }

                }

//                    System.out.println(userInput);
            } while ( !(userInput.toLowerCase(Locale.ROOT).equals("q")) );
            scanner.close();
        }
    }
}
