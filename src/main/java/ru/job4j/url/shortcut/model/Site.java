package ru.job4j.url.shortcut.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sites")
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String siteName;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    private String password;

    public Site(String password, String login, String siteName) {
        this.password = password;
        this.login = login;
        this.siteName = siteName;
    }
}
