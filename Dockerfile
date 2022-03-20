FROM alpine
RUN apk update
RUN apk add openjdk17
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
COPY .development.env /app/.development.env
COPY database.psv /app/database.psv
ENTRYPOINT ["java","-cp","app:app/lib/*","ie.dublinanalytica.web.WebApplication"]
