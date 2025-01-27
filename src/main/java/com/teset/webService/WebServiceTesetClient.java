package com.teset.webService;

import ch.qos.logback.core.CoreConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teset.dto.cliente.ClienteResponseDTO;
import com.teset.dto.cliente.DetalleResponseDTO;
import com.teset.dto.negocio.ComercioResponseDTO;
import com.teset.dto.wsTeset.ClienteResponseWbDTO;
import com.teset.dto.wsTeset.ComercioResponseWbDTO;
import com.teset.dto.wsTeset.DetalleResponseWbDTO;
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
public class WebServiceTesetClient implements IWebServiceTesetClient {
    private final RestTemplate restTemplate;
    private String baseUrl = System.getenv("URL_TESET_WEBSERVICE");

    @Override
    public ClienteResponseDTO getClienteTeset(String dni) {

        HttpHeaders headers = getHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + "/ClienteDisponible/" + dni);
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                ClienteResponseWbDTO clienteResponse = objectMapper.readValue(response.getBody(), ClienteResponseWbDTO.class);

                return ClienteResponseDTO
                        .builder()
                        .dni(dni)
                        .nombre(clienteResponse.getNombre())

                        //Revisar de donde se obtiene total a pagar
                        .totalAPagar(clienteResponse.getSaldoAPagar())
                        .saldoDisponible(clienteResponse.getDisponible())

                        .estado(EstadoCliente.DISPONIBLE)

                        //ADD email
                        .email("costantinifranco2001@gmail.com")
                        .build();
            } else {
                throw new RuntimeException("Error al obtener el cliente: " + response.getStatusCode());
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<DetalleResponseDTO> getDetallesClienteTeset(String dni) {

        HttpHeaders headers = getHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + "/ClienteDetalle/" + dni);
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                List<DetalleResponseWbDTO> detallesResponseWbDTO = objectMapper.readValue(
                        response.getBody(),
                        objectMapper.getTypeFactory().constructCollectionType(List.class, DetalleResponseWbDTO.class)
                );

                return detallesResponseWbDTO.stream()
                        .map(detalle -> DetalleResponseDTO.builder()
                                .id(detalle.getCodCom())
                                .fecha(detalle.getFechaEmision())
                                .cuota(detalle.getCuota())

                                //REVISAR DUDA DE IMPORTE
                                .importe(detalle.getImporte())
                                .build()
                        )
                        .toList();
            } else {
                throw new RuntimeException("Error al obtener los detalles: " + response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al procesar la respuesta: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ComercioResponseDTO> getComerciosAdheridosTeset() {

        HttpHeaders headers = getHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + "/Comercios/");
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                List<ComercioResponseWbDTO> comercioResponseWbDTO = objectMapper.readValue(
                        response.getBody(),
                        objectMapper.getTypeFactory().constructCollectionType(List.class, ComercioResponseWbDTO.class)
                );

                return comercioResponseWbDTO.stream()
                        .map(comercioWb -> ComercioResponseDTO.builder()
                                .id(comercioWb.getCodcom())
                                .nombre(comercioWb.getCnombre())
                                .direccion(comercioWb.getCdomici())
                                .telefono(comercioWb.getCtelnumero())
                                //falta agregar link de negocio y logo
                                .build()
                        )
                        .toList();
            } else {
                throw new RuntimeException("Error al obtener los comercios: " + response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al procesar la respuesta: " + e.getMessage(), e);
        }
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }


}
