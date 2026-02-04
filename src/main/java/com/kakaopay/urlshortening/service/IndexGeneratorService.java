package com.kakaopay.urlshortening.service;

public interface IndexGeneratorService {

    /**
     * 다음 인덱스 값을 반환한다.
     */
    long nextValue();

    /**
     * 다음 인덱스 값이 있는지 확인한다.
     */
    boolean hasNext();
}
