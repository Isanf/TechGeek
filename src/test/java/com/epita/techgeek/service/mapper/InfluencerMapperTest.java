package com.epita.techgeek.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class InfluencerMapperTest {

    private InfluencerMapper influencerMapper;

    @BeforeEach
    public void setUp() {
        influencerMapper = new InfluencerMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(influencerMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(influencerMapper.fromId(null)).isNull();
    }
}
