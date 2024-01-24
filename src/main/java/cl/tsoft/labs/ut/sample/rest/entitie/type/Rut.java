package cl.tsoft.labs.ut.sample.rest.entitie.type;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Rut {
    private final long number;
    private final char digit;

    public Rut() {
        number = 0;
        digit = 'X';
    }

    public Rut(long number, char digit) {
        this.number = number;
        this.digit = digit;
    }

    public boolean isValid() {
        return Rut.isValid(this.number, this.digit);
    }

    public String toString() {
        return String.format("%d-%c", this.number, this.digit);
    }

    public static char calculateDigit(long number) {
        int sum = 0;
        for( int multiplier = 2; number != 0; number /= 10) {
            sum += (number % 10) * multiplier++;
            if(multiplier > 7) multiplier = 2;
        }

        int rest = 11 - sum%11;
        if(rest == 11)
            return '0';
        if(rest == 10)
            return 'K';

        return String.valueOf(rest).charAt(0);
    }

    public static boolean isValid(long number, char digit) {
        if(number <= 0 || number > 99999999)
            return false;

        return digit == calculateDigit(number);
    }

    public static Rut valueOf(String rut) throws IllegalArgumentException {
        if(rut == null || rut.isEmpty())
            return null;

        if(!rut.matches("^[0-9]{1,8}-(k|K|[0-9])$"))
            throw new IllegalArgumentException("rut has an invalid format");

        long number = Long.parseLong(rut.substring(0, rut.length() - 2));
        char digit = rut.substring(rut.length() - 1).toUpperCase().charAt(0);

        return new Rut(number, digit);
    }
}
