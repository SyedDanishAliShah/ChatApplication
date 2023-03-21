package com.example.chatapplication

class User {
    var name : String? = null//initially setting it to null
    var email : String? = null
    var uid : String? = null

    constructor(){}//As we are using firebase as the backend thats why

    constructor(name : String?, email:String?, uid:String?){
        this.name = name//initializing the name variable
        this.email = email
        this.uid = uid
    }


}