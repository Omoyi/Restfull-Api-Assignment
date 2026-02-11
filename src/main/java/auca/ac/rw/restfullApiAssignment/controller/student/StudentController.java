package auca.ac.rw.restfullApiAssignment.controller.student;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import auca.ac.rw.restfullApiAssignment.model.student.Student;

@RestController
@RequestMapping
public class StudentController {

    private List<Student> students = new ArrayList<>();

    public StudentController() {
        students.add(new Student(23684L, "Rachel", "Uwimana", "rach.uwi@auca.ac.rw", "Software Engineering", 3.8)); 
        students.add(new Student(27685L, "John", "Manzi", "john.manzi@auca.ac.rw", "Computer Science", 3.1)); 
        students.add(new Student(29685L, "Faith", "Mwiza", "faith.mwiza@auca.ac.rw", "Networking", 3.0)); 
        students.add(new Student(21685L, "Alice", "Niyonsaba", "alice.niyo@auca.ac.rw", "Computer Science", 3.2)); 
        students.add(new Student(25685L, "Bob", "Kamanzi", "bob.kamanzi@auca.ac.rw", "Information Management", 2.8)); 
    }

    @GetMapping("/api/students")
    public List<Student> getAllStudents() {
        return students;
    }

    @GetMapping("/api/students/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long studentId) {
        return students.stream()
            .filter(student -> student.getStudentId().equals(studentId))
            .findFirst()
            .map(student -> new ResponseEntity<>(student, HttpStatus.OK)) 
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND)); 
    }

    @GetMapping("/api/students/major/{major}")
    public List<Student> getStudentByMajor(@PathVariable String major) {
        return students.stream()
                .filter(student -> student.getMajor().equalsIgnoreCase(major))
                .collect(Collectors.toList());
    }

    @GetMapping("/api/students/filter")
    public List<Student> filterByGpa(@RequestParam Double minGpa) {
        return students.stream()
                .filter(student -> student.getGpa() >= minGpa)
                .collect(Collectors.toList());
    }

    @PostMapping("/api/students")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        students.add(student);
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @PutMapping("/api/students/{studentId}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long studentId, @RequestBody Student updatedStudent) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentId().equals(studentId)) {
                students.set(i, updatedStudent);
                return new ResponseEntity<>(updatedStudent, HttpStatus.OK); 
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }    
}
