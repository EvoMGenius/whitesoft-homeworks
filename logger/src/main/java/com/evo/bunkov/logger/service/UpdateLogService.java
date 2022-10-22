package com.evo.bunkov.logger.service;

import com.evo.bunkov.logger.model.UpdateLog;
import com.evo.bunkov.logger.repository.UpdateLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateLogService {

    private final UpdateLogRepository repository;

    public UpdateLog save(UpdateLog log){
        return repository.save(log);
    }

    public List<UpdateLog> findAll() {
        return repository.findAll();
    }
}
