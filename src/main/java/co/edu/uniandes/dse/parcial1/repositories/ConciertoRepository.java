package co.edu.uniandes.dse.parcial1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import java.util.List;


@Repository
public interface ConciertoRepository extends JpaRepository<ConciertoEntity, Long> {

    List<ConciertoEntity> findByNombre(String nombre);

}
