package com.epita.techgeek.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epita.techgeek.web.rest.TestUtil;

public class UploadfileDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UploadfileDTO.class);
        UploadfileDTO uploadfileDTO1 = new UploadfileDTO();
        uploadfileDTO1.setId(1L);
        UploadfileDTO uploadfileDTO2 = new UploadfileDTO();
        assertThat(uploadfileDTO1).isNotEqualTo(uploadfileDTO2);
        uploadfileDTO2.setId(uploadfileDTO1.getId());
        assertThat(uploadfileDTO1).isEqualTo(uploadfileDTO2);
        uploadfileDTO2.setId(2L);
        assertThat(uploadfileDTO1).isNotEqualTo(uploadfileDTO2);
        uploadfileDTO1.setId(null);
        assertThat(uploadfileDTO1).isNotEqualTo(uploadfileDTO2);
    }
}
