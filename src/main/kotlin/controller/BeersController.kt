package controller

import model.Beer
import spark.Request
import spark.Response
import spark.Spark

class BeersController : ModelController() {
    companion object {

        fun register(endpointUrl: String) {
            Spark.get(endpointUrl, index)
            Spark.get("$endpointUrl/:id", show)
            Spark.post(endpointUrl, save)
            Spark.put("$endpointUrl/:id", update)
            Spark.delete("$endpointUrl/:id", delete)
        }

        val index = fun(_: Request, _: Response) = index(Beer::class.java)
        val show = fun(request: Request, response: Response) = show(Beer::class.java, request, response)
        val save = fun(request: Request, response: Response) = save(Beer::class.java, request, response)
        val update = fun(request: Request, response: Response) = update(Beer::class.java, request, response)
        val delete = fun(request: Request, response: Response) = delete(Beer::class.java, request, response)
    }
}
