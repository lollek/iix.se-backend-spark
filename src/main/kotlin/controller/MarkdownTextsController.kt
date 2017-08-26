package controller

import exceptions.HttpBadRequest
import exceptions.HttpNotFound
import exceptions.HttpUnauthorized
import model.MarkdownText
import service.AccessService
import service.JsonService
import spark.Request
import spark.Response
import spark.Spark

class MarkdownTextsController : ModelController() {

    companion object {
        fun register(endpointUrl: String) {
            Spark.get("$endpointUrl/:name", show, JsonService.gson::toJson)
            Spark.put("$endpointUrl/:name", update, JsonService.gson::toJson)
        }

        private fun getName(request: Request): String {
            return request.params("name") ?: throw HttpNotFound()
        }

        val show = fun(request: Request, _: Response): MarkdownText = MarkdownText.loadByName(getName(request)) ?: throw HttpNotFound()

        val update = fun(request: Request, _: Response): MarkdownText {
            if (!AccessService.isLoggedIn(request)) {
                throw HttpUnauthorized()
            }

            val obj: MarkdownText = MarkdownText.loadByName(getName(request)) ?: throw HttpNotFound()
            val jsonObject: MarkdownText = JsonService.fromJson(request.body(), MarkdownText::class.java) ?: throw HttpBadRequest("")
            obj.data = jsonObject.data
            obj.save()

            return obj
        }
    }

}
