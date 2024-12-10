package com.teset.testUnitario.service;

import com.teset.repository.IPingRepository;
import com.teset.service.impl.PingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PingServiceImplTest {

    @Mock
    private IPingRepository pingRepository;

    @InjectMocks
    private PingServiceImpl pingService;

    @Test
    void getPong_returnsPongFromRepository() {
        String expectedResponse = "pong";
        when(pingRepository.getPong()).thenReturn(expectedResponse);

        assertEquals(expectedResponse, pingService.getPong());
    }
}
