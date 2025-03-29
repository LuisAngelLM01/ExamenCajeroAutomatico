package com.lopezmorales.app.api.model;

import jakarta.persistence.*;

@Entity
public class Cajero {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name = "cajero_seq", sequenceName = "ISEQ$$_76613", allocationSize = 1)
    private Long id;
    private String tipo;
    private int cantidad;
    private double denominacion;
    
    // Constructor
    public Cajero() {
    }

    public Cajero(String tipo, int cantidad, double denominacion) {
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.denominacion = denominacion;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(double denominacion) {
		this.denominacion = denominacion;
	}
}
