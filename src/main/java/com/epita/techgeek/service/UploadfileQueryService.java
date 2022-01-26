package com.epita.techgeek.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.epita.techgeek.domain.Uploadfile;
import com.epita.techgeek.domain.*; // for static metamodels
import com.epita.techgeek.repository.UploadfileRepository;
import com.epita.techgeek.service.dto.UploadfileCriteria;
import com.epita.techgeek.service.dto.UploadfileDTO;
import com.epita.techgeek.service.mapper.UploadfileMapper;

/**
 * Service for executing complex queries for {@link Uploadfile} entities in the database.
 * The main input is a {@link UploadfileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UploadfileDTO} or a {@link Page} of {@link UploadfileDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UploadfileQueryService extends QueryService<Uploadfile> {

    private final Logger log = LoggerFactory.getLogger(UploadfileQueryService.class);

    private final UploadfileRepository uploadfileRepository;

    private final UploadfileMapper uploadfileMapper;

    public UploadfileQueryService(UploadfileRepository uploadfileRepository, UploadfileMapper uploadfileMapper) {
        this.uploadfileRepository = uploadfileRepository;
        this.uploadfileMapper = uploadfileMapper;
    }

    /**
     * Return a {@link List} of {@link UploadfileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UploadfileDTO> findByCriteria(UploadfileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Uploadfile> specification = createSpecification(criteria);
        return uploadfileMapper.toDto(uploadfileRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UploadfileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UploadfileDTO> findByCriteria(UploadfileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Uploadfile> specification = createSpecification(criteria);
        return uploadfileRepository.findAll(specification, page)
            .map(uploadfileMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UploadfileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Uploadfile> specification = createSpecification(criteria);
        return uploadfileRepository.count(specification);
    }

    /**
     * Function to convert {@link UploadfileCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Uploadfile> createSpecification(UploadfileCriteria criteria) {
        Specification<Uploadfile> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Uploadfile_.id));
            }
        }
        return specification;
    }
}
