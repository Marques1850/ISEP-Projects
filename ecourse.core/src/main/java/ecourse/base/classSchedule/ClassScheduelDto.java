package ecourse.base.classSchedule;

public class ClassScheduelDto {
    public String title;
    public int duration;
    public String date;

    public ClassScheduelDto(String title, int duration, String date) {
        this.title = title;
        this.duration = duration;
        this.date = date;
    }

    public ClassScheduelDto() {}

    public String title() {
        return title;
    }

    public int duration() {
        return duration;
    }

    public String date() {
        return date;
    }
}
