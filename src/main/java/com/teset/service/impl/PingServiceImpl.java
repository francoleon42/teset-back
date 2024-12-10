package com.teset.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.teset.repository.IPingRepository;
import com.teset.service.IPingService;

@Service
@RequiredArgsConstructor
public class PingServiceImpl implements IPingService {
    private final IPingRepository pingRepository;

    @Override
    public String getPong() {
        return pingRepository.getPong();
    }
}
