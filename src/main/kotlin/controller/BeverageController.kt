package controller

import exceptions.HttpBadRequest
import exceptions.HttpNotFound
import model.Beverage
import service.JsonService
import spark.Request
import spark.Response
import spark.Spark

class BeverageController : ModelController() {
    companion object {

        fun register(endpointUrl: String) {
            Spark.get(endpointUrl, index, JsonService.gson::toJson)
            Spark.get("$endpointUrl/:id", show, JsonService.gson::toJson)
            Spark.post(endpointUrl, save, JsonService.gson::toJson)
            Spark.put("$endpointUrl/:id", update, JsonService.gson::toJson)
            Spark.delete("$endpointUrl/:id", delete, JsonService.gson::toJson)
        }

        private fun getCategory(request: Request): Beverage.Companion.Category {
            try {
                val category: Int = Integer.parseInt(request.queryParams("category"))
                return Beverage.Companion.Category.values()[category]
            } catch (_: Exception) {
                throw HttpBadRequest("")
            }
        }

        val index = fun(request: Request, _: Response): List<Beverage>
                = Beverage.loadAll(getCategory(request))

        val show = fun(request: Request, _: Response): Beverage
                = Beverage.loadById(getId(request)) ?: throw HttpNotFound()

        val save = fun(request: Request, response: Response): Beverage
                = save(Beverage::class.java, request, response)

        val update = fun(request: Request, response: Response): Beverage
                = update(Beverage::class.java, request, response)

        val delete = fun(request: Request, response: Response): String
                = delete(request, response, fun() = Beverage.deleteById(getId(request)))
    }
}
