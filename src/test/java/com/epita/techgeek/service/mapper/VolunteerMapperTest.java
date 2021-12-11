package com.epita.techgeek.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class VolunteerMapperTest {

    private VolunteerMapper volunteerMapper;

    @BeforeEach
    public void setUp() {
        volunteerMapper = new VolunteerMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(volunteerMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(volunteerMapper.fromId(null)).isNull();
    }
}
