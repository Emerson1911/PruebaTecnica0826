package com.example.PruebaTecnica0826.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.PruebaTecnica0826.model.ProductoD;
import com.example.PruebaTecnica0826.repository.ProductoDRepository;

@Service
public class ProductoDService {
    @Autowired
    private ProductoDRepository productoDRepository;

     public List<ProductoD> listarTodas() {
        return productoDRepository.findAll();
    }

    public ProductoD guardar(ProductoD producto) {
        return productoDRepository.save(producto);
    }

    public ProductoD obtenerPorId(Long id) {
        return productoDRepository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        productoDRepository.deleteById(id);
    }

}
