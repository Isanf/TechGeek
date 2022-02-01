package com.epita.techgeek.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epita.techgeek.web.rest.TestUtil;

public class SupporterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Supporter.class);
        Supporter supporter1 = new Supporter();
        supporter1.setId(1L);
        Supporter supporter2 = new Supporter();
        supporter2.setId(supporter1.getId());
        assertThat(supporter1).isEqualTo(supporter2);
        supporter2.setId(2L);
        assertThat(supporter1).isNotEqualTo(supporter2);
        supporter1.setId(null);
        assertThat(supporter1).isNotEqualTo(supporter2);
    }
}
