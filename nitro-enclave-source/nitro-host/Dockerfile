FROM amazoncorretto:21-al2023

# Create app directory
WORKDIR /app

# Copy application JAR
COPY target/asset-management-0.0.1-SNAPSHOT.jar ./app.jar

# Copy native libraries
COPY libs/libvsockj-native-1.0-SNAPSHOT.so /usr/lib/
COPY libs/nsm-cli ./nsm-cli

# Set the entrypoint
CMD ["java", "-jar", "/app/app.jar"]

