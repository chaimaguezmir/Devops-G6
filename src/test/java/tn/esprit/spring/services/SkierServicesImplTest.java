package tn.esprit.spring.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SkierServicesImplTest {

    @Mock
    private ISkierRepository skierRepository;

    @Mock
    private IPisteRepository pisteRepository;

    @Mock
    private ICourseRepository courseRepository;

    @Mock
    private IRegistrationRepository registrationRepository;

    @Mock
    private ISubscriptionRepository subscriptionRepository;

    @InjectMocks
    private SkierServicesImpl skierServices;

    @Test
    void retrieveAllSkiers() {
        Skier skier1 = new Skier();
        Skier skier2 = new Skier();
        List<Skier> skiers = Arrays.asList(skier1, skier2);
        when(skierRepository.findAll()).thenReturn(skiers);

        List<Skier> retrievedSkiers = skierServices.retrieveAllSkiers();

        assertNotNull(retrievedSkiers);
        assertEquals(2, retrievedSkiers.size());
    }

    @Test
    void addSkier() {
        Skier skier = new Skier();
        skier.setFirstName("John");
        skier.setLastName("Doe");

        Subscription subscription = new Subscription();
        subscription.setTypeSub(TypeSubscription.ANNUAL);
        subscription.setStartDate(LocalDate.now()); // Set a valid start date

        skier.setSubscription(subscription);

        when(skierRepository.save(any(Skier.class))).thenReturn(skier);

        Skier savedSkier = skierServices.addSkier(skier);

        assertNotNull(savedSkier);
        assertNotNull(savedSkier.getSubscription().getEndDate());
    }


    @Test
    void removeSkier() {
        doNothing().when(skierRepository).deleteById(1L);
        skierServices.removeSkier(1L);
        verify(skierRepository, times(1)).deleteById(1L);
    }

    @Test
    void retrieveSkier() {
        Skier skier = new Skier();
        skier.setNumSkier(1L);
        when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));

        Skier retrievedSkier = skierServices.retrieveSkier(1L);

        assertNotNull(retrievedSkier);
        assertEquals(1L, retrievedSkier.getNumSkier());
    }
}
