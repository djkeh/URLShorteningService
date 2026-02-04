package com.kakaopay.urlshortening.service;

import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

@Service
class FeistelNetworkGenerator implements IndexGeneratorService {

    private final long minRange;
    private final long maxRange;
    private final int numRounds;
    private final long seed;
    private final int sideBits;
    private final long sideMask;
    private final AtomicLong currentIndex; // TODO: 단일 인스턴스 기준으로 구현함. 분산 아키텍처에서 다시 고려 필요

    public FeistelNetworkGenerator() {
        this(3521614606208L, 218340105584895L); // BASE62 "10000000" ~ "ZZZZZZZZ" 를 유도하는 약 214조 개의 unique index 생성 범위
    }

    public FeistelNetworkGenerator(long minRange, long maxRange) {
        // numRounds: 4 라운드로 충분한 혼돈과 확산 제공
        // sideBits: 2^24 * 2^24 = 2^48 (약 281조) 범위를 커버
        this(minRange, maxRange, 4, 0L, 24, (1L << 24) - 1, new AtomicLong(0L));
    }

    public FeistelNetworkGenerator(long minRange, long maxRange, int numRounds, long seed, int sideBits, long sideMask, AtomicLong currentIndex) {
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.numRounds = numRounds;
        this.seed = seed;
        this.sideBits = sideBits;
        this.sideMask = sideMask;
        this.currentIndex = currentIndex;

        if (maxRange - minRange > (1L << (sideBits * 2))) {
            throw new IllegalArgumentException("범위(" + (maxRange - minRange) + ")가 너무 큽니다. sideBits 값을 늘려주세요.");
        }
    }

    @Override
    public long nextValue() {
        if (!hasNext()) {
            throw new NoSuchElementException("더 이상 생성할 수 있는 고유한 값이 없습니다.");
        }

        long result = encrypt(currentIndex.get());

        // Cycle-walking: 결과가 범위를 벗어나면 안으로 들어올 때까지 재암호화
        while (result >= maxRange - minRange) {
            result = encrypt(result);
        }

        currentIndex.getAndIncrement(); // TODO: 단일 인스턴스 기준으로 구현함. 분산 아키텍처에서 다시 고려 필요
        return result + minRange;
    }

    @Override
    public boolean hasNext() {
        return currentIndex.get() < maxRange - minRange;
    }

    /**
     * Feistel 암호화: 숫자를 일대일 대응으로 뒤섞음
     */
    private long encrypt(long value) {
        long left = value >>> sideBits;
        long right = value & sideMask;

        for (int i = 0; i < numRounds; i++) {
            long nextLeft = right;
            long nextRight = left ^ hash(right, i);
            left = nextLeft;
            right = nextRight;
        }
        return (left << sideBits) | right;
    }

    /**
     * 간단한 해시 함수 (라운드 함수 F)
     */
    private long hash(long value, int round) {
        long x = value ^ (round * 0xDEADBEEFL) ^ seed;
        x = ((x >>> 16) ^ x) * 0x45d9f3bL;
        x = ((x >>> 16) ^ x) * 0x45d9f3bL;
        x = (x >>> 16) ^ x;
        return x & sideMask;
    }

}
