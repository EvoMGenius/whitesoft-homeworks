package com.evo.bunkov.logger.service;

import com.evo.bunkov.logger.model.RequestLog;
import com.evo.bunkov.logger.repository.RequestLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestLogService {

    private final RequestLogRepository repository;

    public RequestLog save(RequestLog log) {
        return repository.save(log);
    }

    public List<RequestLog> findAll() {
        return repository.findAll();
    }
}
