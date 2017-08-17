package com.kakaopay.urlshortening.utils;

/**
 * Base62 Encoder / Decoder<br><br>
 * converts long digit to Base62 String and vice versa.
 * 
 * @author Uno Kim (djkehh@gmail.com)
 */
public class Base62Codec {

    private final String CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final int LENGTH = CHARACTERS.length();

    /**
     * Decodes Base62 string to long number.
     * 
     * @param base62String Base62 encoded string
     * @return decoded long number
     */
    public long decode(String base62String) {
        long base62Code = 0L;

        for (int i = 0; i < base62String.length(); ++i) {
            base62Code += CHARACTERS.indexOf(base62String.charAt(i)) * Math.pow(LENGTH, (base62String.length() - 1) - i);
        }

        return base62Code;
    }

    /**
     * Encodes long number to Base62 string.
     * 
     * @param number long number
     * @return encoded Base62 string
     */
    public String encode(long number) {
        StringBuilder sb = new StringBuilder();

        do {
            sb.append(CHARACTERS.charAt((int) (number % LENGTH)));
            number /= LENGTH;
        } while (number != 0);

        return sb.reverse().toString();
    }

    public String getCHARACTERS() {
        return CHARACTERS;
    }

    public int getLENGTH() {
        return LENGTH;
    }

}
