package com.example.SprintMaster.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SprintMaster.model.Logger;

@Repository
public interface LoggerRepository extends JpaRepository<Logger, Integer> {

}
