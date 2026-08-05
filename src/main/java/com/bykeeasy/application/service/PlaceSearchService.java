package com.bykeeasy.application.service;

import com.bykeeasy.application.port.in.PlaceSearchUseCase;
import com.bykeeasy.infrastructure.adapter.in.web.PlaceDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class PlaceSearchService implements PlaceSearchUseCase {

    private final String apiKey;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public PlaceSearchService(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public List<PlaceDto> searchPlaces(String query) {
        if (query == null || query.trim().length() < 2) return new ArrayList<>();

        try {
            String encodedQuery = java.net.URLEncoder.encode(query, java.nio.charset.StandardCharsets.UTF_8);
            // Usamos SerpApi con el motor de Google Maps
            // Priorizamos Sincelejo, Colombia (@9.3047,-75.3978,14z)
            String url = "https://serpapi.com/search.json?engine=google_maps&q=" + encodedQuery +
                         "&ll=@9.3047,-75.3978,14z&hl=es&api_key=" + apiKey;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode root = objectMapper.readTree(response.body());
            JsonNode results = root.path("local_results");

            List<PlaceDto> places = new ArrayList<>();
            if (results.isArray()) {
                for (JsonNode node : results) {
                    String name = node.path("title").asText();
                    String address = node.path("address").asText();
                    double lat = node.path("gps_coordinates").path("latitude").asDouble();
                    double lon = node.path("gps_coordinates").path("longitude").asDouble();

                    places.add(new PlaceDto(name, address, lat, lon));
                }
            }
            return places;

        } catch (Exception e) {
            System.err.println("Error searching places with SerpApi: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
