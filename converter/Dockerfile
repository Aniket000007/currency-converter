FROM eclipse-temurin:17-jdk

RUN apt-get update && apt-get install -y \
    tesseract-ocr \
    wget

# ✅ manually download english traineddata
RUN mkdir -p /usr/share/tessdata && \
    wget https://github.com/tesseract-ocr/tessdata_best/raw/main/eng.traineddata \
    -O /usr/share/tessdata/eng.traineddata

RUN find /usr -name eng.traineddata

WORKDIR /app

COPY . .

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

CMD ["java", "-jar", "target/converter-0.0.1-SNAPSHOT.jar"]