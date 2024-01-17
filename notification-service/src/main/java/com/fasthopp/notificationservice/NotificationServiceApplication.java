package com.fasthopp.notificationservice;

import com.fasthopp.notificationservice.entity.UserRegistrationMessage;
import com.fasthopp.notificationservice.event.UserRegisterEvent;
import com.fasthopp.notificationservice.repository.UserRegistrationMessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j
public class NotificationServiceApplication {

	private final UserRegistrationMessageRepository registrationMessageRepository;

    public NotificationServiceApplication(UserRegistrationMessageRepository registrationMessageRepository) {
        this.registrationMessageRepository = registrationMessageRepository;
    }

    public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}

	@KafkaListener(topics = "notificationTopic")
	public void handleNotification(UserRegisterEvent userRegisterEvent){

		log.info("{} with email {} registered to your workspace at company {} on {}",
				userRegisterEvent.getUsername(),
				userRegisterEvent.getEmail(),
				userRegisterEvent.getCompanyName(),
				userRegisterEvent.getCreatedAt());

		// Save the user registration message to the database
		saveUserRegistrationMessage(userRegisterEvent);
	}

	private void saveUserRegistrationMessage(UserRegisterEvent userRegisterEvent) {
		try {
			UserRegistrationMessage message = new UserRegistrationMessage();
			message.setUsername(userRegisterEvent.getUsername());
			message.setEmail(userRegisterEvent.getEmail());
			message.setCompanyName(userRegisterEvent.getCompanyName());
			message.setCreatedAt(userRegisterEvent.getCreatedAt());

			// Save the message to the database
			registrationMessageRepository.save(message);

			log.info("User registration message saved to the database");
		} catch (Exception e) {
			log.error("Error saving user registration message to the database", e);
		}
	}
}
