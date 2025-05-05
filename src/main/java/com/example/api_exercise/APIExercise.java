package com.example.api_exercise;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Locale;
import java.util.Scanner;

public class APIExercise {

    public static void main(String[] args) {

//      get user input
        Scanner uiScanner = new Scanner(System.in);
        String userInput = "";
        while (!(userInput.toLowerCase(Locale.ROOT).equals("q"))) {
            System.out.println("Enter a latitude and longitude (00.0000,00.0000), or press 'q' to quit: ");
            userInput = uiScanner.nextLine();
            double lat = 0;
            double lon = 0;

//            quit if user presses 'q'
            if ((userInput.toLowerCase(Locale.ROOT).equals("q"))) {
                System.out.println("quitting...");
                return;
            } else {
                String[] parts = userInput.trim().split("[,\\s]+"); // parse ui based on space or ,
                if (parts.length == 2) {
                    //          parse input to get lat and long as doubles
                    try {
                        lat = Double.parseDouble(parts[0]);
                        lon = Double.parseDouble(parts[1]);
//                        System.out.println("Latitude: " + lat + ", Longitude: " + lon);
                    } catch (NumberFormatException e) {
                        System.out.println("Latitude or longitude is invalid");
                    }
                } else {
                    System.out.println("Invalid input, please enter a latitude and longitude number");
                }
            }

//            after parsing, ensure that lat and lon are valid
            if (lat > 90 || lat < -90 || lon > 180 || lon < -180) {
                System.out.println("Invalid coordinates");
            } else {
//                  ensure that lat and lon are two digits and four decimal places (required by the api)
//                  https://www.geeksforgeeks.org/how-to-set-precision-for-double-values-in-java/
                String sLat = String.format("%.4f", lat);
                String sLon = String.format("%.4f", lon);

//                connect to api, given lat and lon
                String urlString = "https://api.weather.gov/points/" + sLat + "," + sLon;
                URL url = null;
                URLConnection conn = null;
        //        create url
                try {
        //			URL is deprecated, use URI
        //			URL url = new URL(urlString);
                    url = new URI(urlString).toURL();
                } catch (Exception e) {
                    System.out.println(e);
                    return; // exit early if url fails (would lead to a NullPointerException on conn below)
                }

        //		create connection and read from it
                try {
                    conn = url.openConnection();
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        System.out.println(inputLine);
                    }
                    in.close();
                } catch(Exception e) {
                    System.out.println(e);
                }
//                get the properties object from the api

//                then get the forecast and forecastHourly from properties

                try {
                    String inline = "";
                    Scanner scanner = new Scanner(url.openStream());
                    while (scanner.hasNext()) {
                        inline += scanner.nextLine();
                    }
                    scanner.close();
                    System.out.println(inline);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
//        } while ( !(userInput.toLowerCase(Locale.ROOT).equals("q")) );
        uiScanner.close();
//
//
//
//
//        // get response code
//        int responseCode = -1;
//        try {
//            responseCode =  conn.getResponseCode();
//        } catch(Exception e) {
//            System.out.println(e);
//        }
//        if (responseCode != 200) {
//            System.out.println("Error connecting to API, Response Code: " + responseCode);
//        } else if (responseCode == -1) {
//            System.out.println("Error getting response code");
//        } else {
//            System.out.println("Successfully connected to API...");
//
//
////
//        }
    }
}
