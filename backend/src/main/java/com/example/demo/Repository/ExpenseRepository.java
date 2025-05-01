package com.example.demo.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

	@Query(value = "SELECT * FROM Expense e WHERE e.date BETWEEN :startDate AND :endDate", nativeQuery = true)
	List<Expense> findByDateBetween(Date startDate, Date endDate);

}
