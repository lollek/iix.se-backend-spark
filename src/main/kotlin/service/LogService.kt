package service

import org.apache.log4j.Logger
import spark.Request
import spark.Response

import java.io.PrintWriter
import java.io.StringWriter

class LogService {
    companion object {
        val logger: Logger = Logger.getLogger(LogService::class.java)
        val passwordRe: Regex = Regex("\"password\"\\s*:\\s*\".*?\"")

        fun logAccess(request: Request, response: Response) {
            val requestBody: String = request.body().replace(passwordRe, "<password removed>")

            logger.info(String.format("%s %s %s %s %s [%s]",
                    request.ip(), request.protocol(), request.requestMethod(), request.url(), response.status(), requestBody))
        }

        fun logException(exception: Exception) {
            try {
                val sw: StringWriter = StringWriter()
                val pw: PrintWriter = PrintWriter(sw)
                exception.printStackTrace(pw)
                logger.warn(sw.toString())
            } catch (e: Exception) {
                logger.error("Failed to write exception!", e)
            }
        }
    }
}
