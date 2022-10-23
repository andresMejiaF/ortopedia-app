package ortopedia.proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ortopedia.proyecto.model.Usuario;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final IUsuarioService usuarioService;



    final
    HttpSession session;

    public UserDetailServiceImpl(IUsuarioService usuarioService,  HttpSession session) {
        this.usuarioService = usuarioService;

        this.session = session;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("Username: ");
        Optional<Usuario> optionalUsuario = usuarioService.finByEmail(username);
        if(optionalUsuario.isPresent()){
            System.out.println("Id del usuario: " + optionalUsuario.get().getId());
            session.setAttribute("idusuario", optionalUsuario.get().getId());
            Usuario usuario = optionalUsuario.get();
            return User.builder().username(usuario.getNombre()).password(usuario.getPassword()).roles(usuario.getTipo()).build();
        }else{
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

    }
}
