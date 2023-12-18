package ecourse.base.boardManagment.domain;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    @Test
    void isBelowOr20AndAboveOr1() {
        assertFalse(new Cell().isBelowOr20AndAboveOr1(0));
        assertTrue(new Cell().isBelowOr20AndAboveOr1(1));

    }

    @Test
    void isBelowOr10AndAboveOr1() {
        assertFalse(new Cell().isBelowOr10AndAboveOr1(0));
        assertTrue(new Cell().isBelowOr10AndAboveOr1(1));
    }
}