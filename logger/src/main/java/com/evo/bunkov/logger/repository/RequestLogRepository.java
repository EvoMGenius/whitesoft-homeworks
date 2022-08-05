package com.evo.bunkov.logger.repository;

import com.evo.bunkov.logger.model.RequestLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RequestLogRepository extends JpaRepository<RequestLog, UUID> {
}
