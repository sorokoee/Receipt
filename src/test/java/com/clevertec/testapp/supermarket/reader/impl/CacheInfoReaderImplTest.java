package com.clevertec.testapp.supermarket.reader.impl;

import com.clevertec.testapp.supermarket.cache.impl.LRUCacheImpl;
import com.clevertec.testapp.supermarket.reader.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CacheInfoReaderImplTest {
    Reader reader;

    @BeforeEach
    void setUp() {
    reader=new CacheInfoReaderImpl();
    }
    @Test
    void checkSizeOfData(){
        int actualResult=reader.read().length;
        assertThat(actualResult).isEqualTo(2);
    }
    @Test
    void checkData(){
        String className=reader.read()[0];
        String capacity=reader.read()[1];
        assertAll(
                ()->assertThat(className).isEqualTo("LRUCacheImpl"),
                ()->assertThat(capacity).isEqualTo("5")
        );
    }

}