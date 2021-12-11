package com.epita.techgeek.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProfessorMapperTest {

    private ProfessorMapper professorMapper;

    @BeforeEach
    public void setUp() {
        professorMapper = new ProfessorMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(professorMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(professorMapper.fromId(null)).isNull();
    }
}
