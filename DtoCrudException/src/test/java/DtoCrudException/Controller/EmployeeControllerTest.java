package DtoCrudException.Controller;

import DtoCrudException.Dto.EmployeeDto;
import DtoCrudException.Service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetEmployeesWithSalaryAboveThreshold() throws Exception {
        // 1. Prepare mock employee list
        List<EmployeeDto> mockEmployees = List.of(
                new EmployeeDto(1L, "John Doe", "john@example.com", "1234567890", 75000),
                new EmployeeDto(2L, "Jane Smith", "jane@example.com", "9876543210", 80000)
        );

        // 2. Define service behavior
        Mockito.when(employeeService.getEmployeesWithSalaryAbove(70000.0))
                .thenReturn(mockEmployees);

        // 3. Simulate GET call and check response
        mockMvc.perform(get("/api/salary/above/70000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 200 OK
                .andExpect(jsonPath("$.length()").value(2)) // List has 2 employees
                .andExpect(jsonPath("$[0].name").value("John Doe")) // First one name match
                .andExpect(jsonPath("$[1].salary").value(80000.0)); // Second one salary match
    }

}
