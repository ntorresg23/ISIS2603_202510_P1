package co.edu.uniandes.dse.parcial1.services;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;
import jakarta.transaction.Transactional;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConciertoEstadioService {

    @Autowired

    ConciertoRepository conciertoRepository;

    EstadioRepository estadioRepository;

    @Transactional
    public ConciertoEntity addConcierto(Long conciertoId, Long estadioId) throws EntityNotFoundException, IllegalOperationException {

        log.info("Empieza el proceso de agregar un concierto al estadio de id = {0}", estadioId);

        Optional<EstadioEntity> estadioEntity = estadioRepository.findById(estadioId);
        if(estadioEntity.isEmpty()){

            throw new EntityNotFoundException("El estadio no existe");

        }

        Optional<ConciertoEntity> conciertoEntity = conciertoRepository.findById(conciertoId);

        if(conciertoEntity.isEmpty()){

            throw new EntityNotFoundException("El concierto no existe");

        }

        List<ConciertoEntity> conciertos = estadioEntity.get().getConciertos();

        for(int i = 0; i < conciertos.size(); i++){

            if(Duration.between(conciertoEntity.get().getFecha(), conciertos.get(i).getFecha()).toDays() < 2){

                throw new IllegalOperationException("Debe haber un minimo de 2 días entre conciertos");

            }

        }

        if (conciertoEntity.get().getAforo() > estadioEntity.get().getCapacidad()){

            throw new IllegalOperationException("La capacidad del concierto excede la del estadio");

        }

        if (conciertoEntity.get().getPresupuesto() > estadioEntity.get().getPrecioAlquiler()){

            throw new IllegalOperationException("El presupuesto del concierto excede el alquiler del estadio");

        }

        conciertos.add(conciertoEntity.get());

        return conciertoEntity.get();
    }

}
