package com.epita.techgeek.repository;

import com.epita.techgeek.domain.Uploadfile;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Uploadfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UploadfileRepository extends JpaRepository<Uploadfile, Long>, JpaSpecificationExecutor<Uploadfile> {
}
