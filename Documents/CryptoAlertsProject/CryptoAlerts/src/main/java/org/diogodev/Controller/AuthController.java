package org.diogodev.Controller;

import org.diogodev.Model.GoogleAuthRequestDTO;
import org.diogodev.Model.LoginRequestDTO;
import org.diogodev.Model.ResponseDTO;
import org.diogodev.Services.UsersFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsersFacade usersFacade;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO body) {
        try {
            ResponseDTO response = usersFacade.login(body.email(), body.password());
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(new ResponseDTO("Error", ex.getMessage()));
        }
    }

    @PostMapping("/login/google")
    public ResponseEntity<GoogleAuthRequestDTO> loginGoogle(@RequestBody GoogleAuthRequestDTO body) {
        try {
            GoogleAuthRequestDTO response = usersFacade.loginGoogle(body.email(), body.name());
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(new GoogleAuthRequestDTO("Error", "Failed to login with Google: " + ex.getMessage()));
        }
    }

    @PostMapping("/register/google")
    public ResponseEntity<GoogleAuthRequestDTO> registerGoogle(@RequestBody GoogleAuthRequestDTO body) {
        try {
            GoogleAuthRequestDTO response = usersFacade.registerGoogle(body.email(), body.name());
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(new GoogleAuthRequestDTO("Error", "Failed to register with Google: " + ex.getMessage()));
        }
    }
    }
