package controller

import database.Database
import model.Note
import service.JsonService
import spark.Request
import spark.Response
import spark.Spark

class NotesController : ModelController() {
    companion object {

        fun register(endpointUrl: String) {
            Spark.get(endpointUrl, index)
            Spark.get("$endpointUrl/:id", show)
            Spark.post(endpointUrl, save)
            Spark.put("$endpointUrl/:id", update)
            Spark.delete("$endpointUrl/:id", delete)
        }

        val index = fun(_: Request, _: Response): String = JsonService.toJson(Database.index(Note::class.java, "id", "title", "date"))
        val show = fun (request: Request, response: Response): String = show(Note::class.java, request, response)
        val save = fun (request: Request, response: Response): String = save(Note::class.java, request, response)
        val update = fun (request: Request, response: Response): String = update(Note::class.java, request, response)
        val delete = fun (request: Request, response: Response): String = delete(Note::class.java, request, response)
    }
}
