package com.nikos_group.advancednotes.authcontrollers

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthController {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun isAuth(): Boolean {
        return auth.currentUser != null
    }

    val user: FirebaseUser?
        get() = auth.currentUser

    fun signUp(email: String?, password: String?, listener: OnCompleteListener<AuthResult?>?) {
        auth.createUserWithEmailAndPassword(email!!, password!!).addOnCompleteListener(listener!!)
    }

    fun signIn(email: String?, password: String?, listener: OnCompleteListener<AuthResult?>?) {
        auth.signInWithEmailAndPassword(email!!, password!!).addOnCompleteListener(listener!!)
    }

    fun signOut() {
        auth.signOut()
    }
}