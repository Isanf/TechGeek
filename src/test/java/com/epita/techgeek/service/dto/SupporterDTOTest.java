package com.epita.techgeek.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epita.techgeek.web.rest.TestUtil;

public class SupporterDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupporterDTO.class);
        SupporterDTO supporterDTO1 = new SupporterDTO();
        supporterDTO1.setId(1L);
        SupporterDTO supporterDTO2 = new SupporterDTO();
        assertThat(supporterDTO1).isNotEqualTo(supporterDTO2);
        supporterDTO2.setId(supporterDTO1.getId());
        assertThat(supporterDTO1).isEqualTo(supporterDTO2);
        supporterDTO2.setId(2L);
        assertThat(supporterDTO1).isNotEqualTo(supporterDTO2);
        supporterDTO1.setId(null);
        assertThat(supporterDTO1).isNotEqualTo(supporterDTO2);
    }
}
