package com.epita.techgeek.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LanguageMapperTest {

    private LanguageMapper languageMapper;

    @BeforeEach
    public void setUp() {
        languageMapper = new LanguageMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(languageMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(languageMapper.fromId(null)).isNull();
    }
}
