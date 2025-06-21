FROM openjdk:17-jdk-alpine

WORKDIR /app

# Gradle Wrapper & Configs für Layer Cache
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts settings.gradle.kts ./

RUN chmod +x ./gradlew

# Vorbereitung des Builds (Layer Cache)
RUN ./gradlew build --dry-run || true

# Quellcode kopieren
COPY src ./src

# Build ausführen (inkl. jar)
RUN ./gradlew build

# Entry Point: Starte deine CLI mit Parametern
COPY entrypoint.sh /app/entrypoint.sh
RUN chmod +x /app/entrypoint.sh
ENTRYPOINT ["/app/entrypoint.sh"]
