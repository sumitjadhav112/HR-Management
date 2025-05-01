package com.example.demo.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Notifications;

@Repository
public interface NotificationRepository extends JpaRepository<Notifications, Long> {

	@Query(value = "select * from notifications where seen_time < :time and is_read = true", nativeQuery = true)
	List<Notifications> getExpiredNotifications(LocalDateTime time);

	@Query(value = "SELECT * FROM notifications WHERE client_id = :clientId AND created_at BETWEEN :startTime AND :endTime", nativeQuery = true)
	List<Notifications> findExistingNotifications(Long clientId, LocalDateTime startTime, LocalDateTime endTime);

//	@Query("SELECT COUNT(n) > 0 FROM Notifications n WHERE n.installment.id = :installmentId AND n.createdAt BETWEEN :start AND :end")
//	boolean existsByInstallmentIdAndCreatedAtBetween(Long installmentId, LocalDate start, LocalDate end);

	@Query("SELECT COUNT(n) > 0 FROM Notifications n WHERE n.installment.id = :installmentId AND n.createdAt BETWEEN :start AND :end")
	boolean existsByInstallmentIdAndCreatedAtBetween(Long installmentId, LocalDateTime start, LocalDateTime end);
}
