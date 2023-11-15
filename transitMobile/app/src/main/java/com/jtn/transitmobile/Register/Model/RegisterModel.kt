package com.jtn.transitmobile.Register.Model

import android.R
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.jtn.transitmobile.Register.Contract.RegisterContract


class RegisterModel: RegisterContract.Model{

    private lateinit var auth: FirebaseAuth
    private val firestore = Firebase
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    init {
        auth = FirebaseAuth.getInstance()
    }
    override fun RegistraUsuario(
        correo: String,
        contraseña: String,
        nombre: String,
        apellido: String,
        callback: RegisterContract.Model.Callback
    ) {
        auth.createUserWithEmailAndPassword(correo,contraseña)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.uid?.let { uid ->
                        // Almacena los datos adicionales del usuario en Firestore
                        val userData = hashMapOf(
                            "nombre" to nombre,
                            "apellido" to apellido
                            // Puedes agregar otros datos adicionales aquí
                        )
                       db.collection("users").document(uid)
                            .set(userData)
                            .addOnSuccessListener {
                                callback.RegistroExito()
                            }
                            .addOnFailureListener {
                                callback.RegistroError("Registro fallido.")
                            }
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    callback.RegistroError("Registration failed.")

                }

                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.uid?.let { uid ->
                        // Almacena los datos adicionales del usuario en Firestore
                        val userData = hashMapOf(
                            "nombre" to nombre,
                            "apellido" to apellido
                            // Puedes agregar otros datos adicionales aquí
                        )
                        db.collection("users").document(uid)
                            .set(userData)
                            .addOnSuccessListener {
                                callback.RegistroExito()
                            }
                    }
                }else{
                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        callback.RegistroError("Contraseña muy debil.")
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        callback.RegistroError("Formato correo erroneo.")
                    } catch (e: FirebaseAuthUserCollisionException) {
                        callback.RegistroError("Usuario ya existe!.")
                    } catch (e: Exception) {
                        Log.e(TAG, e.message!!)
                    }

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
