package ortopedia.proyecto.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ortopedia.proyecto.model.Orden;
import ortopedia.proyecto.model.Usuario;
import ortopedia.proyecto.service.IUsuarioService;
import ortopedia.proyecto.service.OrdenService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private OrdenService ordenService;

    BCryptPasswordEncoder passEncode = new BCryptPasswordEncoder();


    // -> /usuario/registro
    @GetMapping("/registro")
    public String create(){

        return "usuario/registro";
    }

    @PostMapping("/save")
    public String save(Usuario usuario){

        System.out.println(usuario.getNombre() + " " + usuario.getEmail());
        usuario.setTipo("USER");
        usuario.setPassword(passEncode.encode(usuario.getPassword()));
        usuarioService.save(usuario);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(){

        return "usuario/login";
    }

    @GetMapping("/acceder")
    public String acceder(Usuario usuario, HttpSession session) throws Exception {



        Optional<Usuario> user = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString()));
     //   System.out.println(user.get().getEmail() + " CONTRASEÑA: " + user.get().getPassword());

        if(user.isPresent()){
            session.setAttribute("idusuario", user.get().getId());
            if(user.get().getTipo().equals("ADMIN")){
                return "redirect:/administrador";
            }else{
                return "redirect:/";

            }
        }else{
            throw new Exception("los datos ingresados no se encuentran en base de datos");
        }

      //  return "redirect:/";
    }

    @GetMapping("/compras")
    public String obtenerCompras(Model model, HttpSession session){

        model.addAttribute("sesion", session.getAttribute("idusuario"));

        Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();

        List<Orden> ordenes = ordenService.findByUsuario(usuario);

        model.addAttribute("ordenes", ordenes);

        return "usuario/compras";
    }

    @GetMapping("/detalle/{id}")
    public String detalleCompra(@PathVariable Integer id, HttpSession session, Model model){

        System.out.println("ID de la ordem: "+ id);

        Optional<Orden> orden = ordenService.finById(id);

        model.addAttribute("detalles", orden.get().getDetalle());
        //session
        model.addAttribute("sesion", session.getAttribute("idusuario"));


        return "usuario/detallecompra";
    }

    @GetMapping("/cerrar")
    public String cerrarSesion(HttpSession session){

        session.removeAttribute("idusuario");

        return "redirect:/";
    }


}
