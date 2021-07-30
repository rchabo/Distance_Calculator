package com.base;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class Main {

    private static final String HEROKU_URL = "https://bpdts-test-app.herokuapp.com/";

    public static void main(String[] args) {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(HEROKU_URL + RequestStrings.USERS)).build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(Main::parse)
                .join();
    }

    public static void parse(String response){
        Type personListType = new TypeToken<ArrayList<Person>>(){}.getType();
        ArrayList<Person> people = new Gson().fromJson(response, personListType);
        people.forEach(Person::printInfo);
    }
}
