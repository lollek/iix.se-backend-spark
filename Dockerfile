FROM openjdk:8-jdk

ADD build/distributions/spark-backend-0.1.tar /
RUN mv /spark-backend-0.1 /app
ADD docker-starter /app/init

RUN useradd -ms /bin/bash www
USER www

ENV IP "127.0.0.1"
ENV DBPATH ""
ENV DBUSER ""
ENV DBPASSWORD ""

CMD ["/app/init"]

EXPOSE 8002
