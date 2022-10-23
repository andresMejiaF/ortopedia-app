package proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ortopedia.proyecto.model.DetalleOrden;
import ortopedia.proyecto.model.Orden;
import ortopedia.proyecto.model.Producto;
import ortopedia.proyecto.model.Usuario;
import ortopedia.proyecto.service.DetalleOrdenService;
import ortopedia.proyecto.service.IUsuarioService;
import ortopedia.proyecto.service.OrdenService;
import ortopedia.proyecto.service.ProductoService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HomeController {



    @Autowired
    private ProductoService productoService;
    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private OrdenService ordenService;

    @Autowired
    private DetalleOrdenService detalleOrdenService;


    List<DetalleOrden> detalles= new ArrayList<DetalleOrden>();


    Orden orden = new Orden();

    @GetMapping("")
    public String home(Model model, HttpSession session){

        System.out.println("SESION DE " + session.getAttribute("idusuario"));

        model.addAttribute("productos", productoService.findAll());

        //

        model.addAttribute("sesion", session.getAttribute("idusuario"));
        return "usuario/home_usuario";
    }

    @GetMapping("productohome/{id}")
    public String productoHome(@PathVariable Integer id, Model model){

        Producto producto = new Producto();

        Optional<Producto> productoOptional = productoService.get(id);
        producto=productoOptional.get();

        model.addAttribute("producto", producto);

        System.out.println("Id producto enviado como parametro: "+ id);
        return "usuario/productoHome";
    }

    @GetMapping("productohomeadm/{id}")
    public String productoHomeAdm(@PathVariable Integer id, Model model){

        Producto producto = new Producto();

        Optional<Producto> productoOptional = productoService.get(id);
        producto=productoOptional.get();

        model.addAttribute("producto", producto);

        System.out.println("Id producto enviado como parametro: "+ id);
        return "administrador/productoHomeadmin";
    }

    @PostMapping("/cart")
    public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model, HttpSession session) {
        if(session.getAttribute("idusuario") != null) {
            DetalleOrden detalleOrden = new DetalleOrden();
            Producto producto = new Producto();
            double sumaTotal = 0;

            Optional<Producto> optionalProducto = productoService.get(id);

            System.out.println("Producto añadido: " + optionalProducto.get());
            System.out.println(("Cantidad: ") + cantidad);
            producto = optionalProducto.get();

            detalleOrden.setCantidad(cantidad);
            detalleOrden.setPrecio(producto.getPrecio());
            detalleOrden.setNombre(producto.getNombre());
            detalleOrden.setTotal(producto.getPrecio() * cantidad);
            detalleOrden.setProducto(producto);

            //Validacion qpara que el producto no se añada dos veces

            Integer idProducto = producto.getId();
            boolean ingresado = detalles.stream().anyMatch(p -> p.getProducto().getId() == idProducto);
            if (!ingresado) {
                detalles.add(detalleOrden);

            }


            sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();

            orden.setTotal(sumaTotal);
            model.addAttribute("cart", detalles);
            model.addAttribute("orden", orden);
            model.addAttribute("sesion", session.getAttribute("idusuario"));
            return "usuario/carrito";
        }else{
            return "usuario/login";
        }
    }
    //Quitar un producto del carrito
    @GetMapping("/delete/cart/{id}")
    public String deleteProductoCart(@PathVariable Integer id, Model model) {

        // lista nueva de prodcutos
        List<DetalleOrden> ordenesNueva = new ArrayList<DetalleOrden>();

        for (DetalleOrden detalleOrden : detalles) {
            if (detalleOrden.getProducto().getId() != id) {
                ordenesNueva.add(detalleOrden);
            }
        }

        // poner la nueva lista con los productos restantes
        detalles = ordenesNueva;

        double sumaTotal = 0;
        sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();

        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
            return "usuario/carrito";
        }

        @GetMapping("/getCart")
    public String getCart(Model model, HttpSession session){

        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        model.addAttribute("sesion", session.getAttribute("idusuario"));


        return "usuario/carrito";
    }

    @GetMapping("/orden")
    public String order(Model model, HttpSession session){

        Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();


        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        model.addAttribute("usuario", usuario);

        return "usuario/resumenorden";
    }

    @GetMapping("/saveOrder")
    public String saveOrder(HttpSession session){

        Date fechaCreacion= new Date();
        orden.setFechaCreacion(fechaCreacion);
        orden.setNumero(ordenService.generarNumeroOrden());

        //usuario
        Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();

        orden.setUsuario(usuario);
        ordenService.save(orden);

        //guardando detalles
        for (DetalleOrden dt: detalles){
            dt.setOrden(orden);
            detalleOrdenService.save(dt);
        }
        //Limpiando los valores
        orden = new Orden();
        detalles.clear();

        return "redirect:/";
    }

    @PostMapping("/search")
    public String searchProduct(@RequestParam String nombre, Model model, HttpSession session){
        System.out.println("SESION BUSQUEDA DE " + session.getAttribute("idusuario"));
        System.out.println("nombre del producto: " + nombre);
      //pendiente a mejorar
        List<Producto> productos = productoService.findAll().stream().filter( p -> p.getNombre().contains(nombre)).collect(Collectors.toList());
        model.addAttribute("productos", productos);
        model.addAttribute("sesion", session.getAttribute("idusuario"));
        return "usuario/home_usuario";
    }


    @PostMapping("/searchadmin")
    public String searchProductAdmin(@RequestParam String nombre, Model model){
        System.out.println("nombre del valido: " + nombre);
        //pendiente a mejorar
        List<Producto> productos = productoService.findAll().stream().filter( p -> p.getNombre().contains(nombre)).collect(Collectors.toList());
        model.addAttribute("productos", productos);
        return "administrador/home";
    }




}
