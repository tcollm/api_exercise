package com.example.api_exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Locale;
import java.util.Scanner;
import org.json.*;

public class APIExercise {

    public static void main(String[] args) {

//      get user input
        Scanner uiScanner = new Scanner(System.in);
        String userInput = "";
        while (!(userInput.toLowerCase(Locale.ROOT).equals("q"))) {
            System.out.println("Enter a latitude and longitude (00.0000,00.0000), or press 'q' to quit: ");
            userInput = uiScanner.nextLine();
            double lat = 91;
            double lon = 181;

//            quit if user presses 'q'
            if ((userInput.toLowerCase(Locale.ROOT).equals("q"))) {
                System.out.println("quitting...");
                return;
            } else {
                String[] parts = userInput.trim().split("[,\\s]+"); // parse ui based on space or ,
                if ((parts.length == 2)) {
                    //          parse input to get lat and long as doubles
                    try {
                        lat = Double.parseDouble(parts[0]);
                        lon = Double.parseDouble(parts[1]);
                        if (lat > 90 || lat < -90 || lon > 180 || lon < -180) {
                            System.out.println("Invalid coordinates. Please try again.");
//                            continue;
                        } else {
//                            ensure that lat and lon are two digits and four decimal places (required by the api)
//                  https://www.geeksforgeeks.org/how-to-set-precision-for-double-values-in-java/
                            String sLat = String.format("%.4f", lat);
                            String sLon = String.format("%.4f", lon);

//                connect to api, given lat and lon
                            String urlString = "https://api.weather.gov/points/" + sLat + "," + sLon;
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

                            //		create connection and read from it
                            StringBuilder response = new StringBuilder();
                            String inputLine = "";
                            try {
                                conn = (HttpURLConnection) url.openConnection();
                                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                while ((inputLine = in.readLine()) != null) {
//                        System.out.println(inputLine);
                                    response.append(inputLine);
                                }
                                in.close();
                            } catch(Exception e) {
                                System.out.println(e);
                            }

                            try {
                                if (conn.getResponseCode() != 200) {
                                    System.out.println(conn.getResponseMessage());
                                } else {
                                    //                convert input to json
                                    String forecast = "";
                                    String forecastHourly = "";
                                    try {
                                        JSONObject json = new JSONObject(response.toString());

//                                      get properties
                                        JSONObject props = json.getJSONObject("properties");
//                                         get forecast and forecastHourly from properties
                                        forecast = props.getString("forecast");
                                        forecastHourly = props.getString("forecastHourly");

                //                    System.out.println(forecast);
                //                    System.out.println(forecastHourly);
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }


                                    System.out.println("Would you like the daily forecast (type: 'd') or the hourly forecast (type: 'h')?");
                                    userInput = uiScanner.nextLine();
                                }
                            } catch (IOException e) {
                                System.out.println(e);
                            }
                        }
//                        System.out.println("Latitude: " + lat + ", Longitude: " + lon);
                    } catch (NumberFormatException e) {
                        System.out.println("Latitude or longitude is invalid");
                        continue;
                    }

                } else {
                    System.out.println("Invalid input, please enter a latitude and longitude number");
                }
            }
        }
        uiScanner.close();
    }
}
