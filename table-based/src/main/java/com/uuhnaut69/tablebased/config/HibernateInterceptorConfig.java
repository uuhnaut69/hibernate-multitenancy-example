package com.uuhnaut69.tablebased.config;

import com.uuhnaut69.core.model.AbstractEntity;
import com.uuhnaut69.tablebased.util.TenantContext;
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
public class HibernateInterceptorConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder factory, DataSource dataSource, JpaProperties properties) {
        Map<String, Object> jpaProperties = new HashMap<>(properties.getProperties());
        jpaProperties.put("hibernate.ejb.interceptor", hibernateInterceptor());
        return factory.dataSource(dataSource).packages("com.uuhnaut69").properties(jpaProperties).build();
    }

    @Bean
    public EmptyInterceptor hibernateInterceptor() {
        return new EmptyInterceptor() {
            @Override
            public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
                if (entity instanceof AbstractEntity) {
                    log.debug("[DELETE] Updating the entity " + id + " with tenant id: " + TenantContext.getCurrentTenant());
                    ((AbstractEntity) entity).setTenantId(TenantContext.getCurrentTenant());
                }
            }

            @Override
            public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
                if (entity instanceof AbstractEntity) {
                    log.debug("[FLUSH-DIRTY] Updating the entity " + id + " with tenant id: " + TenantContext.getCurrentTenant());
                    ((AbstractEntity) entity).setTenantId(TenantContext.getCurrentTenant());
                }
                return false;
            }

            @Override
            public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
                if (entity instanceof AbstractEntity) {
                    log.debug("[SAVE] Updating the entity " + id + " with tenant id: " + TenantContext.getCurrentTenant());
                    ((AbstractEntity) entity).setTenantId(TenantContext.getCurrentTenant());
                }
                return false;
            }
        };
    }
}
