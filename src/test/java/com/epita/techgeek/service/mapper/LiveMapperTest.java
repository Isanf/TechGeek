package com.epita.techgeek.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LiveMapperTest {

    private LiveMapper liveMapper;

    @BeforeEach
    public void setUp() {
        liveMapper = new LiveMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(liveMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(liveMapper.fromId(null)).isNull();
    }
}
