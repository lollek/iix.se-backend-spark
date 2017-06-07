package controller

import model.Beer
import service.JsonService
import spark.Request
import spark.Response
import spark.Spark

class BeersController : ModelController() {
    companion object {

        fun register(endpointUrl: String) {
            Spark.get(endpointUrl, index, JsonService.gson::toJson)
            Spark.get("$endpointUrl/:id", show, JsonService.gson::toJson)
            Spark.post(endpointUrl, save, JsonService.gson::toJson)
            Spark.put("$endpointUrl/:id", update, JsonService.gson::toJson)
            Spark.delete("$endpointUrl/:id", delete, JsonService.gson::toJson)
        }

        val index = fun(_: Request, _: Response): List<Beer> = index(Beer::class.java)
        val show = fun(request: Request, response: Response): Beer = show(Beer::class.java, request, response)
        val save = fun(request: Request, response: Response): Beer = save(Beer::class.java, request, response)
        val update = fun(request: Request, response: Response): Beer = update(Beer::class.java, request, response)
        val delete = fun(request: Request, response: Response): String = delete(Beer::class.java, request, response)
    }
}
