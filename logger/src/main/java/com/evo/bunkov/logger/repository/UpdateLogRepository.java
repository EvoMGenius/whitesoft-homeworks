package com.evo.bunkov.logger.repository;

import com.evo.bunkov.logger.model.UpdateLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UpdateLogRepository extends JpaRepository<UpdateLog, UUID> {
}
