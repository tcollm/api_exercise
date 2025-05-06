package com.example.api_exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
                                    System.out.println("Don't forget that South and West are entered as negatives, i.e. 118.2426° W is -118.2426. (I kept forgetting that, which resulted in 404 errors from the api.");
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

                                    while (!(userInput.toLowerCase(Locale.ROOT).equals("d")) || !(userInput.toLowerCase(Locale.ROOT).equals("h"))) {
                                        System.out.println("Would you like the daily forecast (type: 'd') or the hourly forecast (type: 'h')?");
                                        userInput = uiScanner.nextLine();

//                                    get the type of forecast requested by the user
                                        if (!(userInput.toLowerCase(Locale.ROOT).equals("d")) && !(userInput.toLowerCase(Locale.ROOT).equals("h"))) {
                                            System.out.println("Error: please enter 'd' for the daily forecast or 'h' for the hourly forecast.");
                                        } else {
//                                            get daily or hourly forecast
//                                            TODO: convert this to a function (it's reused)
                                            String urlString1 = "";
                                            if (userInput.toLowerCase(Locale.ROOT).equals("d")) { // get daily forecast
                                                urlString1 = forecast;
                                            } else if (userInput.toLowerCase(Locale.ROOT).equals("h")) { // get hourly
                                                urlString1 = forecastHourly;
                                            }

                                            URL url1 = null;
                                            HttpURLConnection conn1 = null;
                                            //        create url
                                            try {
                                                //			URL is deprecated, use URI
                                                //			URL url = new URL(urlString);
                                                url1 = new URI(urlString1).toURL();
                                            } catch (Exception e) {
                                                System.out.println(e);
                                                return; // exit early if url fails (would lead to a NullPointerException on conn below)
                                            }

                                            //		create connection and read from it
                                            StringBuilder response1 = new StringBuilder();
                                            String inputLine1 = "";
                                            try {
                                                conn1 = (HttpURLConnection) url1.openConnection();
                                                BufferedReader in1 = new BufferedReader(new InputStreamReader(conn1.getInputStream()));
                                                while ((inputLine1 = in1.readLine()) != null) {
//                                              System.out.println(inputLine);
                                                    response1.append(inputLine1);
                                                }
                                                in1.close();
                                            } catch(Exception e) {
                                                System.out.println(e);
                                            }

//                                            System.out.println(response1.toString());
                                            JSONObject responseJson = new JSONObject(response1.toString());
                                            JSONObject props1 = responseJson.getJSONObject("properties");
//                                            extract "periods" as a json array
                                            JSONArray periodsArray = props1.getJSONArray("periods");

//                                            make the output pretty depending on if it's daily or hourly
                                            if (userInput.toLowerCase(Locale.ROOT).equals("d")) { // get daily forecast
//                                                for each period in periods
//                                                  get name
//                                                  temperature (with unit)
//                                                  probabilityOfPrecipitation.value
//                                                  windSpeed (with windDirection)
//                                                  detailedForecast

//                                                for each period in the periods array: get the name of the day, the temp,
//                                                  the probability of precipitation, the windSpeed, and a detailed forecast
                                                for (int i = 0; i < periodsArray.length(); i++) {
                                                    JSONObject period = periodsArray.getJSONObject(i);

//                                                    day of the week
                                                    if (!period.isNull("name")) {
                                                        System.out.println(period.getString("name"));
                                                    } else {
                                                        System.out.println("Day of the Week: N/A");
                                                    }

//                                                     temp (check for null values from api
                                                    if ( !period.isNull("temperature") ) {
                                                        if ( !period.isNull("temperatureUnit") ) {
                                                            System.out.println("Temperature: " + period.getInt("temperature") + "° " + period.getString("temperatureUnit"));
                                                        } else {
                                                            System.out.println("Temperature: " + period.getInt("temperature") + "° ");
                                                        }
                                                    } else {
                                                        System.out.println("Temperature: N/A");
                                                    }

//                                                     precipitation
                                                    if (!period.getJSONObject("probabilityOfPrecipitation").isNull("value")) {
                                                        System.out.println("Chance of precipitation: " + period.getJSONObject("probabilityOfPrecipitation").getInt("value") + "%");
                                                    } else {
                                                        System.out.println("Chance of precipitation: N/A");
                                                    }
//                                                     wind
                                                    if (!period.isNull("wind")) {
                                                        if (!period.isNull("windDirection")) {
                                                            System.out.println("Wind: " + period.getString("windSpeed") + " " + period.getString("windDirection"));
                                                        } else {
                                                            System.out.println("Wind: " + period.getString("windSpeed"));
                                                        }
                                                    } else {
                                                        System.out.println("Wind: N/A");
                                                    }

//                                                    detailed forecast
                                                    System.out.println("Forecast:");
                                                    if (!period.isNull("detailedForecast")) {
                                                        System.out.println(period.getString("detailedForecast"));
                                                    } else {
                                                        System.out.println("N/A");
                                                    }
                                                    System.out.println(); // empty space for readability
                                                }
                                            } else if (userInput.toLowerCase(Locale.ROOT).equals("h")) { // get hourly
//                                                for each period in periods
//                                                  get display start time - end time

//                                                  relativeHumidity.value
//                                                  windSpeed (with windDirection)
//                                                  shortForecast

                                                for (int i = 0; i < periodsArray.length(); i++) {
                                                    JSONObject period = periodsArray.getJSONObject(i);

//                                                    time
                                                    String startTime = "";
                                                    String endTime = "";
                                                    if (!period.isNull("startTime")) {
//                                                        TODO: ensure that startTime always follows this format
                                                        startTime = period.getString("startTime").substring(11, 16);;
                                                    } else {
                                                        startTime = "N/A";
                                                    }
                                                    if (!period.isNull("endTime")) {
                                                        endTime = period.getString("endTime").substring(11, 16);;
                                                    } else {
                                                        endTime = "N/A";
                                                    }

                                                    System.out.println(startTime + " - " + endTime);

//                                                     temp (check for null values from api
                                                    if ( !period.isNull("temperature") ) {
                                                        if ( !period.isNull("temperatureUnit") ) {
                                                            System.out.println("Temperature: " + period.getInt("temperature") + "° " + period.getString("temperatureUnit"));
                                                        } else {
                                                            System.out.println("Temperature: " + period.getInt("temperature") + "° ");
                                                        }
                                                    } else {
                                                        System.out.println("Temperature: N/A");
                                                    }

//                                                     precipitation
                                                    if (!period.getJSONObject("probabilityOfPrecipitation").isNull("value")) {
                                                        System.out.println("Chance of precipitation: " + period.getJSONObject("probabilityOfPrecipitation").getInt("value") + "%");
                                                    } else {
                                                        System.out.println("Chance of precipitation: N/A");
                                                    }

//                                                    dewpoint
                                                    if (!period.getJSONObject("dewpoint").isNull("value")) {
                                                        System.out.println("Dewpoint: " + period.getJSONObject("dewpoint").getInt("value") + "°");
                                                    } else {
                                                        System.out.println("Dewpoint: N/A");
                                                    }

//                                                     relative humidity
                                                    if (!period.getJSONObject("relativeHumidity").isNull("value")) {
                                                        System.out.println("Relative Humidity: " + period.getJSONObject("relativeHumidity").getInt("value") + "%");
                                                    } else {
                                                        System.out.println("Relative Humidity: N/A");
                                                    }

//                                                     wind
                                                    if (!period.isNull("wind")) {
                                                        if (!period.isNull("windDirection")) {
                                                            System.out.println("Wind: " + period.getString("windSpeed") + " " + period.getString("windDirection"));
                                                        } else {
                                                            System.out.println("Wind: " + period.getString("windSpeed"));
                                                        }
                                                    } else {
                                                        System.out.println("Wind: N/A");
                                                    }

//                                                    short forecast
                                                    if (!period.isNull("shortForecast")) {
                                                        System.out.println("Forecast: " + period.getString("shortForecast"));
                                                    } else {
                                                        System.out.println("N/A");
                                                    }
                                                    System.out.println(); // empty space for readability
                                                }

                                            }
                                            break;
                                        }
                                    }
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
