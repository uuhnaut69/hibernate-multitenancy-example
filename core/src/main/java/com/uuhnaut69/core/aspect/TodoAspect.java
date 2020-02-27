package com.uuhnaut69.core.aspect;

import com.uuhnaut69.core.service.TodoService;
import com.uuhnaut69.core.tenant.TenantContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@Aspect
@Component
public class TodoAspect {

    @PersistenceContext
    private EntityManager entityManager;

    @Before("execution(* com.uuhnaut69.core.service.TodoService*(..)) && target(todoService)")
    public void aroundExecution(JoinPoint pjp, TodoService todoService) throws Throwable {
        Filter filter = entityManager.unwrap(Session.class).enableFilter("tenantFilter");
        filter.setParameter("tenantId", TenantContext.getCurrentTenant());
        filter.validate();
    }

}
