package com.lopezmorales.app.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lopezmorales.app.api.model.Cajero;
import com.lopezmorales.app.api.service.CajeroService;

@Controller
@RequestMapping("/atm")
public class CajeroController {
	@Autowired
    private CajeroService atmService;

	@GetMapping("/home")
    public String getHomePage() {
        return "index";
    }

    @GetMapping("/denominaciones")
    public String getAllDenominations(Model model) {
        List<Cajero> denominations = atmService.getAllDenominations();
        model.addAttribute("denominations", denominations);
        return "denominations";
    }

    @PostMapping("/retirar")
    public String withdraw(@RequestParam double amount, Model model) {
        String withdrawalResult = atmService.withdraw(amount);
        model.addAttribute("withdrawalResult", withdrawalResult);

        // Calcular y agregar el efectivo total disponible en el cajero
        double totalCash = atmService.calculateTotalCash();
        model.addAttribute("totalCash", totalCash);

        return "resultado";
    }
    
}
