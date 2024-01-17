package com.fasthopp.notificationservice.repository;

import com.fasthopp.notificationservice.entity.UserRegistrationMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRegistrationMessageRepository extends JpaRepository<UserRegistrationMessage, Long> {
    List<UserRegistrationMessage> findByCompanyName(String companyName);
}
