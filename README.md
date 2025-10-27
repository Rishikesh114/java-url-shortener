Java Spring Boot URL Shortener
A simple, fast, and efficient back-end microservice that converts long URLs into short, easy-to-share links, built with Java and Spring Boot.

🚀 Features
Shorten: Converts any long URL into a unique 6-character short code.

Redirect: Automatically redirects any user from a short link to its original long URL.

Database: Uses an in-memory H2 database for fast, temporary storage of links.

🛠️ Technology Stack
Language: Java 17+

Framework: Spring Boot 3.x

API: Spring Web (for REST APIs)

Database: Spring Data JPA & H2 Database

Build Tool: Maven

🏁 How to Run
Clone the repository:

Bash

git clone https://github.com/your-username/java-url-shortener.git
cd java-url-shortener
Run the application using Maven:

Bash

./mvnw spring-boot:run
The server will start on http://localhost:8080.

API Endpoints
1. Create a Short URL
Sends a long URL and gets a short URL back.

Method: POST

URL: http://localhost:8080/shorten

Body (JSON):

JSON

{
  "url": "https://www.google.com/search?q=my+long+url"
}
Success Response (200 OK):

http://localhost:8080/aB3xZ
2. Redirect to Long URL
Visit the short URL in your browser.

Method: GET

URL: http://localhost:8080/{shortCode}

Example: http://localhost:8080/aB3xZ

Response: An HTTP 302 Found redirect to the original long URL.

3. View the Database (Development)
URL: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:urldb

Username: sa

Password: (leave blank)
