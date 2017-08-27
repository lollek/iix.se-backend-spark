package controller

import exceptions.HttpNotFound
import model.beverage.Sake
import service.JsonService
import spark.Request
import spark.Response
import spark.Spark

class SakeController : ModelController() {
    companion object {

        fun register(endpointUrl: String) {
            Spark.get(endpointUrl, index, JsonService.gson::toJson)
            Spark.get("$endpointUrl/:id", show, JsonService.gson::toJson)
            Spark.post(endpointUrl, save, JsonService.gson::toJson)
            Spark.put("$endpointUrl/:id", update, JsonService.gson::toJson)
            Spark.delete("$endpointUrl/:id", delete, JsonService.gson::toJson)
        }

        val index = fun(_: Request, _: Response): List<Sake> = Sake.loadAll()
        val show = fun(request: Request, _: Response): Sake = Sake.loadById(getId(request)) ?: throw HttpNotFound()
        val save = fun(request: Request, response: Response): Sake = save(Sake::class.java, request, response)
        val update = fun(request: Request, response: Response): Sake = update(Sake::class.java, request, response)
        val delete = fun(request: Request, response: Response): String = delete(request, response, fun() = Sake.deleteById(getId(request)))
    }
}
