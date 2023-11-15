package com.jtn.transitmobile.Register.Contract

interface RegisterContract {

    interface View {
        fun RegistroExito()
        fun RegistroError(mensaje:String)
    }

    interface Presenter {
        fun EnviaUsuario(correo:String,contraseña:String,nombre:String,apellido:String)

    }

    interface Model {
        fun RegistraUsuario(correo:String,contraseña:String, nombre: String, apellido: String,callback: Callback)
        fun getNombreUsuario(uid: String, callback: (String) -> Unit)
        fun getApellidoUsuario(uid: String, callback: (String) -> Unit)

        interface Callback {
            fun RegistroExito()
            fun RegistroError(mensaje: String)
        }
    }
}