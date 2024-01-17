package com.fasthopp.userservice.Repository;

import com.fasthopp.userservice.Entity.Role;
import com.fasthopp.userservice.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    List<User> findAllByRole(Role role);

    List<User> findAllByCompanyName_NameAndRole(String companyName, Role role);

    List<User> findAllByCompanyName_Name(String companyName);
}
