package com.epita.techgeek.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epita.techgeek.web.rest.TestUtil;

public class VolunteerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Volunteer.class);
        Volunteer volunteer1 = new Volunteer();
        volunteer1.setId(1L);
        Volunteer volunteer2 = new Volunteer();
        volunteer2.setId(volunteer1.getId());
        assertThat(volunteer1).isEqualTo(volunteer2);
        volunteer2.setId(2L);
        assertThat(volunteer1).isNotEqualTo(volunteer2);
        volunteer1.setId(null);
        assertThat(volunteer1).isNotEqualTo(volunteer2);
    }
}
