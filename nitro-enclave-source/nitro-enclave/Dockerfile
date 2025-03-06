FROM amazoncorretto:21

WORKDIR /app

COPY target/rfq-enclave-client-0.0.1-SNAPSHOT.jar ./app.jar

COPY libs/libvsockj-native-1.0-SNAPSHOT.so /usr/lib/
COPY libs/libnsm.so /usr/lib/

RUN ldconfig  # Ensure shared libraries are recognized

COPY libs/nsm-cli ./nsm-cli
COPY libs/kmstool_enclave_cli ./kmstool_enclave_cli

CMD ["java", "-jar", "/app/app.jar"]
