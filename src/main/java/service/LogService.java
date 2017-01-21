package service;

import org.apache.log4j.Logger;
import spark.Request;
import spark.Response;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LogService {

    private final static Logger logger = Logger.getLogger(LogService.class);

    public static void logAccess(Request request, Response response) {
        final String requestBody = request.body().replaceAll("\"password\":\".*?\"", "\"password\":\"<removed>\"");

        logger.info(String.format("%s %s %s %s %s [%s]",
                request.ip(), request.protocol(), request.requestMethod(), request.url(), response.status(), requestBody));
    }

    public static void logException(Exception exception) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        logger.warn(sw.toString());
    }
}
