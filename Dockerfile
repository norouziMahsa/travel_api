FROM adoptopenjdk/openjdk12:latest
COPY ./build/libs/travel-api-0.1.0.jar /usr/app/
WORKDIR /usr/app
RUN sh -c 'touch travel-api-0.1.0.jar'
ENTRYPOINT ["java","-jar","travel-api-0.1.0.jar"]