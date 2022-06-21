package io.corona.coronavirustracker.controllers;

import io.corona.coronavirustracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeControler {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/") // URL
    public String home(Model model) {
        model.addAttribute("locationStats", coronaVirusDataService.getAllStats());
        return "home";
    }
}
