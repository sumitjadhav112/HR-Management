package com.example.demo.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Revenue;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Long> {

	@Query(value = "SELECT * FROM Revenue r WHERE r.date BETWEEN :startDate AND :endDate", nativeQuery = true)
	List<Revenue> findByDateBetween(Date startDate, Date endDate);

}
