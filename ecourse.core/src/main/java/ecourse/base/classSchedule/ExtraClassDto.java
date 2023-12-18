package ecourse.base.classSchedule;

import ecourse.base.eCourseSystemUser.eCourseSystemUser;

import java.util.List;

public class ExtraClassDto {
    public String title;

    public int duration;
    public String date;
    public List<eCourseSystemUser> studentList;

    public ExtraClassDto(String title, int duration, String date, List<eCourseSystemUser> studentList) {
        this.title = title;
        this.duration = duration;
        this.date = date;
        this.studentList = studentList;
    }

    public ExtraClassDto() {}

}
