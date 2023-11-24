package com.jtn.transitmobile.Login.Presenter

import com.jtn.transitmobile.Login.Contract.LoginContract
import com.jtn.transitmobile.Login.Model.LoginModel


class LoginPresenter(_view: LoginContract.View, model: LoginContract.Model): LoginContract.Presenter{
    private val view: LoginContract.View = _view
    private val model: LoginContract.Model= model

    override fun EnviaUsuario(correo: String, contraseña: String) {
        if (view != null) {
            model.LogeaUsuario(correo, contraseña, object : LoginContract.Model.Callback {

                override fun LoginExito(userEmail: String, uid: String) {
                    model.getNombreUsuario(uid) { nombre ->
                        model.getApellidoUsuario(uid) { apellido ->
                            // Ahora tienes el correo, nombre y apellido del usuario
                            // Puedes enviarlos a la vista o realizar cualquier otra acción necesaria
                            view?.LoginExito(userEmail, nombre, apellido)
                        }
                    }
                }

                override fun LoginError(mensaje: String) {
                    view?.LoginError(mensaje)
                }
            })
        }
    }





}