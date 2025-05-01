package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

	@Query(value = "select * from users where mobile_number = ? and status = true", nativeQuery = true)
	Optional<Users> findByMobileNumber(String mobile_number);

	@Query(value = "select * from users where email = ? and status = true", nativeQuery = true)
	Optional<Users> findByEmail(String email);

	@Query(value = "select * from users where id = ? and status = true", nativeQuery = true)
	Optional<Users> findbyUserId(Long id);

}
