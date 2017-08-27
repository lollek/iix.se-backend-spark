package controller

import exceptions.HttpNotFound
import model.beverage.Wine
import service.JsonService
import spark.Request
import spark.Response
import spark.Spark

class WineController : ModelController() {
    companion object {

        fun register(endpointUrl: String) {
            Spark.get(endpointUrl, index, JsonService.gson::toJson)
            Spark.get("$endpointUrl/:id", show, JsonService.gson::toJson)
            Spark.post(endpointUrl, save, JsonService.gson::toJson)
            Spark.put("$endpointUrl/:id", update, JsonService.gson::toJson)
            Spark.delete("$endpointUrl/:id", delete, JsonService.gson::toJson)
        }

        val index = fun(_: Request, _: Response): List<Wine> = Wine.loadAll()
        val show = fun(request: Request, _: Response): Wine = Wine.loadById(getId(request)) ?: throw HttpNotFound()
        val save = fun(request: Request, response: Response): Wine = save(Wine::class.java, request, response)
        val update = fun(request: Request, response: Response): Wine = update(Wine::class.java, request, response)
        val delete = fun(request: Request, response: Response): String = delete(request, response, fun() = Wine.deleteById(getId(request)))
    }
}
