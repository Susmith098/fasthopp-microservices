package com.fasthopp.userservice.Repository;

import com.fasthopp.userservice.Entity.CompanyName;
import com.fasthopp.userservice.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyNameRepository extends JpaRepository<CompanyName, Long> {
    Optional<CompanyName> findByName(String companyName);
}
