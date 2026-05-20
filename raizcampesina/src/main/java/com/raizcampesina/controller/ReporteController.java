
package com.raizcampesina.controller;

import com.raizcampesina.service.UsuarioService;
import com.raizcampesina.service.ProductoService;
import com.raizcampesina.service.PedidoService;
import com.raizcampesina.service.ValoracionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// Controlador de métricas de control corporativo y análisis de datos estadísticos globales.
@Controller
public class ReporteController
    {
        @Autowired
        private UsuarioService usuarioService;

        @Autowired
        private ProductoService productoService;

        @Autowired
        private PedidoService pedidoService;

        @Autowired
        private ValoracionService valoracionService;

        // Compila los volúmenes globales de registros de la base de datos para los tableros de control de analítica.
        @GetMapping("/reportes")
        public String mostrarReportes(Model model)
            {
                model.addAttribute("totalUsuarios",
                        usuarioService.listarUsuarios().size());

                model.addAttribute("totalProductos",
                        productoService.listarProductos().size());

                model.addAttribute("totalPedidos",
                        pedidoService.listarPedidos().size());

                model.addAttribute("totalValoraciones",
                        valoracionService.listarValoraciones().size());

                return "reportes";
            }
    }

