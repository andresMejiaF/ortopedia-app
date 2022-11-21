package ortopedia.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ortopedia.proyecto.model.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

        Optional<Usuario> findByEmail(String email);

        @Query("select u from Usuario u where u.email = :email")
        Usuario obtenerEmail(String email);

}
