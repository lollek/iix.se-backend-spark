package exceptions

import org.eclipse.jetty.http.HttpStatus

abstract class HttpStatusException(
        val httpStatus: Int,
        message: String
) : Exception(message)

class HttpBadRequest(message: String) : HttpStatusException(HttpStatus.BAD_REQUEST_400, message)
class HttpUnauthorized : HttpStatusException(HttpStatus.UNAUTHORIZED_401, "")
class HttpForbidden(message: String) : HttpStatusException(HttpStatus.FORBIDDEN_403, message)
class HttpNotFound: HttpStatusException(HttpStatus.NOT_FOUND_404, "")
class HttpInternalServerError: HttpStatusException(HttpStatus.INTERNAL_SERVER_ERROR_500, "")
