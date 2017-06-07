package controller

import model.Note
import service.JsonService
import spark.Request
import spark.Response
import spark.Spark

class NotesController : ModelController() {
    companion object {

        fun register(endpointUrl: String) {
            Spark.get(endpointUrl, index, JsonService.gson::toJson)
            Spark.get("$endpointUrl/:id", show, JsonService.gson::toJson)
            Spark.post(endpointUrl, save, JsonService.gson::toJson)
            Spark.put("$endpointUrl/:id", update, JsonService.gson::toJson)
            Spark.delete("$endpointUrl/:id", delete, JsonService.gson::toJson)
        }

        val index = fun(_: Request, _: Response): List<Note> = index(Note::class.java, "id", "title", "date")
        val show = fun (request: Request, response: Response): Note = show(Note::class.java, request, response)
        val save = fun (request: Request, response: Response): Note = save(Note::class.java, request, response)
        val update = fun (request: Request, response: Response): Note = update(Note::class.java, request, response)
        val delete = fun (request: Request, response: Response): String = delete(Note::class.java, request, response)
    }
}
