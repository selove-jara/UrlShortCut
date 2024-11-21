package ru.job4j.url.shortcut.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Url {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "original_url", nullable = false, unique = true)
        private String originalUrl;

        @Column(name = "short_code", nullable = false, unique = true)
        private String shortCode;

        @ManyToOne(fetch = FetchType.EAGER)
        private Site site;

        @Column(name = "call_count", nullable = false)
        private Long callCount = 0L;
}