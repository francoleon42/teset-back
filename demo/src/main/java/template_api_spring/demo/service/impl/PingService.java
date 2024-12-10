package template_api_spring.demo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import template_api_spring.demo.repository.IPingRepository;
import template_api_spring.demo.service.IPingService;

@Service
@RequiredArgsConstructor
public class PingService implements IPingService {
    private final IPingRepository pingRepository;

    @Override
    public String getPong() {
        return pingRepository.getPong();
    }
}
