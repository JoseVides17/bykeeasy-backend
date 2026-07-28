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

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<PlaceDto> searchPlaces(String query) {
        if (query == null || query.trim().length() < 2) return new ArrayList<>();

        try {
            // Buscamos priorizando Colombia y limitando a 5 resultados
            String encodedQuery = java.net.URLEncoder.encode(query + " Sincelejo", java.nio.charset.StandardCharsets.UTF_8);
            String url = "https://nominatim.openstreetmap.org/search?q=" + encodedQuery + "&format=json&addressdetails=1&limit=5&countrycodes=co";

            // ⚠️ IMPORTANTE: Nominatim exige un encabezado 'User-Agent' personalizado para no bloquear la petición
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("User-Agent", "BykEasyApp/1.0 (contacto@bykeeasy.com)")
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode results = objectMapper.readTree(response.body());

            List<PlaceDto> places = new ArrayList<>();
            if (results.isArray()) {
                for (JsonNode node : results) {
                    // Extraemos los nombres y coordenadas
                    String name = node.path("name").asText();
                    if (name.isEmpty()) {
                        name = node.path("display_name").asText().split(",")[0];
                    }
                    String address = node.path("display_name").asText();
                    double lat = node.path("lat").asDouble();
                    double lon = node.path("lon").asDouble();

                    places.add(new PlaceDto(name, address, lat, lon));
                }
            }
            return places;

        } catch (Exception e) {
            System.err.println("Error searching places with Nominatim: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}