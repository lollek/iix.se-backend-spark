package controller

import exceptions.HttpNotFound
import model.Word
import service.AccessService
import service.JsonService
import spark.Request
import spark.Response
import spark.Spark

class WordsController : ModelController() {
    companion object {

        fun register(endpointUrl: String) {
            Spark.get(endpointUrl, index, JsonService.gson::toJson)
            Spark.get("$endpointUrl/:id", show, JsonService.gson::toJson)
            Spark.post(endpointUrl, save, JsonService.gson::toJson)
            Spark.put("$endpointUrl/:id", update, JsonService.gson::toJson)
            Spark.delete("$endpointUrl/:id", delete, JsonService.gson::toJson)
        }

        val index = fun(request: Request, _: Response): List<Word>
                = AccessService.doIfLoggedIn(request,
                { -> Word.loadAll(request.queryParams("language"),
                                  request.queryParams("category")) })

        val show = fun(request: Request, _: Response): Word
                = AccessService.doIfLoggedIn(request,
                { -> Word.loadById(getId(request)) ?: throw HttpNotFound() })

        val save = fun(request: Request, response: Response): Word
                = save(Word::class.java, request, response)

        val update = fun(request: Request, response: Response): Word
                = update(Word::class.java, request, response)

        val delete = fun(request: Request, response: Response): String
                = delete(request, response, fun() = Word.deleteById(getId(request)))
    }
}
