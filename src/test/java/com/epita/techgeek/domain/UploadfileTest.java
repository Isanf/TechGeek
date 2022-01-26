package com.epita.techgeek.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epita.techgeek.web.rest.TestUtil;

public class UploadfileTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Uploadfile.class);
        Uploadfile uploadfile1 = new Uploadfile();
        uploadfile1.setId(1L);
        Uploadfile uploadfile2 = new Uploadfile();
        uploadfile2.setId(uploadfile1.getId());
        assertThat(uploadfile1).isEqualTo(uploadfile2);
        uploadfile2.setId(2L);
        assertThat(uploadfile1).isNotEqualTo(uploadfile2);
        uploadfile1.setId(null);
        assertThat(uploadfile1).isNotEqualTo(uploadfile2);
    }
}
