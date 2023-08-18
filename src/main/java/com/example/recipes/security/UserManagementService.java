package com.example.recipes.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserManagementService {

    private final FirebaseAuth firebaseAuth;

    public UserManagementService(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    public void setUserClaims(String adminUID,String email, List<String > requestedPermissions) throws FirebaseAuthException {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", requestedPermissions);
        UserRecord userByEmail = this.firebaseAuth.getUserByEmail(email);
        if(!adminUID.equals(userByEmail.getUid()))
            this.firebaseAuth.setCustomUserClaims(userByEmail.getUid(),claims);
        else
            throw new RuntimeException("can't change your roles by yourself");
    }
}