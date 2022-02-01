package com.epita.techgeek.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epita.techgeek.web.rest.TestUtil;

public class InfluencerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Influencer.class);
        Influencer influencer1 = new Influencer();
        influencer1.setId(1L);
        Influencer influencer2 = new Influencer();
        influencer2.setId(influencer1.getId());
        assertThat(influencer1).isEqualTo(influencer2);
        influencer2.setId(2L);
        assertThat(influencer1).isNotEqualTo(influencer2);
        influencer1.setId(null);
        assertThat(influencer1).isNotEqualTo(influencer2);
    }
}
