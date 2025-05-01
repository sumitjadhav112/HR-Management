package com.example.demo.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

	@Query(value = "SELECT * FROM invoice ORDER BY invoice_number DESC LIMIT 1", nativeQuery = true)
	Optional<Invoice> findTopByOrderByInvoiceNumberDesc();

	@Query(value = "select * from invoice where client_id = ?", nativeQuery = true)
	List<Invoice> findByClientId(Long client_id);

	@Query(value = "SELECT * FROM Invoice i WHERE i.invoice_date BETWEEN :startDate AND :endDate", nativeQuery = true)
	List<Invoice> findByDateBetween(Date startDate, Date endDate);

}
