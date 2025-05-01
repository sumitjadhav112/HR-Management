package com.example.demo.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Installment;

@Repository
public interface InstallmentRepository extends JpaRepository<Installment, Long> {

	@Query(value = "SELECT COUNT(*) FROM installment WHERE client_id = :clientId", nativeQuery = true)
	long countByClient(Long clientId);

	@Query(value = "SELECT COALESCE(SUM(amount), 0) FROM installment WHERE client_id = :clientId", nativeQuery = true)
	double sumByClient(Long clientId);

	@Query(value = "select * from installment where client_id = ?", nativeQuery = true)
	List<Installment> findByClientId(Long id);

	@Query(value = "SELECT * FROM installment WHERE client_id = :clientId ORDER BY due_date DESC LIMIT 1", nativeQuery = true)
	Optional<Installment> findTopByClientOrderByDueDateDesc(Long clientId);

	@Query(value = "SELECT * FROM installment WHERE client_id = :clientId ORDER BY due_date DESC LIMIT 1 OFFSET 1", nativeQuery = true)
	Optional<Installment> findSecondLastByClientId(Long clientId);

}
