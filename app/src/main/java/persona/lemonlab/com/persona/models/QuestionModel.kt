package persona.lemonlab.com.persona.models


class QuestionModel(val id: String, val questionText: String,
                    val a: String, val b: String, val c: String, val d: String) {
    constructor() : this("","","","","","")
}
