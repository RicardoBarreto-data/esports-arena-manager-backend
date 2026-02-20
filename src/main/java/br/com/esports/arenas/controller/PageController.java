package br.com.esports.arenas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard-admin")
    public String dashboardAdmin() {
        return "dashboard-admin";
    }

    @GetMapping("/dashboard-organizador")
    public String dashboardOrganizador() {
        return "dashboard-organizador";
    }

    @GetMapping("/torneios")
    public String torneios() {
        return "torneios";
    }

    @GetMapping("/times")
    public String times() {
        return "times";
    }

    @GetMapping("/partidas")
    public String partidas() {
        return "partidas";
    }

    @GetMapping("/rankings")
    public String rankings() {
        return "rankings";
    }
    
    @GetMapping("/jogadores")
    public String jogadores() {
        return "jogadores";
    }
}