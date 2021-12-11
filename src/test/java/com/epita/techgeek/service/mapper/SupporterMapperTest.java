package com.epita.techgeek.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SupporterMapperTest {

    private SupporterMapper supporterMapper;

    @BeforeEach
    public void setUp() {
        supporterMapper = new SupporterMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(supporterMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(supporterMapper.fromId(null)).isNull();
    }
}
