package com.uuhnaut69.demo.repository.custom;

import com.uuhnaut69.demo.model.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<E extends AbstractEntity, ID extends Serializable> extends
        JpaRepository<E, ID>, JpaSpecificationExecutor<E> {

}
