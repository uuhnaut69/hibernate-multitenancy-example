package com.uuhnaut69.core.config;

import com.uuhnaut69.core.model.AbstractEntity;
import com.uuhnaut69.core.tenant.TenantContext;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class HibernateConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder factory, DataSource dataSource, JpaProperties properties) {
        Map<String, Object> jpaProperties = new HashMap<>(properties.getProperties());
        jpaProperties.put("hibernate.ejb.interceptor", hibernateInterceptor());
        return factory.dataSource(dataSource).packages("com.example").properties(jpaProperties).build();
    }


    @Bean
    public EmptyInterceptor hibernateInterceptor() {
        return new EmptyInterceptor() {

            @Override
            public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
                if (entity instanceof AbstractEntity) {
                    log.debug("[save] Updating the entity " + id + " with util information: " + TenantContext.getCurrentTenant());
                    ((AbstractEntity) entity).setTenantId(TenantContext.getCurrentTenant());
                }
                return false;
            }

            @Override
            public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
                if (entity instanceof AbstractEntity) {
                    log.debug("[delete] Updating the entity " + id + " with util information: " + TenantContext.getCurrentTenant());
                    ((AbstractEntity) entity).setTenantId(TenantContext.getCurrentTenant());
                }
            }

            @Override
            public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
                if (entity instanceof AbstractEntity) {
                    log.debug("[flush-dirty] Updating the entity " + id + " with util information: " + TenantContext.getCurrentTenant());
                    ((AbstractEntity) entity).setTenantId(TenantContext.getCurrentTenant());
                }
                return false;
            }

        };
    }
}
