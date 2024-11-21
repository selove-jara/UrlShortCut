package ru.job4j.url.shortcut.dto;

import lombok.*;
import ru.job4j.url.shortcut.model.Site;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlRequest {
    private String url;
    private String login;
}
