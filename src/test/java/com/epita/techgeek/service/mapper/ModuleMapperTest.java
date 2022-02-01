package com.epita.techgeek.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ModuleMapperTest {

    private ModuleMapper moduleMapper;

    @BeforeEach
    public void setUp() {
        moduleMapper = new ModuleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(moduleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(moduleMapper.fromId(null)).isNull();
    }
}
