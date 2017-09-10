FROM openjdk:8-jdk

ADD build/distributions/spark-backend-0.1.tar /
RUN mv /spark-backend-0.1 /app
ADD docker-starter /app/init

RUN useradd -ms /bin/bash www
USER www

ENV DBPATH ""
ENV DBUSER ""
ENV DBPASSWORD ""
ENV GOODREADS_KEY ""
ENV JWT ""

CMD ["/app/init"]

EXPOSE 8002
