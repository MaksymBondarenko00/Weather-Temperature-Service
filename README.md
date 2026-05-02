# Weather Temperature Service

## About the project

This project is a backend service that retrieves the current temperature for a given city and classifies it into a temperature category.

The application is designed to run as an AWS Lambda function (deployment will be added later). At this stage, the business logic is fully implemented and can be executed locally.

---

## Application flow

The application follows a layered architecture:

Handler → Service → Provider → Geocoding → Classifier


- **Handler** – entry point (AWS Lambda), responsible only for orchestration
- **Service** – contains business logic and coordinates components
- **Provider** – retrieves weather data from an external API
- **Geocoding Service** – converts city name into coordinates
- **Classifier** – determines temperature category

---

## Used APIs

- **Geocoding**: :contentReference[oaicite:0]{index=0}  
  Used to convert city names into latitude and longitude.

- **Weather data**: :contentReference[oaicite:1]{index=1}  
  Used to retrieve current temperature based on coordinates.

---

## Temperature classification

The temperature is categorized based on the following rules:

| Temperature (°C) | Category   |
|----------------|-----------|
| < 0            | Freezing  |
| 0–10           | Cold      |
| 10–20          | Mild      |
| 20–30          | Warm      |
| > 30           | Hot       |

---

## Design decisions

- **Separation of concerns**  
  Business logic is separated from external API communication and from the Lambda handler.

- **Extensibility via abstraction**  
  A `WeatherProvider` interface is used to allow easy replacement or addition of other weather providers.

- **External API isolation**  
  API-specific DTOs are separated from internal response models to avoid tight coupling.

- **Minimal DTO mapping**  
  Only required fields from external APIs are mapped, making the system more resilient to API changes.

- **Input safety**  
  City names are URL-encoded before sending requests to external APIs.

---

## Unit testing approach

The application is designed to be testable without calling real external APIs.

This can be achieved by:

- mocking the `WeatherProvider` interface
- mocking the `GeocodingService`
- testing `WeatherService` independently

Example approach:

- provide a mock `WeatherProvider` that returns a fixed temperature
- verify that classification logic works correctly
- verify that service returns expected response

---

## Project status

 Business logic implemented  
 AWS Lambda integration – in progress

---

## Documentation

The `doc/` directory will contain:

- AWS Lambda creation confirmation
- Sample Lambda execution results
- Example requests and responses

(Will be added after AWS deployment)