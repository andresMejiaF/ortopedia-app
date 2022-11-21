package ortopedia.proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ortopedia.proyecto.model.Usuario;
import ortopedia.proyecto.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public Optional<Usuario> findById(Integer id) {

        return usuarioRepository.findById(id);
    }

    /*
    @Override
    public Usuario save(Usuario usuario) throws Exception{

        Optional<Usuario>  buscado = finByEmail(usuario.getEmail());
        if(buscado.isPresent()){
                throw new Exception("El Correo del usuario ya existe");

        }else{
            return usuarioRepository.save(usuario);
        }


    }
*/
    @Override
    public Usuario save(Usuario usuario) {

        Optional<Usuario>  buscado = finByEmail(usuario.getEmail());

            return usuarioRepository.save(usuario);

    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> finByEmail(String email) {

        return usuarioRepository.findByEmail(email);

    }
}
