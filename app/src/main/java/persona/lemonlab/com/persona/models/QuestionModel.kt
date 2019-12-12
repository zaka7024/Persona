package persona.lemonlab.com.persona.models


class QuestionModel(var id: String, var questionText: String, var a: String, var b: String, var c: String, var d: String) {
    constructor() : this("","","","","","")
}
data class OnlineQuestion(val questionText:String, //This is the actual question
                           var firstAnswer:Pair<String, Pair<Int, Int>>, //String is the answer text, first Int is the answer effect on the final result x,
                           var secondAnswer:Pair<String, Pair<Int, Int>>,  // second Int is the answer effect on the final result y.
                           var thirdAnswer:Pair<String, Pair<Int, Int>>,
                           var fourthAnswer:Pair<String, Pair<Int, Int>>)
