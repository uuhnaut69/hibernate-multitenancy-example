package com.uuhnaut69.tablebased.service;

import com.uuhnaut69.core.model.Employee;
import com.uuhnaut69.tablebased.payload.request.EmployeeRequest;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    /**
     * Find employee by id
     *
     * @param id
     * @return
     * @throws Exception
     */
    Employee find(UUID id) throws Exception;

    /**
     * Find employees
     *
     * @return List {@link Employee}
     */
    List<Employee> findAll();

    /**
     * Save employee
     *
     * @param employeeRequest
     * @return Employee
     */
    Employee create(EmployeeRequest employeeRequest);

    /**
     * Update employee
     *
     * @param id
     * @param employeeRequest
     * @return Employee
     * @throws Exception
     */
    Employee update(UUID id, EmployeeRequest employeeRequest) throws Exception;

    /**
     * Delete employee
     *
     * @param id
     */
    void delete(UUID id) throws Exception;

}
