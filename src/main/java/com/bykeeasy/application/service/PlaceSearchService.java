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
            // Usamos Google Places API (Text Search)
            // Priorizamos resultados cerca de Sincelejo, Colombia (aproximadamente 9.3047, -75.3978)
            String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + encodedQuery + 
                         "&location=9.3047,-75.3978&radius=10000&region=co&key=" + apiKey;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode root = objectMapper.readTree(response.body());
            JsonNode results = root.path("results");

            List<PlaceDto> places = new ArrayList<>();
            if (results.isArray()) {
                for (JsonNode node : results) {
                    String name = node.path("name").asText();
                    String address = node.path("formatted_address").asText();
                    double lat = node.path("geometry").path("location").path("lat").asDouble();
                    double lon = node.path("geometry").path("location").path("lng").asDouble();

                    places.add(new PlaceDto(name, address, lat, lon));
                }
            }
            return places;

        } catch (Exception e) {
            System.err.println("Error searching places with Google Maps: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}