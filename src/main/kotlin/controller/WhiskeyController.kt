package controller

import exceptions.HttpNotFound
import model.beverage.Whiskey
import service.JsonService
import spark.Request
import spark.Response
import spark.Spark

class WhiskeyController : ModelController() {
    companion object {

        fun register(endpointUrl: String) {
            Spark.get(endpointUrl, index, JsonService.gson::toJson)
            Spark.get("$endpointUrl/:id", show, JsonService.gson::toJson)
            Spark.post(endpointUrl, save, JsonService.gson::toJson)
            Spark.put("$endpointUrl/:id", update, JsonService.gson::toJson)
            Spark.delete("$endpointUrl/:id", delete, JsonService.gson::toJson)
        }

        val index = fun(_: Request, _: Response): List<Whiskey> = Whiskey.loadAll()
        val show = fun(request: Request, _: Response): Whiskey = Whiskey.loadById(getId(request)) ?: throw HttpNotFound()
        val save = fun(request: Request, response: Response): Whiskey = save(Whiskey::class.java, request, response)
        val update = fun(request: Request, response: Response): Whiskey = update(Whiskey::class.java, request, response)
        val delete = fun(request: Request, response: Response): String = delete(request, response, fun() = Whiskey.deleteById(getId(request)))
    }
}
