package com.rishi.url_shortener.model;

import jakarta.persistence.*; // This is the new package for JPA in Spring Boot 3
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is an Entity. It's a blueprint for the "url_mappings" table in our H2 database.
 * Each object of this class will be one row in that table.
 */
@Entity // Tells JPA: "Please manage this class and create a table for it."
@Table(name = "url_mappings") // Specifies the actual table name.
@Getter // Lombok: Automatically creates all getter methods (e.g., getLongUrl())
@Setter // Lombok: Automatically creates all setter methods (e.g., setLongUrl())
@NoArgsConstructor // Lombok: Automatically creates an empty constructor: public UrlMapping() {}
public class UrlMapping {

    /**
     * This is the Primary Key. It's a unique number that identifies each row.
     */
    @Id // Marks this field as the Primary Key.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tells the database to automatically generate this number
                                                        // (e.g., 1, 2, 3, ...) when a new row is added.
    private Long id;

    /**
     * This is the short code we generate, like "aB3xZ".
     * We make it 'unique' so we can't have two rows with the same short code.
     */
    @Column(nullable = false, unique = true) // 'nullable = false' means this column cannot be empty.
                                             // 'unique = true' enforces that no two rows can have the same value.
    private String shortCode;

    /**
     * This is the original, long URL we are shortening.
     * We give it a generous length, as URLs can be very long.
     */
    @Column(nullable = false, length = 2048) // 'length' sets the max size of the string.
    private String longUrl;

    // We create a custom constructor to make it easier to create new objects.
    public UrlMapping(String shortCode, String longUrl) {
        this.shortCode = shortCode;
        this.longUrl = longUrl;
    }
}