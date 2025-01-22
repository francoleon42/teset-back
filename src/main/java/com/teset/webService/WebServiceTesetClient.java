package com.teset.webService;

import com.teset.dto.cliente.ClienteResponseDTO;
import com.teset.dto.cliente.DetalleResponseDTO;
import com.teset.dto.negocio.ComercioResponseDTO;
import com.teset.dto.wsTeset.ClienteResponseWbDTO;
import com.teset.utils.enums.EstadoCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


@Component
@RequiredArgsConstructor
public class WebServiceTesetClient implements  IWebServiceTesetClient{
    private final RestTemplate restTemplate;
    private String baseUrl = System.getenv("URL_TESET_WEBSERVICE");

    @Override
    public ClienteResponseDTO getClienteTeset(String dni) {
        HttpHeaders headers = getHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + "/devices");

        ResponseEntity<ClienteResponseWbDTO> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                ClienteResponseWbDTO.class
        );


        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return ClienteResponseDTO
                    .builder()
                    .dni(dni)
//                    .email(response.getBody().getEmail())
                    .estado(EstadoCliente.DISPONIBLE)
                    .build();
        } else {
            throw new RuntimeException("Error al obtener los dispositivos: " + response.getStatusCode());
        }
    }

    @Override
    public DetalleResponseDTO getDetalleClienteTeset(String dni) {
        return null;
    }

    @Override
    public List<ComercioResponseDTO> getComerciosAdheridosTeset() {
        return List.of();
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        return headers;
    }
}
