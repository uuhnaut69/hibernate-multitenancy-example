package com.uuhnaut69.demo.aspect;

import com.uuhnaut69.demo.request.TodoRequest;
import com.uuhnaut69.demo.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class Logging {

    @Before(value = "execution(* com.uuhnaut69.demo.service.TodoService.create(..)) && target(todoService)")
    public void aroundExecution(JoinPoint pjp, TodoService todoService) {
        if (pjp.getArgs()[0] instanceof TodoRequest) {
            TodoRequest todoRequest = (TodoRequest) pjp.getArgs()[0];
            log.info("Insert/Update " + todoRequest.getName() + " task into database !!!");
        }
    }
}
