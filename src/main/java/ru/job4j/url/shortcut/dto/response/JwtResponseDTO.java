package ru.job4j.url.shortcut.dto.response;

import lombok.Data;

@Data
public class JwtResponseDTO {
    private String token;
    private String type = "Bearer";
    private String username;

    public JwtResponseDTO(String accessToken, String username) {
        this.token = accessToken;
        this.username = username;
    }
}
