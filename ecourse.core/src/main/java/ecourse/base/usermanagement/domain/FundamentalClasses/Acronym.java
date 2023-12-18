package ecourse.base.usermanagement.domain.FundamentalClasses;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.util.HashSet;
import java.util.Set;
@Embeddable
public class Acronym implements ValueObject {

    private String acronym;

    private Acronym(String acronym) {
        this.acronym = acronym;
    }

    private static Set<String> usedAcronyms = new HashSet<>();

    protected Acronym() {

    }

    public static boolean isValidAcronym(String acronym) {
        // Check if the acronym has 3 letters
        if (acronym.length() != 3) return false;

        // Check if all letters are uppercase
        for (int i = 0; i < 3; i++) {
            if (!Character.isUpperCase(acronym.charAt(i))) return false;
        }

        // Check if the acronym is unique
        if (usedAcronyms.contains(acronym)) return false;

        // Add the acronym to the set of used acronyms
        usedAcronyms.add(acronym);

        return true;
    }

    public static Acronym valueOf(String acronym) {
        if (isValidAcronym(acronym)) {
            return new Acronym(acronym);
        } else {
            throw new IllegalArgumentException("Invalid acronym");
        }
    }

    public String acronym() {
        return acronym;
    }

    @Override
    public String toString() {
        return acronym;
    }
}
