package com.epita.techgeek.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LevelMapperTest {

    private LevelMapper levelMapper;

    @BeforeEach
    public void setUp() {
        levelMapper = new LevelMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(levelMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(levelMapper.fromId(null)).isNull();
    }
}
