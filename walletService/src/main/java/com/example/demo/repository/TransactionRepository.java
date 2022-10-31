package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.TransactionInfo;

public interface TransactionRepository extends JpaRepository<TransactionInfo,Long> {
	
	@Modifying
	@Query("delete from TransactionInfo where transactionId=:id")
	void deleteById(@Param("id") Long id);
}
