package io.github.tcq1007.ddd.app.domain.service;


import io.github.tcq1007.ddd.app.application.mapper.MicroAppBoToDtoMapper;
import io.github.tcq1007.ddd.app.domain.obj.MicroAppBO;
import io.github.tcq1007.ddd.app.domain.query.MicroAppBOQuery;
import io.github.tcq1007.ddd.app.domain.repository.MicroAppBoRepository;
import io.github.tcq1007.ddd.app.interfaces.model.MicroAppQueryResponseDTO;
import io.github.tcq1007.ddd.app.interfaces.service.MicroAppService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MicroAppServiceImpl implements MicroAppService {

    private final MicroAppBoRepository microAppBoRepository;
    private final MicroAppBoToDtoMapper microAppBoToDtoMapper;

    @Override
    public MicroAppQueryResponseDTO queryById(String id) {
        Optional<MicroAppBO> microAppBoOpt = microAppBoRepository.findById(id);
        return microAppBoOpt.map(microAppBoToDtoMapper::boToDto).orElse(null);
    }

    @Override
    public List<MicroAppQueryResponseDTO> query(MicroAppBOQuery query) {
        List<MicroAppBO> microAppBoList = microAppBoRepository.query(query);
        return Optional.ofNullable(microAppBoList)
                .map(microAppBOList ->
                        microAppBOList.stream().map(microAppBoToDtoMapper::boToDto).toList())
                .orElse(Collections.emptyList());
    }

    @Override
    public Page<MicroAppQueryResponseDTO> pageQuery(MicroAppBOQuery query, Pageable pageable) {
        Page<MicroAppBO> microAppBoPage = microAppBoRepository.pageQuery(query, pageable);
        return microAppBoPage.map(microAppBoToDtoMapper::boToDto);
    }
}
