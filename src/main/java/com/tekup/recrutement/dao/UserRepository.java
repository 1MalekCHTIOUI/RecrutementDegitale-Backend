package com.tekup.recrutement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tekup.recrutement.entities.User;
import com.tekup.recrutement.enums.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);

	User findByName(String name);

	User findByUserRole(UserRole userRole);
}
