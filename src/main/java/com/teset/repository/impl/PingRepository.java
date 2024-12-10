package com.teset.repository.impl;

import org.springframework.stereotype.Service;
import com.teset.repository.IPingRepository;

@Service
public class PingRepository implements IPingRepository {
    @Override
    public String getPong() {
        return "pong";
    }
}
