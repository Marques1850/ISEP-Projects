package ecourse.base.usermanagement.domain.FundamentalClasses;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class Grade implements ValueObject {

    public static boolean isValidGrade(double grade) {
        return grade >= 0 && grade <= 20;
    }
}
