package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.exceptions.CourseNotFoundException;
import tn.esprit.spring.exceptions.InstructorNotFoundException;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class InstructorServicesImpl implements IInstructorServices {

    private IInstructorRepository instructorRepository;
    private ICourseRepository courseRepository;

    @Override
    public Instructor addInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    @Override
    public List<Instructor> retrieveAllInstructors() {
        return instructorRepository.findAll();
    }

    @Override
    public Instructor updateInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    @Override
    public Instructor retrieveInstructor(Long numInstructor) {
        return instructorRepository.findById(numInstructor)
                .orElseThrow(() -> new InstructorNotFoundException("Instructor with ID " + numInstructor + " not found"));
    }

    @Override
    public Instructor addInstructorAndAssignToCourse(Instructor instructor, Long numCourse) {
        Course course = courseRepository.findById(numCourse)
                .orElseThrow(() -> new CourseNotFoundException("Course with ID " + numCourse + " not found"));

        Set<Course> courseSet = instructor.getCourses() != null ? instructor.getCourses() : new HashSet<>();
        courseSet.add(course);
        instructor.setCourses(courseSet);

        return instructorRepository.save(instructor);
    }

    // Nouvelle méthode pour retirer un cours d'un instructeur
    public Instructor removeCourseFromInstructor(Long numInstructor, Long numCourse) {
        Instructor instructor = instructorRepository.findById(numInstructor)
                .orElseThrow(() -> new InstructorNotFoundException("Instructor with ID " + numInstructor + " not found"));

        Course course = courseRepository.findById(numCourse)
                .orElseThrow(() -> new CourseNotFoundException("Course with ID " + numCourse + " not found"));

        if (instructor.getCourses() != null) {
            instructor.getCourses().remove(course);
        }

        return instructorRepository.save(instructor);
    }

    // Nouvelle méthode pour récupérer les cours d'un instructeur
    public Set<Course> getCoursesByInstructor(Long numInstructor) {
        Instructor instructor = instructorRepository.findById(numInstructor)
                .orElseThrow(() -> new InstructorNotFoundException("Instructor with ID " + numInstructor + " not found"));

        return instructor.getCourses();
    }
}
