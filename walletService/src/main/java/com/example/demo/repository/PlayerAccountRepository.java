package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.PlayerAccountInfo;

@Repository
public interface PlayerAccountRepository extends JpaRepository<PlayerAccountInfo,Long>{

}
