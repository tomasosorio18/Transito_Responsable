package com.jtn.transitmobile.Login.Contract



interface LoginContract {

    interface View {
    fun LoginExito(userEmail: String, nombre: String, apellido: String)
    fun LoginError(mensaje:String)
    }

    interface Presenter {
    fun EnviaUsuario(correo:String,contraseña:String)

    }

    interface Model {
    fun LogeaUsuario(correo:String,contraseña:String, callback: Callback)
        interface Callback {
            fun LoginExito(userEmail: String, uid: String)
            fun LoginError(mensaje: String)
        }
    fun getNombreUsuario(uid: String, callback: (String) -> Unit)
    fun getApellidoUsuario(uid: String, callback: (String) -> Unit)

    }
}