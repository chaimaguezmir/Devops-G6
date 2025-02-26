package tn.esprit.spring.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.repositories.IPisteRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PisteServicesImplTest {

    @Mock
    IPisteRepository pisteRepository;

    @InjectMocks
    PisteServicesImpl pisteServices;

    @Test
    void retrieveAllPistes() {
        Piste piste1 = new Piste();
        Piste piste2 = new Piste();
        when(pisteRepository.findAll()).thenReturn(Arrays.asList(piste1, piste2));

        List<Piste> pistes = pisteServices.retrieveAllPistes();

        assertNotNull(pistes);
        assertEquals(2, pistes.size());
        verify(pisteRepository, times(1)).findAll();
    }

    @Test
    void addPiste() {
        Piste piste = new Piste();
        piste.setNumPiste(1L);
        when(pisteRepository.save(piste)).thenReturn(piste);

        Piste addedPiste = pisteServices.addPiste(piste);

        assertNotNull(addedPiste);
        assertEquals(1L, addedPiste.getNumPiste());
        verify(pisteRepository, times(1)).save(piste);
    }

    @Test
    void removePiste() {
        doNothing().when(pisteRepository).deleteById(1L);

        pisteServices.removePiste(1L);

        verify(pisteRepository, times(1)).deleteById(1L);
    }

    @Test
    void retrievePiste() {
        Piste piste = new Piste();
        piste.setNumPiste(1L);
        when(pisteRepository.findById(1L)).thenReturn(Optional.of(piste));

        Piste retrievedPiste = pisteServices.retrievePiste(1L);

        assertNotNull(retrievedPiste);
        assertEquals(1L, retrievedPiste.getNumPiste());
        verify(pisteRepository, times(1)).findById(1L);
    }
}
