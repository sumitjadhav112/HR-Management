package com.example.demo.ServiceImpli;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.ClientRepository;
import com.example.demo.Repository.InstallmentRepository;
import com.example.demo.Repository.NotificationRepository;
import com.example.demo.Service.NotificationService;
import com.example.demo.enums.InstallmentStatus;
import com.example.demo.model.Client;
import com.example.demo.model.Installment;
import com.example.demo.model.Notifications;
import com.example.demo.response.SuccessResponse;

@Service
public class NotificationServiceImple implements NotificationService {

	@Autowired
	NotificationRepository notificationRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private InstallmentRepository installmentRepository;

	SuccessResponse response = new SuccessResponse();

	@Autowired
	ModelMapper modelMapper;

	@Override
	public SuccessResponse getAllNotifications() {
		List<Notifications> findAll = notificationRepository.findAll();
		if (findAll.isEmpty()) {
			response.notificationsAreEmpty();
			return response;
		}
		response.notificationRetrive(findAll);
		return response;
	}

	@Override
	public SuccessResponse markNotificationAsRead(Long id) {
		Optional<Notifications> findById = notificationRepository.findById(id);
		if (findById.isPresent()) {
			Notifications notifications = findById.get();
			notifications.setRead(true);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			notifications.setSeen_time(sdf.format(new Date()));
			notificationRepository.save(notifications);
			response.readSuccessfuly(notifications);
			return response;
		} else {
			response.notificationNotFound();
			return response;
		}
	}

	@Scheduled(fixedDelay = 300000)
	public void removeOldNotifications() {
		LocalDateTime minusMinutes = LocalDateTime.now().minusMinutes(5);
		List<Notifications> expiredNotifications = notificationRepository.getExpiredNotifications(minusMinutes);
		notificationRepository.deleteAll(expiredNotifications);
	}

//	@Scheduled(cron = "0 0 0 * * *") // Runs every day at midnight
////	@Scheduled(fixedDelay = 60000)
//	public void checkClientTimelines() {
//		System.out.println("schedular runs...");
//		LocalDateTime now = LocalDateTime.now();
//		LocalDateTime threeDaysFromNow = now.plusDays(3);
//
//		List<Client> clients = clientRepository.findAll();
//		for (Client client : clients) {
//			System.out.println("TimeLine for users" + client.getTimeline());
//			System.out.println("three days ago time " + threeDaysFromNow);
//			if (client.getTimeline() != null && client.getTimeline().isBefore(threeDaysFromNow)
//					&& client.getTimeline().isAfter(now)) {
//				Notifications notification = new Notifications();
//				notification.setClient(client);
//				notification.setMessage(
//						"The project deadline for client " + client.getClient_name() + " is within 3 days.");
//				notification.setCreatedAt(LocalDateTime.now());
//				notification.setRead(false);
//				notificationRepository.save(notification);
//			}
//		}
//	}

	@Scheduled(cron = "0 0 0 * * *") // Runs every day at midnight
	public void checkClientTimelines() {
		System.out.println("Scheduler runs...");
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime threeDaysFromNow = now.plusDays(3);
		LocalDateTime oneDayAgo = now.minusDays(1);

		List<Client> clients = clientRepository.findAll();
		for (Client client : clients) {
			if (client.getTimeline() != null && client.getTimeline().isBefore(threeDaysFromNow)
					&& client.getTimeline().isAfter(now)) {

				// Check if a notification already exists for this client
				List<Notifications> existingNotifications = notificationRepository
						.findExistingNotifications(client.getClient_id(), oneDayAgo, now);

				if (existingNotifications.isEmpty()) {
					Notifications notification = new Notifications();
					notification.setClient(client);
					notification.setMessage(
							"The project deadline for client " + client.getClient_name() + " is within 3 days.");
					notification.setCreatedAt(LocalDateTime.now());
					notification.setRead(false);
					notificationRepository.save(notification);
				}
			}
		}
	}

//	@Scheduled(fixedDelay = 60000) // Runs every minute
//	public void checkInstallmentDueDates() {
//		System.out.println("Checking installment due dates...");
//		LocalDate now = LocalDate.now();
//		LocalDate twoDaysFromNow = now.plusDays(2);
//
//		// Fetch all installments due within the next 2 days
//		List<Installment> installments = installmentRepository.findAll();
//		List<Notifications> notificationsToSave = new ArrayList<>();
//
//		for (Installment installment : installments) {
//			if (installment.getDueDate() != null
//					&& (installment.getDueDate().isBefore(twoDaysFromNow) && installment.getDueDate().isAfter(now))
//					&& installment.getStatus() == InstallmentStatus.PENDING) { // Check if the installment is not paid
//
//				// Check if a notification already exists for this installment
//				boolean notificationExists = notificationRepository.existsByInstallmentIdAndCreatedAtBetween(
//						installment.getInstallment_id(), now.minusDays(1), now);
//
//				if (!notificationExists) {
//					// Create a new notification
//					Notifications notification = new Notifications();
//					notification.setClient(installment.getClient());
//					notification.setInstallment(installment);
//					notification.setMessage("Installment for client " + installment.getClient().getClient_name()
//							+ " is due within 2 days.");
//					notification.setRead(false);
//					notificationsToSave.add(notification);
//				}
//			}
//		}


@Scheduled(fixedDelay = 60000) // Runs every minute
public void checkInstallmentDueDates() {
    System.out.println("Checking installment due dates...");
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime twoDaysFromNow = now.plusDays(2);

    // Fetch all installments due within the next 2 days
    List<Installment> installments = installmentRepository.findAll();
    List<Notifications> notificationsToSave = new ArrayList<>();

    for (Installment installment : installments) {
        if (installment.getDueDate() != null
                && (installment.getDueDate().isBefore(twoDaysFromNow.toLocalDate()) && installment.getDueDate().isAfter(now.toLocalDate()))
                && installment.getStatus() == InstallmentStatus.PENDING) { // Check if the installment is not paid

            // Check if a notification already exists for this installment
            boolean notificationExists = notificationRepository.existsByInstallmentIdAndCreatedAtBetween(
                    installment.getInstallment_id(), now.minusDays(1), now);

            if (!notificationExists) {
                // Create a new notification
                Notifications notification = new Notifications();
                notification.setClient(installment.getClient());
                notification.setInstallment(installment);
                notification.setMessage("Installment for client " + installment.getClient().getClient_name()
                        + " is due within 2 days.");
                notification.setRead(false);
                notificationsToSave.add(notification);
            }
        }
    }

    // Save all notifications in a batch
    if (!notificationsToSave.isEmpty()) {
        notificationRepository.saveAll(notificationsToSave);
        System.out.println(notificationsToSave.size() + " notifications saved.");
    }
}


}
