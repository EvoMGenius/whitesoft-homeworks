package com.evo.bunkov.feing;

import com.evo.bunkov.aspect.logging.dto.LogRequestDto;
import com.evo.bunkov.aspect.logging.dto.LogUpdateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "LOGGER-SERVICE")
public interface LoggerServiceFeingClient {

    @PostMapping("/logger/request")
    void logRequest(@RequestBody LogRequestDto logDto);

    @PostMapping("/logger/update")
    void logUpdate(@RequestBody LogUpdateDto logDto);

}
