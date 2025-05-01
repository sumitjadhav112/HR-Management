package com.example.demo.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.EmployeeLeave;

@Repository
public interface LeaveRepository extends JpaRepository<EmployeeLeave, Long> {

	@Query(value = "SELECT * FROM employee_leave WHERE employee_id = :employeeId AND MONTH(leave_date) = :month AND YEAR(leave_date) = :year", nativeQuery = true)
	List<EmployeeLeave> findByGeniusEmployeeAndMonth(@Param("employeeId") Long employeeId, @Param("month") int month,
			@Param("year") int year);

	@Query(value = "select * from employee_leave where employee_id = ?1", nativeQuery = true)
	List<EmployeeLeave> findByEmpId(Long emp_id);

	@Query(value = "SELECT * FROM employee_leave WHERE employee_leave.leave_date BETWEEN :startDate AND :endDate", nativeQuery = true)
	List<EmployeeLeave> findByDateBetween(Date startDate, Date endDate);

	@Query(value = "SELECT * FROM employee_leave WHERE leave_date BETWEEN :startDate AND :endDate", nativeQuery = true)
	List<EmployeeLeave> findByLeaveDateBetween(@Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate);

}
