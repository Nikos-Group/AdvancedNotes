package com.nikos_group.advancednotes;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthController {

    private final FirebaseAuth auth;

    public AuthController() {
        this.auth = FirebaseAuth.getInstance();
    }

    public boolean isAuth() {
        return auth.getCurrentUser() != null;
    }

    public FirebaseUser getUser() {
        return auth.getCurrentUser();
    }

    public void signUp(String email, String password, OnCompleteListener<AuthResult> listener) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(listener);
    }

    public void signIn(String email, String password, OnCompleteListener<AuthResult> listener) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(listener);
    }

    public void signOut() {
        auth.signOut();
    }
}