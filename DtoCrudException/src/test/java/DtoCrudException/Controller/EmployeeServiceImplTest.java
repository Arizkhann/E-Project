package DtoCrudException.Controller;

import DtoCrudException.Entity.Employee;
import DtoCrudException.Exception.ResourceNotFoundExceptions;
import DtoCrudException.Repository.EmployeeRepository;
import DtoCrudException.Service.EmployeeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setup() {
        employee = new Employee();
        employee.setId(1L);
        employee.setName("Ananya");
        employee.setEmail("ani@gmail.com");
        employee.setMobile("0978359875");
        employee.setSalary(50000.0);
    }

    @Test
    void testUpdateMobileNumber_success() {
        String newMobile = "9876543204";

        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(employee);

        employeeService.updateMobileNumber(1L, newMobile);

        Assertions.assertEquals(newMobile, employee.getMobile());
        Mockito.verify(employeeRepository).save(employee);
    }

    @Test
    void testUpdateMobileNumber_employeeNotFound() {
        Mockito.when(employeeRepository.findById(2L)).thenReturn(Optional.empty());

        ResourceNotFoundExceptions exception = Assertions.assertThrows(ResourceNotFoundExceptions.class, () -> {
            employeeService.updateMobileNumber(2L, "9876543210");
        });

        Assertions.assertEquals("Employee not found with id: 2", exception.getMessage());
        Mockito.verify(employeeRepository, Mockito.never()).save(Mockito.any());
    }
}
