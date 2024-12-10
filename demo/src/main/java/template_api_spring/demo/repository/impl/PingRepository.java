package template_api_spring.demo.repository.impl;

import org.springframework.stereotype.Service;
import template_api_spring.demo.repository.IPingRepository;

@Service
public class PingRepository implements IPingRepository {
    @Override
    public String getPong() {
        return "pong";
    }
}
