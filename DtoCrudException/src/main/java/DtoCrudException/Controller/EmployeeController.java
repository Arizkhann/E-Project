package DtoCrudException.Controller;

import DtoCrudException.Dto.EmployeeDto;
import DtoCrudException.Service.EmployeeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor

public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeDto> createUSer(@Valid @RequestBody EmployeeDto employeeDto){


        EmployeeDto newEmployeeDto=employeeService.createEmployee(employeeDto);

        return new ResponseEntity<>(newEmployeeDto, HttpStatus.CREATED);
    }


    @GetMapping()
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {

       List<EmployeeDto> employeeDtos= employeeService.getAllEmployees();

       return ResponseEntity.ok(employeeDtos);

    }


    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEMpByID(@PathVariable("id") Long id){

     EmployeeDto employeeDto = employeeService.getEmpById(id);

        return ResponseEntity.ok(employeeDto);
    }

//updating the employee
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmploye(@PathVariable("id") Long id,@Valid @RequestBody EmployeeDto dtoFromClient){

       EmployeeDto employeeDto= employeeService.updateEmploy(id, dtoFromClient);

       return ResponseEntity.ok(employeeDto);

    }

        @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long id){

        employeeService.deleteEmployee(id);

        return ResponseEntity.ok("employee deleted successfully");
    }

@GetMapping("/{name}")
    public  ResponseEntity<EmployeeDto> getEMpByName(@PathVariable("name") String name){

        EmployeeDto employeeDto=employeeService.getEmployeeByName(name);

        return ResponseEntity.ok(employeeDto);

    }

    //get Emp By name and email

    @GetMapping("/getByNameAndEmail")
    public ResponseEntity<EmployeeDto> getEMpByNameAndEmail(@RequestParam String name,
                                                            @RequestParam String email){

      EmployeeDto employeeDto=  employeeService.getByNameAndEmail(name,email);

      return ResponseEntity.ok(employeeDto);

    }




    //pagination


    @GetMapping("/page/paginated")
    public ResponseEntity<Page<EmployeeDto>> getPaginatedEmployees(@RequestParam(defaultValue = "0") int pageNo,
                                                  @RequestParam(defaultValue = "5") int pageSize) {

        Page<EmployeeDto> allEmpByPagination = employeeService.getAllEmpByPagination(pageNo, pageSize);

        return ResponseEntity.ok(allEmpByPagination);
    }

    //Sorting

    @GetMapping("/sorting")
    public ResponseEntity<List<EmployeeDto>> getEmpBySorting(@RequestParam(defaultValue = "name")String field){
        List<EmployeeDto> employeeDtos=employeeService.getEmployeeSorted(field);

        return ResponseEntity.ok(employeeDtos);
    }

    //Pagination and Sorting

    @GetMapping("/paginationAndSorting")

    public ResponseEntity<Page<EmployeeDto>> getEmpByPaginationNdSorting(@RequestParam(defaultValue = "0") int pageNo,
                                                                         @RequestParam(defaultValue = "3")  int pageSize,
                                                                         @RequestParam(defaultValue = "name") String field){


       Page<EmployeeDto> eDto= employeeService.getAllByPaginationAndSorting(pageNo,pageSize,field);


       return ResponseEntity.ok(eDto);

    }


    @GetMapping("/search")
    public ResponseEntity<List<EmployeeDto>> searchEmployees(@RequestParam String name, @RequestParam(required = false) Double minSalary, @RequestParam(required = false) Double maxSalary){
        List<EmployeeDto> result = employeeService.searchEmployees(name,minSalary,maxSalary);

        if(result.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(result);
    }

//: Add Endpoint to Get Employees Whose Salary Is Above a Given Threshold
// /api/salary/above/500



    @GetMapping("/salary/above/{threshold}")
    public ResponseEntity<List<EmployeeDto>> getEmployeesWithSalaryAbove(@PathVariable Double threshold) {
        List<EmployeeDto> employees = employeeService.getEmployeesWithSalaryAbove(threshold);
        return ResponseEntity.ok(employees);
    }


}
