FROM adoptopenjdk:8-jre-hotspot

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

EXPOSE 8002

CMD ["/app/init"]
