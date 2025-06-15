package DtoCrudException.Repository;

import DtoCrudException.Dto.EmployeeDto;
import DtoCrudException.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

   Optional<Employee> getByName(String name);

   Optional<Employee> searchByNameAndEmail(String name,String email);

   @Query("SELECT e FROM Employee e WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%')) " +
           "AND (:minSalary IS NULL OR e.salary >= :minSalary) " +
           "AND (:maxSalary IS NULL OR e.salary <= :maxSalary)")
   List<Employee> searchEmployees(@Param("name") String name,@Param("minSalary") Double minSalary, @Param("maxSalary") Double maxSalary);

   List<Employee> findBySalaryGreaterThan(Double threshold);
}
