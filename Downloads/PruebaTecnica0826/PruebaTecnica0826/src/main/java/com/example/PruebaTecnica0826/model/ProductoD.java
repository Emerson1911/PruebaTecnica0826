package com.example.PruebaTecnica0826.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ProductoD {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreD;

    private Float precioD;

    private Integer existenciaD;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaD categoriad;

    public ProductoD() {
    }

    public ProductoD(Long id, String nombreD, Float precioD, Integer existenciaD, CategoriaD categoriad) {
        this.id = id;
        this.nombreD = nombreD;
        this.precioD = precioD;
        this.existenciaD = existenciaD;
        this.categoriad = categoriad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreD() {
        return nombreD;
    }

    public void setNombreD(String nombreD) {
        this.nombreD = nombreD;
    }

    public Float getPrecioD() {
        return precioD;
    }

    public void setPrecioD(Float precioD) {
        this.precioD = precioD;
    }

    public Integer getExistenciaD() {
        return existenciaD;
    }

    public void setExistenciaD(Integer existenciaD) {
        this.existenciaD = existenciaD;
    }

    public CategoriaD getCategoriad() {
        return categoriad;
    }

    public void setCategoriad(CategoriaD categoriad) {
        this.categoriad = categoriad;
    }

    
}
