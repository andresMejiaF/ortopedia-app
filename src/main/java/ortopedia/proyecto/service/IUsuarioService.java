package ortopedia.proyecto.service;

import ortopedia.proyecto.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    Optional<Usuario> findById(Integer id);
    Usuario save(Usuario usuario);

    List<Usuario> findAll();
    Optional<Usuario> finByEmail(String email);

}
