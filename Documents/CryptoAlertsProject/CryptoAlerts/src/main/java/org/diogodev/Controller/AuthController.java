package org.diogodev.Controller;


import org.diogodev.Model.LoginRequestDTO;
import org.diogodev.Model.ResponseDTO;

import org.diogodev.Services.UsersFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UsersFacade usersFacade;
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO body) {
        try {
            ResponseDTO response = usersFacade.login(body.email(), body.password());
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(new ResponseDTO("Error", ex.getMessage()));
        }
    }



}
