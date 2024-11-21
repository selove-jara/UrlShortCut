package ru.job4j.url.shortcut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.url.shortcut.dto.SiteDTO;
import ru.job4j.url.shortcut.service.SiteService;

@RestController
@RequestMapping("/api")
public class SiteController {
    @Autowired
    private SiteService siteService;

    @PostMapping("/registration/{siteName}")
    public ResponseEntity<SiteDTO> register(@RequestBody String siteName) {
        SiteDTO siteDTO = siteService.registerSite(siteName);
        if (siteDTO.isRegistration()) {
            return ResponseEntity.ok(siteDTO);
        } else {
            return ResponseEntity.badRequest()
                    .body(new SiteDTO(false, "", ""));
        }
    }
}
