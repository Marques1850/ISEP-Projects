package ecourse.base.usermanagement.domain.Course;



import javax.persistence.Column;

public class CourseDto {

    public String code;
    public  String name;
    public String status;
    public String regent;
    public String minStudents;
    public String maxStudents;
    public String numStudents;
    public String description;




    public CourseDto(String code, String name, String status, String regent, String minStudents, String maxStudents, String numStudents, String description) {
        this.code = code;
        this.name = name;
        this.status = status;
        this.regent = regent;
        this.minStudents = minStudents;
        this.maxStudents = maxStudents;
        this.numStudents = numStudents;
        this.description = description;
    }

    @Override
    public String toString() {
        return "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", regent='" + regent + '\'' +
                ", minStudents='" + minStudents + '\'' +
                ", maxStudents='" + maxStudents + '\'' +
                ", numStudents='" + numStudents + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }
}
