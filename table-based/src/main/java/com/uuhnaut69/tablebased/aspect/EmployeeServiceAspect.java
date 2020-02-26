package com.uuhnaut69.tablebased.aspect;

import com.uuhnaut69.tablebased.service.EmployeeService;
import com.uuhnaut69.tablebased.util.TenantContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Aspect
@Component
public class EmployeeServiceAspect {

    @PersistenceContext
    public EntityManager entityManager;

    @Before("execution(* com.uuhnaut69.tablebased.service.EmployeeService.*(..)) && target(employeeService)")
    public void aroundExecution(JoinPoint pjp, EmployeeService employeeService) throws Throwable {
        org.hibernate.Filter filter = entityManager.unwrap(Session.class).enableFilter("tenantFilter");
        filter.setParameter("tenantId", TenantContext.getCurrentTenant());
        filter.validate();
    }

}
