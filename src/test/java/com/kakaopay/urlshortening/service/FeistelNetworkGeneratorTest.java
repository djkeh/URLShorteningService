package com.kakaopay.urlshortening.service;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.random.RandomGenerator;
import java.util.stream.LongStream;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FeistelNetworkGeneratorTest {

    private final IndexGeneratorService sut = new FeistelNetworkGenerator(
            10L, // 인덱스 최소값
            20L, // 인덱스 최대값
            4,
            RandomGenerator.getDefault().nextInt(10),
            3, // 용량: 2^3 * 2 = 16
            (1L << 3) - 1, // == 7, 용량의 절반 마스크
            new AtomicLong(0L)
    );

    @RepeatedTest(100)
    void everyGeneratedIndexShouldBeUnique() {
        var expectedSize = 10;
        List<Long> list = LongStream.range(0L, expectedSize)
                .map(a -> sut.nextValue())
                .boxed()
                .toList();
        Set<Long> actual = new HashSet<>(list);

        System.out.println("(size) " + list.size() + " -> " + list);
        assertThat(actual.size(), is(expectedSize));
    }

    @Test
    void shouldNotGenerateIndexMoreThanItsCapacity() {
        LongStream.range(0L, 10L).forEach(a -> sut.nextValue());

        var t = assertThrows(NoSuchElementException.class, sut::nextValue);

        assertThat(t.getMessage(), is(equalTo("더 이상 생성할 수 있는 고유한 값이 없습니다.")));
    }

    @Test
    void shouldNotBeInitializedWhenRangeIsTooHuge() {
        System.out.println((1L << (2 * 2)));
        var t = assertThrows(
                IllegalArgumentException.class,
                () -> new FeistelNetworkGenerator(
                        10L,
                        20L, // 범위 크기: 10 ~ 20 -> 10
                        4,
                        0L,
                        1, // 용량: 2^1 * 2^1 = 4, 부족함
                        1L,
                        new AtomicLong(0L)
                )
        );

        assertThat(t.getMessage(), containsString("너무 큽니다. sideBits 값을 늘려주세요."));
    }

}
