package com.epita.techgeek.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FrameworkMapperTest {

    private FrameworkMapper frameworkMapper;

    @BeforeEach
    public void setUp() {
        frameworkMapper = new FrameworkMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(frameworkMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(frameworkMapper.fromId(null)).isNull();
    }
}
