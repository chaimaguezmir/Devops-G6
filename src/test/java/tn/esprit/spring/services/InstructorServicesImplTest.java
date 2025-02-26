package tn.esprit.spring.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InstructorServicesImplTest {

    @Mock
    private IInstructorRepository instructorRepository;

    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private InstructorServicesImpl instructorServices;

    @Test
    void addInstructor() {
        Instructor instructor = new Instructor();
        instructor.setNumInstructor(1L);
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor savedInstructor = instructorServices.addInstructor(instructor);

        assertNotNull(savedInstructor);
        assertEquals(1L, savedInstructor.getNumInstructor());
    }

    @Test
    void retrieveAllInstructors() {
        Instructor instructor1 = new Instructor();
        Instructor instructor2 = new Instructor();
        List<Instructor> instructorList = Arrays.asList(instructor1, instructor2);
        when(instructorRepository.findAll()).thenReturn(instructorList);

        List<Instructor> retrievedInstructors = instructorServices.retrieveAllInstructors();

        assertNotNull(retrievedInstructors);
        assertEquals(2, retrievedInstructors.size());
    }

    @Test
    void updateInstructor() {
        Instructor instructor = new Instructor();
        instructor.setNumInstructor(1L);
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor updatedInstructor = instructorServices.updateInstructor(instructor);

        assertNotNull(updatedInstructor);
        assertEquals(1L, updatedInstructor.getNumInstructor());
    }

    @Test
    void retrieveInstructor() {
        Instructor instructor = new Instructor();
        instructor.setNumInstructor(1L);
        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));

        Instructor retrievedInstructor = instructorServices.retrieveInstructor(1L);

        assertNotNull(retrievedInstructor);
        assertEquals(1L, retrievedInstructor.getNumInstructor());
    }

    @Test
    void addInstructorAndAssignToCourse() {
        Instructor instructor = new Instructor();
        instructor.setNumInstructor(1L);
        Course course = new Course();
        course.setNumCourse(1L);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(instructorRepository.save(any(Instructor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Instructor savedInstructor = instructorServices.addInstructorAndAssignToCourse(instructor, 1L);

        assertNotNull(savedInstructor);
        assertEquals(1, savedInstructor.getCourses().size());
        assertTrue(savedInstructor.getCourses().contains(course));
    }

}
