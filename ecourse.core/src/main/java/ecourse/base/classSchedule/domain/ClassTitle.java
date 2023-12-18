package ecourse.base.classSchedule.domain;

import eapli.framework.domain.model.ValueObject;

import java.util.Objects;

public class ClassTitle implements ValueObject, Comparable<ClassTitle> {
    private final String classTitle;

    public ClassTitle(String classTitle) {
        this.classTitle = classTitle;
    }
    public ClassTitle() {
        classTitle = null;
    }

    public String classTitle() {
        return this.classTitle;
    }

    public static ClassTitle valueOf(String classTitle) {
        return new ClassTitle(classTitle);
    }

    @Override
    public int compareTo(ClassTitle o) {
        if(o.classTitle.equals(this.classTitle)) return 1;
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassTitle)) return false;
        ClassTitle that = (ClassTitle) o;
        return Objects.equals(classTitle, that.classTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classTitle);
    }
}
