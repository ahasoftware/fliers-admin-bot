package kz.aha.bot.data.bom.service;

import kz.aha.bot.data.common.dto.ParamDTO;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Akhmet.Sulemenov
 * created 28.06.2023
 */
@Service
public interface CommonService {
    ResponseEntity<Object> exchange(String url, List<ParamDTO> params, HttpMethod method);

    ResponseEntity<Object> exchange(String url, List<ParamDTO> params, List<?> pBody, HttpMethod method);

    ResponseEntity<Object> exchange(String url, List<ParamDTO> params, Object pBody, HttpMethod method);
}
