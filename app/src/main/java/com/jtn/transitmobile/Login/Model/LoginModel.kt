package com.jtn.transitmobile.Login.Model

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jtn.transitmobile.Login.Contract.LoginContract




class LoginModel: LoginContract.Model {

    private lateinit var auth: FirebaseAuth
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    init {
        auth = FirebaseAuth.getInstance()
    }

    override fun LogeaUsuario(
        correo: String,
        contraseña: String,
        callback: LoginContract.Model.Callback
    ) {
        auth.signInWithEmailAndPassword(correo, contraseña)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    user?.uid?.let { uid ->
                        callback.LoginExito(user.email ?: "", uid)
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    callback.LoginError("Authentication failed.")
                }
            }

    }

    override fun getNombreUsuario(uid: String, callback: (String) -> Unit) {
        db.collection("users").document(uid)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val nombre = document.getString("nombre")
                    callback(nombre ?: "")
                } else {
                    callback("")
                }
            }
    }

    override fun getApellidoUsuario(uid: String, callback: (String) -> Unit) {
        db.collection("users").document(uid)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val apellido = document.getString("apellido")
                    callback(apellido ?: "")
                } else {
                    callback("")
                }
            }
    }

}