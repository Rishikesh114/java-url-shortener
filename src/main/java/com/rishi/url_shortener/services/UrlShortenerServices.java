package com.rishi.url_shortener.services;

import com.rishi.url_shortener.model.UrlMapping;
import com.rishi.url_shortener.repository.UrlMappingRepository;
import org.apache.commons.lang3.RandomStringUtils; // The library we just added
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service // Tells Spring this is a Service (a bean to be managed)
public class UrlShortenerServices {

    // We need our repository (the gatekeeper) to talk to the database.
    private final UrlMappingRepository urlMappingRepository;

    /**
     * This is "Constructor Injection".
     * We are telling Spring: "When you create this UrlShortenerService,
     * you MUST provide (inject) an instance of UrlMappingRepository."
     * Spring will automatically find the repository bean it created and pass it in.
     */
    public UrlShortenerServices(UrlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
    }

    /**
     * This method handles the logic of creating and saving a short URL.
     */
    public String shortenUrl(String longUrl) {
        String shortCode;

        // We need to make sure the code is unique.
        while (true) {
            // 1. Generate a 6-character random alphanumeric string (e.g., "aB3xZ9")
            shortCode = RandomStringUtils.randomAlphanumeric(6);

            // 2. Check the database (via our repository) to see if this code is already used.
            Optional<UrlMapping> existing = urlMappingRepository.findByShortCode(shortCode);

            // 3. If it's NOT present (.isEmpty()), this code is unique! We can break the loop.
            if (existing.isEmpty()) {
                break;
            }
            // 4. If it IS present, the loop continues and generates a new code to try again.
        }

        // 5. We have a unique code. Let's create our new data object.
        UrlMapping urlMapping = new UrlMapping(shortCode, longUrl);

        // 6. Save the new object to the database using our repository.
        urlMappingRepository.save(urlMapping);

        // 7. Return the new short code to the user.
        return shortCode;
    }

    /**
     * This method handles the logic of finding the original long URL.
     */
    public String getLongUrl(String shortCode) {
        // 1. Ask the repository to find the mapping by its short code.
        Optional<UrlMapping> urlMappingOptional = urlMappingRepository.findByShortCode(shortCode);

        // 2. Check if the Optional actually contains a result.
        if (urlMappingOptional.isPresent()) {
            // 3. It exists! Get the UrlMapping object...
            UrlMapping urlMapping = urlMappingOptional.get();
            // 4. ...and return its longUrl.
            return urlMapping.getLongUrl();
        } else {
            // 5. It does not exist. Throw an exception so the Controller (next step)
            // knows that this code is not valid.
            throw new RuntimeException("Short URL not found: " + shortCode);
        }
    }
}
