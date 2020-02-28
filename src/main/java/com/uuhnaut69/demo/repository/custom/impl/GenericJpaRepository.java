package com.uuhnaut69.demo.repository.custom.impl;

import com.uuhnaut69.demo.exception.NotFoundException;
import com.uuhnaut69.demo.model.AbstractEntity;
import com.uuhnaut69.demo.repository.custom.BaseRepository;
import com.uuhnaut69.demo.tenant.TenantContext;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public class GenericJpaRepository<E extends AbstractEntity, ID extends Serializable> extends
        SimpleJpaRepository<E, ID> implements BaseRepository<E, ID> {

    private static final String TENANT_ID_PROPERTY = "tenantId";

    private final EntityManager entityManager;
    private final JpaEntityInformation<E, ID> entityInformation;

    public GenericJpaRepository(JpaEntityInformation<E, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        this.entityInformation = entityInformation;
    }

    @Override
    public Optional<E> findById(ID id) {
        return super.findOne(Specification.where(new ByIdSpecification<E, ID>(id, entityInformation)));
    }

    @Override
    public void deleteById(ID id) {
        E entity = this.getById(id);
        super.delete(entity);
    }

    private E getById(ID id) {
        return super.findOne(Specification.where(new ByIdSpecification<E, ID>(id, entityInformation))).orElseThrow(() -> new NotFoundException("Not found entity"));
    }

    @Override
    protected <S extends E> TypedQuery<S> getQuery(Specification<S> spec, Class<S> domainClass, Pageable pageable) {
        return super.getQuery(spec != null ? spec.and(new ByTenantIdSpecification<>()) : new ByTenantIdSpecification<>(), domainClass, pageable);
    }

    @Override
    protected <S extends E> TypedQuery<S> getQuery(Specification<S> spec, Class<S> domainClass, Sort sort) {
        return super.getQuery(spec != null ? spec.and(new ByTenantIdSpecification<>()) : new ByTenantIdSpecification<>(), domainClass, sort);
    }

    private static final class ByIdSpecification<T, ID extends Serializable> implements Specification<T> {
        private final ID id;
        private final JpaEntityInformation<T, ?> information;

        public ByIdSpecification(ID id, JpaEntityInformation<T, ?> information) {
            this.id = id;
            this.information = information;
        }

        @Override
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            final List<Predicate> predicates = new ArrayList<>();
            if (information.hasCompositeId()) {
                information.getIdAttributeNames().forEach(name ->
                        predicates.add(criteriaBuilder.equal(root.get(name), information.getCompositeIdAttributeValue(id, name))));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
            return criteriaBuilder.equal(root.get(information.getIdAttribute().getName()), id);
        }
    }

    private static final class ByTenantIdSpecification<E, ID extends Serializable> implements Specification<E> {
        @Override
        public Predicate toPredicate(Root<E> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
            return criteriaBuilder.equal(root.get(TENANT_ID_PROPERTY), TenantContext.getCurrentTenant());
        }
    }
}
