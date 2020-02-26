package com.uuhnaut69.tablebased.service.impl;

import com.uuhnaut69.core.exception.NotFoundException;
import com.uuhnaut69.core.model.Employee;
import com.uuhnaut69.tablebased.payload.request.EmployeeRequest;
import com.uuhnaut69.tablebased.repository.EmployeeRepository;
import com.uuhnaut69.tablebased.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Employee find(UUID id) throws Exception {
        return employeeRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found exception !!!"));
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee create(EmployeeRequest employeeRequest) {
        return save(new Employee(), employeeRequest);
    }

    @Override
    public Employee update(UUID id, EmployeeRequest employeeRequest) throws Exception {
        Employee employee = find(id);
        return save(employee, employeeRequest);
    }

    @Override
    public void delete(UUID id) throws Exception {
        Employee employee = find(id);
        employeeRepository.delete(employee);
    }

    /**
     * Save
     *
     * @param employee
     * @param employeeRequest
     * @return Employee
     */
    private Employee save(Employee employee, EmployeeRequest employeeRequest) {
        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        return employeeRepository.save(employee);
    }
}
