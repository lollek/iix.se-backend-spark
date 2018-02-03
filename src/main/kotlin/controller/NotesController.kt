package controller

import exceptions.HttpNotFound
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

        val index = fun(_: Request, _: Response): List<Note>
                = Note.loadAllAsRef()

        val show = fun(request: Request, _: Response): Note
                = Note.loadById(getId(request)) ?: throw HttpNotFound()

        val save = fun(request: Request, response: Response): Note
                = save(Note::class.java, request, response)

        val update = fun(request: Request, response: Response): Note
                = update(Note::class.java, request, response)

        val delete = fun(request: Request, response: Response): String
                = delete(request, response, fun() = Note.deleteById(getId(request)))
    }
}
