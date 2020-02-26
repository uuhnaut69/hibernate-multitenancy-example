package com.uuhnaut69.web.controller;

import com.uuhnaut69.core.model.Employee;
import com.uuhnaut69.tablebased.payload.request.EmployeeRequest;
import com.uuhnaut69.tablebased.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    @PostMapping
    public Employee create(@RequestBody @Valid EmployeeRequest employeeRequest) {
        return employeeService.create(employeeRequest);
    }

    @PostMapping("/{id}")
    public Employee update(@PathVariable UUID id, @RequestBody @Valid EmployeeRequest employeeRequest) throws Exception {
        return employeeService.update(id, employeeRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) throws Exception {
        employeeService.delete(id);
    }
}
