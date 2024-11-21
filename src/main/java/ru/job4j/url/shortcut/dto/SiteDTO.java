package ru.job4j.url.shortcut.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SiteDTO {
    private boolean registration;

    private String login;

    private String password;
}
