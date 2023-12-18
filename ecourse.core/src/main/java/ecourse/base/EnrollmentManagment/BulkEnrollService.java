package ecourse.base.EnrollmentManagment;

import ecourse.base.clientusermanagement.domain.MecanographicNumber;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.eCourseSystemUser.eCourseSystemUserDto;
import ecourse.base.eCourseSystemUser.eCourseSystemUserMapper;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;
import ecourse.base.usermanagement.domain.Course.Course;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BulkEnrollService {

    public Set<MecanographicNumber> parseCSV(String csvPath) throws FileNotFoundException {
        Set<MecanographicNumber> mecanographicNumbers = new TreeSet<>(); //TreeSet used to sort and save only unique numbers
        String line;
        String csvDelimiter = ","; //TODO: Check if this is the correct delimiter

        try (BufferedReader reader = new BufferedReader(new FileReader(csvPath))) {

            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(csvDelimiter);
                for (int i = 0; i < rowData.length; i++) {
                    mecanographicNumbers.add(new MecanographicNumber(rowData[i]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mecanographicNumbers;
    }
    public List<MecanographicNumber> validateStudents (List<MecanographicNumber> mecanographicNumbers, eCourseUserRepository repo){
        List<MecanographicNumber> corrected = new ArrayList<>();
        for (MecanographicNumber mecanographicNumber : mecanographicNumbers) {
            if(!repo.findByMeca(mecanographicNumber).isEmpty()) {
                corrected.add(mecanographicNumber);
            }
        }
        return corrected;
    }
    //how to enrollStudents
    public List<eCourseSystemUserDto> enrollStudents (Course course,String csvPath, eCourseUserRepository repo) throws FileNotFoundException {

        Set<MecanographicNumber> mecanographicNumbers = parseCSV(csvPath);//Parse CSV file
        List<MecanographicNumber> corrected = validateStudents(new ArrayList<>(mecanographicNumbers),repo);//Validate if students exist in the system

        List<eCourseSystemUserDto> studentsEnrolled=new ArrayList<>(); //List of students trully enrolled
        eCourseSystemUserMapper mapper=new eCourseSystemUserMapper(); //Mapper to convert from entity to DTO

        for (MecanographicNumber mecanographicNumber : corrected) { //For each student
            if(course.maxStudents()== course.numStudents()){
                course.disableEnrollments(); //Disable enrollments if max number of students is reached
                break;
            }
            eCourseSystemUser student=repo.findByMeca(mecanographicNumber).get(); //Get student
            studentsEnrolled.add(mapper.toDto(student)); //Add student to list of students enrolled
            student.setStudentCourse(course); //Set student course
            course.numStudents(course.numStudents()+1); //Increment number of students
            repo.save(student); //Save student
        }
        return studentsEnrolled;
    }
}
