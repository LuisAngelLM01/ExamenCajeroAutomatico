package com.lopezmorales.app.api.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
	
	@PostConstruct // Metodo para inicializar el cajero con los valores predeterminados
    public void initializeCajero() {
        denominationRepository.deleteAll(); // Limpia todo antes
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

    // Obtenemos todas las denominaciones del cajero
    public List<Cajero> getAllDenominations() {
        return denominationRepository.findAll();
    }

    // Realizar un retiro de efectivo
    public String withdraw(double amount) {
        if (amount <= 0) {
            return "El monto debe ser mayor a 0.";
        }

        BigDecimal monto = BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_UP);
        if (monto.remainder(BigDecimal.valueOf(0.50)).compareTo(BigDecimal.ZERO) != 0) {
            return "El monto debe ser múltiplo de 0.50.";
        }

        List<Cajero> originalDenominations = denominationRepository.findAll();
        // Ordenar descendente por denominación
        originalDenominations.sort((d1, d2) -> Double.compare(d2.getDenominacion(), d1.getDenominacion()));

        // Hacer copia temporal para simular el retiro
        List<Cajero> simulated = new ArrayList<>();
        for (Cajero d : originalDenominations) {
            simulated.add(new Cajero(d.getTipo(), d.getCantidad(), d.getDenominacion()));
        }

        List<String> result = new ArrayList<>();
        BigDecimal remainingAmount = monto;

        for (int i = 0; i < simulated.size(); i++) {
            Cajero denomination = simulated.get(i);
            int count = 0;
            BigDecimal denomValue = BigDecimal.valueOf(denomination.getDenominacion());

            while (remainingAmount.compareTo(denomValue) >= 0 && denomination.getCantidad() > 0) {
                remainingAmount = remainingAmount.subtract(denomValue);
                denomination.setCantidad(denomination.getCantidad() - 1);
                count++;
            }

            if (count > 0) {
                result.add(count + " " + denomination.getTipo() + (count > 1 ? "s" : "") + " de $" + denomValue);
            }

            if (remainingAmount.compareTo(BigDecimal.ZERO) == 0) break;
        }

        if (remainingAmount.compareTo(BigDecimal.ZERO) > 0) {
            return "No se puede dispensar el monto exacto. Faltan: $" + remainingAmount;
        } else {
            // Aplicamos cambios reales a originalDenominations
            for (int i = 0; i < originalDenominations.size(); i++) {
                originalDenominations.get(i).setCantidad(simulated.get(i).getCantidad());
            }
            saveDenominations(originalDenominations);
            return String.join(", ", result);
        }
    }

    // Metodo para calcular el efectivo total en el cajero
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
