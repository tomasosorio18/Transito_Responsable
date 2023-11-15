package com.jtn.transitmobile.Register.Presenter

import com.jtn.transitmobile.Register.Contract.RegisterContract

class RegisterPresenter(private var view: RegisterContract.View, private val model: RegisterContract.Model):RegisterContract.Presenter{

    override fun EnviaUsuario(
        correo: String,
        contraseña: String,
        nombre: String,
        apellido: String
    ) {
        if (view != null) {
            model.RegistraUsuario(correo, contraseña,nombre,apellido, object : RegisterContract.Model.Callback {
                override fun RegistroExito() {
                    view?.RegistroExito()
                }

                override fun RegistroError(mensaje: String) {
                    view?.RegistroError(mensaje)
                }
            })
        }
    }
    fun getNombreUsuario(uid: String, callback: (String) -> Unit) {
       model.getNombreUsuario(uid, callback)
    }

    fun getApellidoUsuario(uid: String, callback: (String) -> Unit) {
        model.getApellidoUsuario(uid, callback)
    }

}