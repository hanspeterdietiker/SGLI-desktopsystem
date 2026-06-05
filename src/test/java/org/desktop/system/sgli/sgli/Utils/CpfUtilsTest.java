package org.desktop.system.sgli.sgli.Utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CpfUtilsTest {

    @Test
    void validCpfPassesChecksum() {
        assertTrue(CpfUtils.isValid("529.982.247-25"));
    }

    @Test
    void invalidChecksumFails() {
        assertFalse(CpfUtils.isValid("529.982.247-26"));
    }

    @Test
    void allSameDigitsFails() {
        assertFalse(CpfUtils.isValid("111.111.111-11"));
    }

    @Test
    void wrongFormatFails() {
        assertFalse(CpfUtils.isValid("52998224725"));
    }

    @Test
    void maskShowsFirstAndLastDigits() {
        assertEquals("529.***.***-25", CpfUtils.mask("529.982.247-25"));
    }

    @Test
    void maskHandlesNull() {
        assertEquals("", CpfUtils.mask(null));
    }
}
