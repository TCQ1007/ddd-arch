package io.github.tcq1007.ddd.app.interfaces.service;


import io.github.tcq1007.ddd.app.domain.query.MicroAppBOQuery;
import io.github.tcq1007.ddd.app.interfaces.model.MicroAppQueryResponseDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MicroAppService {

    MicroAppQueryResponseDTO queryById(String id);

    List<MicroAppQueryResponseDTO> query(MicroAppBOQuery query);

    Page<MicroAppQueryResponseDTO> pageQuery(MicroAppBOQuery query, Pageable pageable);
}
