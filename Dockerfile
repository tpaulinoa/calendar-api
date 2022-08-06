#Build image
FROM adoptopenjdk/openjdk11:debian-slim as BUILD_IMAGE

ENV APP_HOME /app

WORKDIR $APP_HOME

# Gradle
COPY gradlew $APP_HOME
COPY gradle $APP_HOME/gradle
COPY build.gradle settings.gradle $APP_HOME/
RUN ./gradlew --version

# Build
COPY src $APP_HOME/src
RUN $APP_HOME/gradlew --no-daemon clean build -x test

#Run image
FROM adoptopenjdk/openjdk11:debian-slim AS RUN_IMAGE

ENV APP_HOME /app
WORKDIR $APP_HOME

COPY --from=BUILD_IMAGE $APP_HOME/build/libs/app-0.0.1-SNAPSHOT.jar app.jar
