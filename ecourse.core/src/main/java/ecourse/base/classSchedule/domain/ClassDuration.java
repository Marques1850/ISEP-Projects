package ecourse.base.classSchedule.domain;

import eapli.framework.domain.model.ValueObject;

public class ClassDuration implements ValueObject, Comparable<ClassDuration> {
    private final int duration;

    public ClassDuration(int duration) {
        this.duration = duration;
    }
    public ClassDuration() {
        duration = Integer.parseInt(null);
    }

    public int duration() {
        return this.duration;
    }

    public static ClassDuration valueOf(int duration) {
        return new ClassDuration(duration);
    }

    @Override
    public int compareTo(ClassDuration o) {
        if(o.duration == this.duration) return 1;
        return 0;
    }
}
