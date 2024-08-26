package com.example.PruebaTecnica0826.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.PruebaTecnica0826.model.CategoriaD;
import com.example.PruebaTecnica0826.repository.CategoriaDRepository;


@Service
public class CategoriaDService {
    @Autowired
    private CategoriaDRepository categoriaDRepository;

    public List<CategoriaD> listarTodas() {
        return categoriaDRepository.findAll();
    }

    public CategoriaD guardar(CategoriaD categoria) {
        return categoriaDRepository.save(categoria);
    }

    public CategoriaD obtenerPorId(Long id) {
        return categoriaDRepository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        categoriaDRepository.deleteById(id);
    }
}
