package cl.tsoft.labs.ut.sample.rest.entitie.type;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

class RutTest {

    @Test
    @DisplayName("create empty rut")
    void create_empty_rut() {
        // Given
        Rut rut;

        // When
        rut = new Rut();

        // Then
        assertNotNull(rut);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/valid-ruts.csv")
    @DisplayName("should return true with valid rut")
    void should_return_true_with_valid_rut(long number, char digit) {
        // Given
        Rut rut = new Rut(number, digit);

        // When
        boolean isValid = rut.isValid();

        // Then
        assertTrue(isValid);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/invalid-ruts.csv")
    @DisplayName("should return false with invalid rut")
    void should_return_false_with_invalid_rut(long number, char digit) {
        // Given
        Rut rut = new Rut(number, digit);

        // When
        boolean isValid = rut.isValid();

        // Then
        assertFalse(isValid);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/valid-ruts.csv")
    @DisplayName("should return true with valid rut - static")
    void should_return_true_with_valid_rut_static(long number, char digit) {
        // Given
        // Son los parámetros de entrada

        // When
        boolean isValidStatic = Rut.isValid(number, digit);

        // Then
        assertTrue(isValidStatic);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/invalid-ruts.csv")
    @DisplayName("should return false with invalid rut - static")
    void should_return_false_with_invalid_rut_static(long number, char digit) {
        // Given
        // Son los parámetros de entrada

        // When
        boolean isValidStatic = Rut.isValid(number, digit);

        // Then
        assertFalse(isValidStatic);
    }

    @Test
    @DisplayName("should successful convert rut from valid string")
    void should_successful_convert_rut_from_valid_string() {
        // Given
        String rutText = "40262650-K";

        // When
        Rut rutObject = Rut.valueOf(rutText);

        // Then
        assertNotNull(rutObject);
    }

    @Test
    @DisplayName("should return null when valueOf with null string")
    void should_return_null_when_valueOf_with_null_string() {
        // Given
        String rutNull = null;

        // When
        Rut rut = Rut.valueOf(rutNull);

        // Then
        assertNull(rut);
    }

    @Test
    @DisplayName("should return null when valueOf with empty string")
    void should_return_null_when_valueOf_with_empty_string() {
        // Given
        String rutEmpty = "";

        // When
        Rut rut = Rut.valueOf(rutEmpty);

        // Then
        assertNull(rut);
    }

    @Test
    @DisplayName("should throw exception when valueOf with invalid format - with points")
    void should_throw_exception_when_valueOf_with_invalid_format() {
        // Given
        String rutInvalid = "12.345.678-0";

        // When
        Exception exception = assertThrowsExactly(IllegalArgumentException.class, () -> Rut.valueOf(rutInvalid));

        // Then
        assertEquals("rut has an invalid format", exception.getMessage());
    }

    @Test
    @DisplayName("should throw exception when valueOf with invalid format - no dash")
    void should_throw_exception_when_valueOf_with_invalid_format_no_dash() {
        // Given
        String rutInvalid = "123456780";

        // When
        Exception exception = assertThrowsExactly(IllegalArgumentException.class, () -> Rut.valueOf(rutInvalid));

        // Then
        assertEquals("rut has an invalid format", exception.getMessage());
    }

    @Test
    @DisplayName("should throw exception when valueOf with invalid format - invalid char in digit")
    void should_throw_exception_when_valueOf_with_invalid_format_invalid_char_digit() {
        // Given
        String rutInvalid = "12345678-L";

        // When
        Exception exception = assertThrowsExactly(IllegalArgumentException.class, () -> Rut.valueOf(rutInvalid));

        // Then
        assertEquals("rut has an invalid format", exception.getMessage());
    }

    @Test
    @DisplayName("should throw exception when valueOf with invalid format - no digit")
    void should_throw_exception_when_valueOf_with_invalid_format_no_digit() {
        // Given
        String rutInvalid = "12345678-";

        // When
        Exception exception = assertThrowsExactly(IllegalArgumentException.class, () -> Rut.valueOf(rutInvalid));

        // Then
        assertEquals("rut has an invalid format", exception.getMessage());
    }
}