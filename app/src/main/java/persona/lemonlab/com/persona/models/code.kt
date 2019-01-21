package persona.lemonlab.com.persona.models

data class code(var value:String, var isUsed:Boolean, var name:String) {
    constructor():this("", false, "")
}