package com.allanvital.shortener.domain.id;

import io.seruco.encoding.base62.Base62;
import org.springframework.stereotype.Component;

/**
 * without hashid, just to simplify
 *
 * @author Allan Vital (https://allanvital.com)
 */
@Component
public class Base62Handler {

    private final Base62 base62;

    public Base62Handler(Base62 base62) {
        this.base62 = base62;
    }

    public Long decode(String toDecode) {
        byte[] decode = base62.decode(toDecode.getBytes());
        String number = new String(decode);
        return Long.parseLong(number);
    }

    public String encode(Long idToEncode) {
        byte[] encoded = base62.encode(String.valueOf(idToEncode).getBytes());
        return new String(encoded);
    }

}
