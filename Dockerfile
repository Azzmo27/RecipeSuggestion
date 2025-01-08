FROM openjdk:17

WORKDIR /app

COPY target/RecipeSuggestion-0.0.1-SNAPSHOT.jar /app/recipe-suggestion.jar

# Kør JAR-filen ved containerens start
CMD ["java", "-jar", "recipe-suggestion.jar"]

EXPOSE 8080
