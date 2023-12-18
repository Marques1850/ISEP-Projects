package ecourse.base.eCourseSystemUser;


public class TeacherMapper {

    public eCourseSystemUserDto toTeacherDTO(eCourseSystemUser Teacher) {
        return new eCourseSystemUserDto(Teacher.email.toString(), Teacher.systemUser().toString(), Teacher.eCourseUserNIF().toString(), Teacher.eCourseUserBirthDate().birthDate(),Teacher.teacherAcronym().acronym());
    }
}
