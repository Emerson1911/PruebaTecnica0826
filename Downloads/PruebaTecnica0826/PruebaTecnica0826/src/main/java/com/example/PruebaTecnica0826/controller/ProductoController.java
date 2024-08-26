package com.example.PruebaTecnica0826.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.PruebaTecnica0826.model.ProductoD;
import com.example.PruebaTecnica0826.services.CategoriaDService;
import com.example.PruebaTecnica0826.services.ProductoDService;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoDService productoDService;

    @Autowired
    private CategoriaDService categoriaDService;

    @GetMapping
    public String listarProducto(Model model) {
        model.addAttribute("productos", productoDService.listarTodas());
        return "producto/producto-list";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioProducto(Model model) {
        model.addAttribute("producto", new ProductoD());
        model.addAttribute("categorias", categoriaDService.listarTodas());
        return "producto/producto-forn";
    }

    @PostMapping
    public String guardarProducto(ProductoD producto) {
        productoDService.guardar(producto);
        return "redirect:/producto";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarDetallesOrden(@PathVariable Long id, Model model) {
        model.addAttribute("producto", productoDService.obtenerPorId(id));
        model.addAttribute("categorias", categoriaDService.listarTodas());
        return "producto/producto-forn";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarDetallesOrden(@PathVariable Long id) {
        productoDService.eliminar(id);
        return "redirect:/producto";
    }
}
