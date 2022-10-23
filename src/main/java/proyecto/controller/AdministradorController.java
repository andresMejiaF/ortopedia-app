package proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ortopedia.proyecto.model.Orden;
import ortopedia.proyecto.model.Producto;
import ortopedia.proyecto.service.IUsuarioService;
import ortopedia.proyecto.service.OrdenService;
import ortopedia.proyecto.service.ProductoService;

import java.util.List;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private OrdenService ordenService;


    @GetMapping("")
    public String home(Model model){

        List<Producto> productos = productoService.findAll();

        model.addAttribute("productos", productos);
        return "administrador/home";
    }

    @GetMapping("/usuarios")
    public String usuarios(Model model){

        model.addAttribute("usuarios", usuarioService.findAll());
        return "administrador/usuarios";
    }

    @GetMapping("/ordenes")
    public String ordenes(Model model){

        model.addAttribute("ordenes", ordenService.findAll());

        return "administrador/ordenes";
    }

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Integer id, Model model ){
        System.out.println("ID de ña orden: " + id);

        Orden orden= ordenService.finById(id).get();

        model.addAttribute("detalles", orden.getDetalle());

        return "administrador/detalleorden";
    }


}


