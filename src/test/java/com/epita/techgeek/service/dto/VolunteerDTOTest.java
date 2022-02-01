package com.epita.techgeek.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epita.techgeek.web.rest.TestUtil;

public class VolunteerDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VolunteerDTO.class);
        VolunteerDTO volunteerDTO1 = new VolunteerDTO();
        volunteerDTO1.setId(1L);
        VolunteerDTO volunteerDTO2 = new VolunteerDTO();
        assertThat(volunteerDTO1).isNotEqualTo(volunteerDTO2);
        volunteerDTO2.setId(volunteerDTO1.getId());
        assertThat(volunteerDTO1).isEqualTo(volunteerDTO2);
        volunteerDTO2.setId(2L);
        assertThat(volunteerDTO1).isNotEqualTo(volunteerDTO2);
        volunteerDTO1.setId(null);
        assertThat(volunteerDTO1).isNotEqualTo(volunteerDTO2);
    }
}
