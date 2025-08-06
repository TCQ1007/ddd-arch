package io.github.tcq1007.ddd.app.application.controller;


import io.github.tcq1007.ddd.app.domain.query.factory.MicroAppQueryFactory;
import io.github.tcq1007.ddd.app.interfaces.endpoint.IMicroAppController;
import io.github.tcq1007.ddd.app.interfaces.model.MicroAppQueryRequestDTO;
import io.github.tcq1007.ddd.app.interfaces.model.MicroAppQueryResponseDTO;
import io.github.tcq1007.ddd.app.interfaces.service.MicroAppService;
import io.github.tcq1007.ddd.core.request.BasePageRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MicroAppController implements IMicroAppController {

    private final MicroAppService microAppService;

    @Override
    public ResponseEntity<List<MicroAppQueryResponseDTO>> query(MicroAppQueryRequestDTO requestDTO) {
        return ResponseEntity.ok(microAppService.query(MicroAppQueryFactory.build(requestDTO)));
    }

    @Override
    public ResponseEntity<PagedModel<MicroAppQueryResponseDTO>> pageQuery(
            BasePageRequest<MicroAppQueryRequestDTO> requestDTO) {
        PageRequest pageable = PageRequest.of(requestDTO.getPageNumber() - 1, requestDTO.getPageSize());
        Page<MicroAppQueryResponseDTO> page = microAppService.pageQuery(
                MicroAppQueryFactory.build(requestDTO.getQuery()), pageable);
        return ResponseEntity.ok(new PagedModel<>(page));
    }
}
