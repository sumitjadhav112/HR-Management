package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

	@Query(value = "select * from client where status = true order by creation_time desc", nativeQuery = true)
	List<Client> findAllClientByOrder();

}
