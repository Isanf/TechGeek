package com.epita.techgeek.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epita.techgeek.web.rest.TestUtil;

public class InfluencerDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InfluencerDTO.class);
        InfluencerDTO influencerDTO1 = new InfluencerDTO();
        influencerDTO1.setId(1L);
        InfluencerDTO influencerDTO2 = new InfluencerDTO();
        assertThat(influencerDTO1).isNotEqualTo(influencerDTO2);
        influencerDTO2.setId(influencerDTO1.getId());
        assertThat(influencerDTO1).isEqualTo(influencerDTO2);
        influencerDTO2.setId(2L);
        assertThat(influencerDTO1).isNotEqualTo(influencerDTO2);
        influencerDTO1.setId(null);
        assertThat(influencerDTO1).isNotEqualTo(influencerDTO2);
    }
}
