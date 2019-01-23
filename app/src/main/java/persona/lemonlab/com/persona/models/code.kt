package persona.lemonlab.com.persona.models

data class code(var value:String, var isUsed:Boolean, var host_name:String,
                var guest_name:String,var guestIsHere:Boolean ,var host:ArrayList<Int>, var guest:ArrayList<Int>) {
    constructor():this("", false, "","",false, ArrayList(), ArrayList())
}