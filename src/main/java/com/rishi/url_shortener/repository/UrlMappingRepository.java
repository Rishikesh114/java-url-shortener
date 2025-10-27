package com.rishi.url_shortener.repository;

import com.rishi.url_shortener.model.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This is the gatekeeper for our 'UrlMapping' data.
 * We extend JpaRepository, which gives us a ton of free methods like:
 * save(), findById(), findAll(), delete(), etc.
 */
@Repository // Tells Spring this is a Repository (a bean to be managed)
public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
    // <UrlMapping, Long> means "This repository manages the 'UrlMapping' entity,
    // and that entity's primary key (@Id) is of type 'Long'."

    /**
     * This is a custom "finder" method.
     * Because we named it 'findByShortCode', Spring Data JPA is smart enough
     * to automatically understand this means:
     * "Give me the UrlMapping row WHERE the 'shortCode' column matches the string I provide."
     *
     * We use Optional<> because the code might not exist, and Optional is a safe
     * way to handle a value that might be null.
     */
    Optional<UrlMapping> findByShortCode(String shortCode);
}
