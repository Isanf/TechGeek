package com.epita.techgeek.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epita.techgeek.web.rest.TestUtil;

public class FrameworkTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Framework.class);
        Framework framework1 = new Framework();
        framework1.setId(1L);
        Framework framework2 = new Framework();
        framework2.setId(framework1.getId());
        assertThat(framework1).isEqualTo(framework2);
        framework2.setId(2L);
        assertThat(framework1).isNotEqualTo(framework2);
        framework1.setId(null);
        assertThat(framework1).isNotEqualTo(framework2);
    }
}
