package com.rishi.url_shortener.controller;

import com.rishi.url_shortener.services.UrlShortenerServices;
import jakarta.servlet.http.HttpServletRequest; // Used to get the base URL
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController // This combines @Controller and @ResponseBody.
                // It tells Spring this class will handle HTTP requests and send back JSON responses.
public class UrlShortenerController {

    // The Controller needs the Service to do the actual work.
    private final UrlShortenerServices urlShortenerService;

    // We use Constructor Injection again.
    public UrlShortenerController(UrlShortenerServices urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    /**
     * This endpoint is for CREATING a new short URL.
     * It listens for POST requests to http://localhost:8080/shorten
     */
    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody Map<String, String> requestBody, HttpServletRequest request) {
        // @RequestBody Map<String, String> requestBody:
        // This tells Spring to expect a JSON object in the request body,
        // like {"url": "https://..."}, and turn it into a Java Map.

        String longUrl = requestBody.get("url"); // Get the value of the "url" key from the JSON.
        if (longUrl == null) {
            return ResponseEntity.badRequest().body("Missing 'url' in request body");
        }

        // 1. Call the service to do the work.
        String shortCode = urlShortenerService.shortenUrl(longUrl);

        // 2. Build the full, clickable short URL to send back to the user.
        // We use HttpServletRequest to get the server's base URL (e.g., "http://localhost:8080")
        String baseUrl = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        String fullShortUrl = baseUrl + "/" + shortCode;

        // 3. Send the full URL back as the response.
        return ResponseEntity.ok(fullShortUrl);
    }

    /**
     * This endpoint is for REDIRECTING to the original long URL.
     * It listens for GET requests to http://localhost:8080/{shortCode}
     * (e.g., http://localhost:8080/aB3xZ)
     */
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToLongUrl(@PathVariable String shortCode) {
        // @PathVariable String shortCode:
        // This tells Spring that the "{shortCode}" part of the URL
        // should be passed into this method as the 'shortCode' variable.

        try {
            // 1. Call the service to get the long URL.
            String longUrl = urlShortenerService.getLongUrl(shortCode);

            // 2. If successful, we send a "302 Found" redirect response.
            // The browser will see this and automatically navigate to the 'Location' URL.
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(longUrl))
                    .build();

        } catch (RuntimeException e) {
            // 3. If the service threw an exception (because the code wasn't found),
            // we send back a "404 Not Found" response.
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
