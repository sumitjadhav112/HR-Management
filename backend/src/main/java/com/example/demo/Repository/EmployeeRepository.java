package com.example.demo.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.OurEmployees;

@Repository
public interface EmployeeRepository extends JpaRepository<OurEmployees, Long> {

	@Query(value = "select * from genius_employees where status = true", nativeQuery = true)
	List<OurEmployees> findAllActiveEmployees();

	@Query(value = "SELECT * FROM genius_employees WHERE employee_id IN "
			+ "(SELECT employee_id FROM salary WHERE month BETWEEN :startDate AND :endDate)", nativeQuery = true)
	List<OurEmployees> findBySalaryMonthBetween(@Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate);
}
