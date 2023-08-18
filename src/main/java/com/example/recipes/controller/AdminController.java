package com.example.recipes.controller;

import com.example.recipes.security.UserManagementService;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController()
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    private final UserManagementService userManagementService;

    public AdminController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(path = "/addrole/{email}")
    public ResponseEntity<String> setUserClaims(Principal principal,@PathVariable String email,@RequestBody List<String> requestedClaims
    ) throws FirebaseAuthException {

        try {
            userManagementService.setUserClaims(principal.getName(), email, requestedClaims);
            return ResponseEntity.ok().body("ok");

        }catch (RuntimeException e){
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("you can't edit your own roles");
        }
    }
}
