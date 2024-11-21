package ru.job4j.url.shortcut.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.job4j.url.shortcut.dto.request.LoginRequestDTO;
import ru.job4j.url.shortcut.dto.response.JwtResponseDTO;
import ru.job4j.url.shortcut.jwt.JwtUtils;
import ru.job4j.url.shortcut.service.SiteService;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SiteService siteService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/auth")
    public ResponseEntity<JwtResponseDTO> authenticate(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getLogin(), loginRequestDTO.getPassword()));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(authentication);
        JwtResponseDTO response = new JwtResponseDTO(jwt, userDetails.getUsername());
        System.out.println("Login: " + loginRequestDTO.getLogin());
        System.out.println("Password: " + loginRequestDTO.getPassword());
        return ResponseEntity.ok(response);
    }
}