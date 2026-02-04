package com.kakaopay.urlshortening.service;

import java.util.concurrent.ThreadLocalRandom;

public class SimpleRandomIndexGenerator implements IndexGeneratorService {

    /**
     * Generates index number between Base62 code "10000000" ~ "ZZZZZZZZ".
     * This can generate up to 214818490978688 index numbers.
     *
     * @return generated index number in certain range
     */
    @Override
    public long nextValue() {
        return ThreadLocalRandom.current().nextLong(3521614606208L, 218340105584895L);
    }

    @Override
    public boolean hasNext() {
        return true; // TODO: 대충 만듦
    }
}
