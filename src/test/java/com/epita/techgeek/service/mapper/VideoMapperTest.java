package com.epita.techgeek.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class VideoMapperTest {

    private VideoMapper videoMapper;

    @BeforeEach
    public void setUp() {
        videoMapper = new VideoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(videoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(videoMapper.fromId(null)).isNull();
    }
}
