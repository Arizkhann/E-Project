package DtoCrudException.Service;

import DtoCrudException.Dto.EmployeeDto;
import DtoCrudException.Entity.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {

EmployeeDto createEmployee(EmployeeDto employeeDto);

List<EmployeeDto> getAllEmployees();

EmployeeDto getEmpById(Long id);

EmployeeDto updateEmploy(Long id,EmployeeDto employeeDto);

void deleteEmployee(Long id);


EmployeeDto getEmployeeByName(String name);

Page<EmployeeDto> getAllEmpByPagination(int pageNo,int pageSize);

List<EmployeeDto> getEmployeeSorted(String field);

Page<EmployeeDto> getAllByPaginationAndSorting(int pageNo,int pageSize,String field);


EmployeeDto getByNameAndEmail(String name,String email);


    List<EmployeeDto> searchEmployees(String name, Double minSalary, Double maxSalary );

    List<EmployeeDto> getEmployeesWithSalaryAbove(Double threshold);
}


