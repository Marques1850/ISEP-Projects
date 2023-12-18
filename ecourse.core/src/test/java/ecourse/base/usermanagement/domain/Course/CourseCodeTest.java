package ecourse.base.usermanagement.domain.Course;


import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

class CourseCodeTest {

    @Test
    void testForEmptyCourseCode() {
        String testCode= "";
        assertFalse(CourseCode.isValidCode(testCode));
    }

    @Test
    void testValidCourseCodes() {
        String testCode1 = "EAPLI";
        String testCode2 = "LAPR4";
        String testCode3 = "SEM_2_PI";
        String testCode4 = "JAVA-1";

        assertTrue(CourseCode.isValidCode(testCode1));
        assertTrue(CourseCode.isValidCode(testCode2));
        assertTrue(CourseCode.isValidCode(testCode3));
        assertTrue(CourseCode.isValidCode(testCode4));
    }

    @Test
    void testInvalidCourseCodes() {
        String testCode1 = "eapli";
        String testCode2 = "EApLI";
        String testCode3 = "EA___PLI";
        String testCode4 = "-EAPLI";
        String testCode5 = "ea@li";
        String testCode6 = "EAp";
        String testCode7 = "EAPLIIIIIIII";
        String testCode8 = "EAP123LI";
        String testCode9 = "122112";
        String testCode10 = "LAPR---R";
        String testCode11 = "EA___PLI";
        String testCode12 = "!EAPLI";
        String testCode13 = "1EAPLI";
        String testCode14 = "EAPLI_";

        assertFalse(CourseCode.isValidCode(testCode1));
        assertFalse(CourseCode.isValidCode(testCode2));
        assertFalse(CourseCode.isValidCode(testCode3));
        assertFalse(CourseCode.isValidCode(testCode4));
        assertFalse(CourseCode.isValidCode(testCode5));
        assertFalse(CourseCode.isValidCode(testCode6));
        assertFalse(CourseCode.isValidCode(testCode7));
        assertFalse(CourseCode.isValidCode(testCode8));
        assertFalse(CourseCode.isValidCode(testCode9));
        assertFalse(CourseCode.isValidCode(testCode10));
        assertFalse(CourseCode.isValidCode(testCode11));
        assertFalse(CourseCode.isValidCode(testCode12));
        assertFalse(CourseCode.isValidCode(testCode13));
        assertFalse(CourseCode.isValidCode(testCode14));
    }

    @Test
    void testCompareEqualCodes() {
        String testCode1= "EAPLI";
        String testCode2= "EAPLI";
        CourseCode code1 = CourseCode.valueOf(testCode1);
        CourseCode code2 = CourseCode.valueOf(testCode2);

        assertTrue(code1.equals(code2));
    }

    @Test
    void testCompareDistinctCodes() {
        String testCode1= "LAPR4";
        String testCode2= "EAPLI";
        CourseCode code1 = CourseCode.valueOf(testCode1);
        CourseCode code2 = CourseCode.valueOf(testCode2);

        assertFalse(code1.equals(code2));
    }

}