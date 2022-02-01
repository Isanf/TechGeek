package com.epita.techgeek.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UploadfileMapperTest {

    private UploadfileMapper uploadfileMapper;

    @BeforeEach
    public void setUp() {
        uploadfileMapper = new UploadfileMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(uploadfileMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(uploadfileMapper.fromId(null)).isNull();
    }
}
