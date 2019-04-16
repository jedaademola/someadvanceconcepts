package com.advance.concepts.util.batch;

import com.advance.concepts.model.Employee;
import org.springframework.batch.item.ItemProcessor;

public class DBLogProcessor implements ItemProcessor<Employee, Employee> {
    public Employee process(Employee employee) throws Exception {
        System.out.println("Inserting employee : " + employee);
        return employee;
    }
}
