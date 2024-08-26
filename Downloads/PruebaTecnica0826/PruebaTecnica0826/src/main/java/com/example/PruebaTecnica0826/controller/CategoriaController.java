package com.example.PruebaTecnica0826.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.PruebaTecnica0826.model.CategoriaD;
import com.example.PruebaTecnica0826.services.CategoriaDService;

@Controller
@RequestMapping("categoria")
public class CategoriaController {

    @Autowired
    private CategoriaDService categoriaDService;

    @GetMapping
    public String listarCategoria(Model model){
        model.addAttribute("categorias", categoriaDService.listarTodas());
        return "categoria/categoria-list";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevaCategoria(Model model) {
        model.addAttribute("categoria", new CategoriaD());
        return "categoria/categoria-forn";
    }

    @PostMapping
    public String guardarCategoria(CategoriaD categoria) {
        categoriaDService.guardar(categoria);
        return "redirect:/categoria";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarCategoria(@PathVariable Long id, Model model) {
        model.addAttribute("categoria", categoriaDService.obtenerPorId(id));
        return "categoria/categoria-forn";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCategoria(@PathVariable Long id) {
        categoriaDService.eliminar(id);
        return "redirect:/categoria";
    }

}
