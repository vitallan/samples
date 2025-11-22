package com.allanvital.shortener;

import io.seruco.encoding.base62.Base62;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Allan Vital (https://allanvital.com)
 */
public class Base62Test {

    private final Base62 base62 = Base62.createInstance();

    @Test
    public void test1() {
        assertEncodeDecode(1L, "n");
        assertEncodeDecode(10000L, "3ibJF44");
        assertEncodeDecode(10001L, "3ibJF45");
        assertEncodeDecode(10002L, "3ibJF46");
        assertEncodeDecode(10003L, "3ibJF47");
    }

    private void assertEncodeDecode(Long toEncode, String expected) {
        byte[] encoded = base62.encode(String.valueOf(toEncode).getBytes());
        assertEquals(expected, new String(encoded));
    }

}
