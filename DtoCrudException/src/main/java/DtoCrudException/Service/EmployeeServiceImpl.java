package DtoCrudException.Service;

import DtoCrudException.Dto.EmployeeDto;
import DtoCrudException.Entity.Employee;
import DtoCrudException.Exception.ResourceNotFoundExceptions;
import DtoCrudException.Mapper.EmployeeMapper;
import DtoCrudException.Repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;



    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee= EmployeeMapper.mapToEmployee(employeeDto);

      Employee employee1=  employeeRepository.save(employee);

      return EmployeeMapper.mapToEMployeeDto(employee1);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
       List<Employee> employee= employeeRepository.findAll();
        return employee.stream().map(EmployeeMapper::mapToEMployeeDto).toList();

    }

    @Override
    public EmployeeDto getEmpById(Long id) {

      Employee employee=  employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundExceptions("id Not found"));

        return EmployeeMapper.mapToEMployeeDto(employee);
    }

    @Override
    public EmployeeDto updateEmploy(Long id, EmployeeDto employeeDto) {

        Employee employee=employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundExceptions("id not found"));
        employee.setName(employeeDto.getName());
        employee.setEmail(employeeDto.getEmail());
        employee.setMobile(employeeDto.getMobile());
        employee.setSalary(employeeDto.getSalary());

        employeeRepository.save(employee);
        return EmployeeMapper.mapToEMployeeDto(employee);
    }

    @Override
    public void deleteEmployee(Long id) {

      employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundExceptions("id not found"));

        employeeRepository.deleteById(id);

    }

    @Override
    public EmployeeDto getEmployeeByName(String name) {

        Employee employee=employeeRepository.getByName(name).orElseThrow(()->new ResourceNotFoundExceptions("id not found"));

        return EmployeeMapper.mapToEMployeeDto(employee);
    }

    @Override
    public Page<EmployeeDto> getAllEmpByPagination(int pageNo, int pageSize) {

        Pageable pageable= PageRequest.of(pageNo,pageSize);

        Page<Employee> employee=employeeRepository.findAll(pageable);

        Page<EmployeeDto> empDto=employee.map(EmployeeMapper::mapToEMployeeDto);

        return empDto;
    }

    @Override
    public List<EmployeeDto> getEmployeeSorted(String field) {


      List<Employee> employee= employeeRepository.findAll(Sort.by(field));


        return employee.stream().map(EmployeeMapper::mapToEMployeeDto).toList();
    }

    @Override
    public Page<EmployeeDto> getAllByPaginationAndSorting(int pageNo, int pageSize, String field) {

        Pageable pageable=PageRequest.of(pageNo,pageSize,Sort.by(Sort.Direction.DESC,field));

        Page<Employee> page=employeeRepository.findAll(pageable);

        Page<EmployeeDto> employeeDtos=page.map(EmployeeMapper::mapToEMployeeDto);

        return employeeDtos;




    }

    @Override
    public EmployeeDto getByNameAndEmail(String name, String email) {

       Employee employee= employeeRepository.searchByNameAndEmail(name,email).orElseThrow(()->new ResourceNotFoundExceptions("Id/email not found"));

        return EmployeeMapper.mapToEMployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> searchEmployees(String name, Double minSalary, Double maxSalary) {
        List<Employee> employees = employeeRepository.searchEmployees(name,minSalary,maxSalary);
        return employees.stream().map(EmployeeMapper::mapToEMployeeDto).collect(Collectors.toList());
    }


}
