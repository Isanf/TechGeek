package com.epita.techgeek.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epita.techgeek.web.rest.TestUtil;

public class LiveDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LiveDTO.class);
        LiveDTO liveDTO1 = new LiveDTO();
        liveDTO1.setId(1L);
        LiveDTO liveDTO2 = new LiveDTO();
        assertThat(liveDTO1).isNotEqualTo(liveDTO2);
        liveDTO2.setId(liveDTO1.getId());
        assertThat(liveDTO1).isEqualTo(liveDTO2);
        liveDTO2.setId(2L);
        assertThat(liveDTO1).isNotEqualTo(liveDTO2);
        liveDTO1.setId(null);
        assertThat(liveDTO1).isNotEqualTo(liveDTO2);
    }
}
