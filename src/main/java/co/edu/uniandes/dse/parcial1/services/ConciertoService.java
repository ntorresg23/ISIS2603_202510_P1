package co.edu.uniandes.dse.parcial1.services;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConciertoService {

    @Autowired
    ConciertoRepository conciertoRepository;

    @Transactional
    public ConciertoEntity crearConcierto (ConciertoEntity concierto) throws IllegalOperationException {

        log.info("Inicia proceso de creación del concierto");

        if(concierto.getFecha().isAfter(LocalDateTime.now())){
            throw new IllegalOperationException("La fecha ya pasó");
        }

        if(concierto.getAforo() <= 10){

            throw new IllegalOperationException("El aforo debe de ser mayor a 10 personas");

        }

        if(concierto.getPresupuesto() <= 1000){

            throw new IllegalOperationException("El presupuesto debe de ser mayor a 1000 dolares");

        }

        return conciertoRepository.save(concierto);

    }

    @Transactional
    public List<ConciertoEntity> getConciertos (){

        log.info("Empieza la busqueda de todos los conciertos");
        return conciertoRepository.findAll();

    }

    @Transactional
    public ConciertoEntity getConcierto(Long conciertoId) throws EntityNotFoundException {

        log.info("Empieza el proceso de encontrar el concierto por nombre");

        if(conciertoRepository.findById(conciertoId).isEmpty()){

            throw new EntityNotFoundException("El Id no es valido");

        }
        
        return conciertoRepository.findById(conciertoId).get();

    }

    @Transactional
    public ConciertoEntity updateConcierto(Long conciertoid, ConciertoEntity concierto) throws IllegalOperationException, EntityNotFoundException {

        log.info("Empieza el proceso de actualizar el concierto");

        Optional<ConciertoEntity> conciertoEntity = conciertoRepository.findById(conciertoid);

        if(conciertoEntity.isEmpty()){

            throw new EntityNotFoundException("El concierto no existe");

        }

        concierto.setId(conciertoid);
        return conciertoRepository.save(concierto);



    }

    @Transactional
    public void deleteConcierto(Long conciertoId) throws EntityNotFoundException {

        log.info("Empieza el proceso de borrar el concierto");

        Optional<ConciertoEntity> conciertoEntity = conciertoRepository.findById(conciertoId);
        if (conciertoEntity.isEmpty()){

            throw new EntityNotFoundException("El Id no es válido");

        }

        conciertoRepository.deleteById(conciertoId);

    }

}
