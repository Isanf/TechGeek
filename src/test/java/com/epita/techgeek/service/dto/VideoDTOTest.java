package com.epita.techgeek.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epita.techgeek.web.rest.TestUtil;

public class VideoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VideoDTO.class);
        VideoDTO videoDTO1 = new VideoDTO();
        videoDTO1.setId(1L);
        VideoDTO videoDTO2 = new VideoDTO();
        assertThat(videoDTO1).isNotEqualTo(videoDTO2);
        videoDTO2.setId(videoDTO1.getId());
        assertThat(videoDTO1).isEqualTo(videoDTO2);
        videoDTO2.setId(2L);
        assertThat(videoDTO1).isNotEqualTo(videoDTO2);
        videoDTO1.setId(null);
        assertThat(videoDTO1).isNotEqualTo(videoDTO2);
    }
}
