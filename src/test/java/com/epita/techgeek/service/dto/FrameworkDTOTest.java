package com.epita.techgeek.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epita.techgeek.web.rest.TestUtil;

public class FrameworkDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FrameworkDTO.class);
        FrameworkDTO frameworkDTO1 = new FrameworkDTO();
        frameworkDTO1.setId(1L);
        FrameworkDTO frameworkDTO2 = new FrameworkDTO();
        assertThat(frameworkDTO1).isNotEqualTo(frameworkDTO2);
        frameworkDTO2.setId(frameworkDTO1.getId());
        assertThat(frameworkDTO1).isEqualTo(frameworkDTO2);
        frameworkDTO2.setId(2L);
        assertThat(frameworkDTO1).isNotEqualTo(frameworkDTO2);
        frameworkDTO1.setId(null);
        assertThat(frameworkDTO1).isNotEqualTo(frameworkDTO2);
    }
}
