package kz.aha.bot.data.bom.service.impl;

import kz.aha.bot.data.bom.service.CommonService;
import kz.aha.bot.data.common.dto.ParamDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Akhmet.Sulemenov
 * created 28.06.2023
 */
@Service
@RequiredArgsConstructor
public class DefaultCommonService implements CommonService {

    private final RestTemplate restTemplate;

    private List<?> bodyList = new ArrayList<>();
    private Object body;

    @Override
    public ResponseEntity<Object> exchange(String url, List<ParamDTO> params, HttpMethod method) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        if (params.size() > 0)
            headers.set("Authorization", params.get(0).getValue());
        headers.setAccessControlAllowOrigin("*");


        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

        for (ParamDTO dto: params) {
            builder.queryParam(dto.getKey(), dto.getValue());
        }

        HttpEntity<?> entity;
        if (Objects.nonNull(bodyList) && bodyList.size() > 0) {
            entity = new HttpEntity<>(bodyList, headers);
        } else if (Objects.nonNull(body)) {
            entity = new HttpEntity<>(body, headers);
        } else
            entity = new HttpEntity<>(headers);

        ParameterizedTypeReference<Object> typeRef = new ParameterizedTypeReference<>() {};
        ResponseEntity<Object> response = restTemplate.exchange(
                builder.encode().build().toUri(),
                method,
                entity,
                typeRef);

        return response;
    }

    @Override
    public ResponseEntity<Object> exchange(String url, List<ParamDTO> params, List<?> pBody, HttpMethod method) {
        this.bodyList = pBody;
        return this.exchange(url, params, method);
    }

    @Override
    public ResponseEntity<Object> exchange(String url, List<ParamDTO> params, Object pBody, HttpMethod method) {
        this.body = pBody;
        return this.exchange(url, params, method);
    }
}
