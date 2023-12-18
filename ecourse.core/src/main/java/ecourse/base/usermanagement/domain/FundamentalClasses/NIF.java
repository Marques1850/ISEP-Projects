package ecourse.base.usermanagement.domain.FundamentalClasses;

import com.fasterxml.jackson.annotation.JsonFormat;
import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class NIF implements ValueObject {

    private String nif;

    private NIF(String nif) {
        this.nif = nif;
    }

    protected NIF() {

    }

    public static boolean isValidNIF(String nif) {
        // Check if the NIF has 9 digits
        if (nif.length() != 9) return false;

        // Check if the first digit is valid
        char firstDigit = nif.charAt(0);
        if (firstDigit != '1' && firstDigit != '2' && firstDigit != '5' && firstDigit != '6' &&
                firstDigit != '8' && firstDigit != '9') return false;

        // Calculate the check digit
        int checkSum = 0;
        for (int i = 0; i < 8; i++) {
            checkSum += (nif.charAt(i) - '0') * (10 - i);
        }
        int checkDigit = 11 - (checkSum % 11);
        if (checkDigit >= 10) checkDigit = 0;

        // Check if the check digit is correct
        return checkDigit == (nif.charAt(8) - '0');
    }

    public static NIF valueOf(String nif) {
        if (!isValidNIF(nif)) throw new IllegalArgumentException("Invalid NIF");
        return new NIF(nif);
    }

    public String nif() {
        return nif;
    }

    @Override
    public String toString() {
        return nif;
    }
}
