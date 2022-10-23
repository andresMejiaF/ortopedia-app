package ortopedia.proyecto.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ortopedia.proyecto.model.Producto;
import ortopedia.proyecto.model.Usuario;
import ortopedia.proyecto.service.IUsuarioService;
import ortopedia.proyecto.service.ProductoService;
import ortopedia.proyecto.service.UploadFileService;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;


@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductoService productoService;

    @Autowired
    private UploadFileService upload;

    @Autowired
    private IUsuarioService usuarioService;
    @GetMapping("")
    public String home(Model model){

        model.addAttribute("productos", productoService.findAll());
        return "productos/show";
    }

    @GetMapping("/create")
    public String create(){

        return "productos/create";
    }

    @PostMapping("/save")
    public String save(Producto producto, @RequestParam("img") MultipartFile file, HttpSession session) throws  IOException{

        LOGGER.info("Este es el Objeto Producto{}", producto);
        Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
        producto.setUsuario(usuario);
        //imagen
        if(producto.getId()==null){ //Validacion Para cuando se crea un Producto

                String nombreImagen= upload.saveImage(file);
                producto.setImagen(nombreImagen);

        }



        productoService.save(producto);
        return "redirect:/productos";

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){

       Producto producto = new Producto();
       Optional<Producto> optionalProducto = productoService.get(id);
        producto= optionalProducto.get();

        LOGGER.info("Producto buscado: {}", producto);
        model.addAttribute("producto", producto);
        return "productos/edit";

    }

    @PostMapping("/update")
    public String update(Producto producto, @RequestParam("img") MultipartFile file ) throws IOException {

        Producto p=new Producto();
        p=productoService.get(producto.getId()).get();

        if(file.isEmpty()){ //Es cuando editamos el producto pero no cambiamos la imagen

            producto.setImagen(p.getImagen());

        }
        else{//Se Edita la imagen


            //Eliminar Imagen cuando no es Por defecto
            if(!p.getImagen().equals("default.jpg")){
                upload.deleteImage(p.getNombre());

            }

            String  nombreImagen = upload.saveImage(file);
            producto.setImagen(nombreImagen);
        }

        producto.setUsuario(p.getUsuario());

        productoService.update(producto);

        return "redirect:/productos";

    }

 @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){

        Producto p= new Producto();
        p=productoService.get(id).get();

     //Eliminar Imagen cuando no es Por defecto
        if(!p.getImagen().equals("default.jpg")){
            upload.deleteImage(p.getImagen());

        }

        productoService.delete(id);
        return "redirect:/productos";
    }
}
