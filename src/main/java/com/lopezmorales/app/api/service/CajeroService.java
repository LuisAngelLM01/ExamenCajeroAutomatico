package com.lopezmorales.app.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lopezmorales.app.api.model.Cajero;
import com.lopezmorales.app.api.repository.CajeroRepository;

import jakarta.annotation.PostConstruct;

@Service
public class CajeroService {
	@Autowired
	private CajeroRepository denominationRepository;
	
	@PostConstruct // Método para inicializar el cajero con los valores predeterminados
    public void initializeCajero() {
        if (denominationRepository.count() == 0) {
            List<Cajero> initialDenominations = new ArrayList<>();
            initialDenominations.add(new Cajero("Billete", 2, 1000));
            initialDenominations.add(new Cajero("Billete", 5, 500));
            initialDenominations.add(new Cajero("Billete", 10, 200));
            initialDenominations.add(new Cajero("Billete", 20, 100));
            initialDenominations.add(new Cajero("Billete", 30, 50));
            initialDenominations.add(new Cajero("Billete", 40, 20));
            initialDenominations.add(new Cajero("Moneda", 50, 10));
            initialDenominations.add(new Cajero("Moneda", 100, 5));
            initialDenominations.add(new Cajero("Moneda", 200, 2));
            initialDenominations.add(new Cajero("Moneda", 300, 1));
            initialDenominations.add(new Cajero("Moneda", 100, 0.50));
            denominationRepository.saveAll(initialDenominations);
        }
    }

    // Obtenemos todas las denominaciones del cajero
    public List<Cajero> getAllDenominations() {
        return denominationRepository.findAll();
    }

    // Realizar un retiro de efectivo
    public String withdraw(double amount) {
        List<Cajero> denominations = denominationRepository.findAll();
        denominations.sort((d1, d2) -> Double.compare(d2.getDenominacion(), d1.getDenominacion()));

        List<String> result = new ArrayList<>();
        double remainingAmount = amount;

        for (Cajero denomination : denominations) {
            int count = 0;
            while (remainingAmount >= denomination.getDenominacion() && denomination.getCantidad() > 0) {
                remainingAmount -= denomination.getDenominacion();
                denomination.setCantidad(denomination.getCantidad() - 1);
                count++;
            }
            if (count > 0) {
                result.add(count + " " + denomination.getTipo() + " de " + denomination.getDenominacion());
            }
        }

        if (remainingAmount > 0) {
            return "No se puede dispensar el monto exacto. Restante: " + remainingAmount;
        } else {
            // Guardamos los cambios en la base de datos
            saveDenominations(denominations);
            return String.join(", ", result);
        }
    }
    
    // Método para calcular el efectivo total en el cajero
    public double calculateTotalCash() {
        List<Cajero> denominations = denominationRepository.findAll();
        double totalCash = 0.0;
        for (Cajero denomination : denominations) {
            totalCash += denomination.getCantidad() * denomination.getDenominacion();
        }
        return totalCash;
    }

    // Guardar los cambios en las cantidades de billetes y monedas en la base de datos
    private void saveDenominations(List<Cajero> denominations) {
        denominationRepository.saveAll(denominations);
    }
}
