package ecourse.base.usermanagement.domain.FundamentalClasses;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

@Embeddable
public class BirthDate implements ValueObject {

    private String birthDate;

    private BirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    protected BirthDate() {

    }

    public static boolean isValidBirthDate(String birthDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate date = LocalDate.parse(birthDate, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean isFutureBirthDate(String birthDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate date = LocalDate.parse(birthDate, formatter);
            return date.isAfter(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static BirthDate valueOf(String birthDate) {
        if (!isValidBirthDate(birthDate)) throw new IllegalArgumentException("Invalid birth date");
        if (isFutureBirthDate(birthDate)) throw new IllegalArgumentException("Birth date cannot be in the future");
        return new BirthDate(birthDate);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BirthDate birthDate1 = (BirthDate) o;
        return Objects.equals(birthDate, birthDate1.birthDate);
    }

    public String birthDate() {
        return this.birthDate;
    }

    @Override
    public String toString() {
        return birthDate;
    }
}
