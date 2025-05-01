package com.example.demo.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Salary;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {

	@Query("SELECT s FROM Salary s WHERE s.employee.employee_id = :employeeId AND s.month = :month")
	Optional<Salary> findByEmployeeIdAndMonth(Long employeeId, LocalDate month);

	@Query("SELECT SUM(s.advance_amount) FROM Salary s WHERE s.employee.employee_id = :employeeId AND s.month = :month")
	Double findAdvanceAmountByEmployeeId(Long employeeId, LocalDate month);

	@Query(value = "SELECT * FROM salary WHERE month BETWEEN :startDate AND :endDate", nativeQuery = true)
	List<Salary> findByMonthBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

	@Query("SELECT s FROM Salary s WHERE s.employee.employee_id = :employeeId AND MONTH(s.month) = :month AND YEAR(s.month) = :year")
	List<Salary> findByEmployeeAndMonth(@Param("employeeId") Long employeeId, @Param("month") int month,
			@Param("year") int year);
}