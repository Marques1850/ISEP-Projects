package ecourse.base.usermanagement.domain.Course;


import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class CourseCode implements ValueObject, Comparable<CourseCode> {
    private final String courseCode;

    private CourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public CourseCode() {
        courseCode = "";
    }

    public static boolean isValidCode(String courseCode) {
        // Check if the code has 5 to 8 chars
        if (courseCode.length() < 5 || courseCode.length() >8) return false;

        // first char of string must be a letter
        if (!Character.isLetter(courseCode.charAt(0))) return false;

        // last char of string must be a letter or a number
        if (!Character.isLetterOrDigit(courseCode.charAt(courseCode.length()-1))) return false;

        // Check if it´s in CAPS, no more than 2 bars and has 1/0 digits
        int counterBar=0,counterDigits=0;
        for (char c : courseCode.toCharArray()) {
            if (!Character.isUpperCase(c) && c != '-' && c != '_' && !Character.isDigit(c)) {
                return false;
            }
            if (Character.isDigit(c)){
                counterDigits++;
            }
            if (c=='_' || c == '-'){
                counterBar++;
            }
            if ( counterDigits > 1 || counterBar > 2){
                return false;
            }
        }
        return true;
    }

    public static CourseCode valueOf(String courseCode) {
        if (!isValidCode(courseCode)) throw new IllegalArgumentException("Invalid Course Code");
        return new CourseCode(courseCode);
    }

    @Override
    public String toString() {
        return courseCode;
    }

    @Override
    public int compareTo(CourseCode o) {
        return this.courseCode.compareTo(o.courseCode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseCode)) return false;
        CourseCode that = (CourseCode) o;
        return Objects.equals(courseCode, that.courseCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseCode);
    }

    public String courseCode() {
        return this.courseCode;
    }
}
