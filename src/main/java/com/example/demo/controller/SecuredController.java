package com.example.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecuredController {
    /**
     * test APIs to illustrate the APIs authorities in spring security.
     */
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @GetMapping("/api/private/hello")
    public String testSecuredMethod() {
        return "This is secured hello method";
    }

    @GetMapping("/api/public/hello")
    public String testUnSecuredMethod() {
        return "This is un-secured hello method";
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @DeleteMapping("/api/private/delete")
    public String testSecuredDeleteMethod() {
        return "This is secured delete method";
    }
}
