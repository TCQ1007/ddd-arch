package io.github.tcq1007.ddd.app.infrastructure.persistent.delegate;


import io.github.tcq1007.ddd.app.domain.obj.MicroAppBO;
import io.github.tcq1007.ddd.app.domain.query.MicroAppBOQuery;
import io.github.tcq1007.ddd.app.domain.repository.MicroAppBoRepository;
import io.github.tcq1007.ddd.app.infrastructure.persistent.entity.MicroAppEntity;
import io.github.tcq1007.ddd.app.infrastructure.persistent.repository.MicroAppEntityRepository;
import io.github.tcq1007.ddd.core.repository.SingleObjectRepositoryDelegate;
import java.util.List;
import java.util.Optional;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MicroAppRepositoryDelegateImpl
        extends SingleObjectRepositoryDelegate<MicroAppBO, MicroAppEntity, String, MicroAppEntityRepository>
        implements MicroAppBoRepository {

    public MicroAppRepositoryDelegateImpl(MicroAppEntityRepository microAppRepository,
            ConversionService conversionService) {
        super(microAppRepository, conversionService);
    }

    @Override
    public Optional<MicroAppBO> findById(String id) {
        return getEntityRepository().findById(id)
                .map(e -> conversionService.convert(e, getBOClass()));
    }

    @Override
    public List<MicroAppBO> query(MicroAppBOQuery query) {
        return getEntityRepository().findAll(
                        query != null ? hasSpecification(query) ? specification(query) : null : null)
                .stream().map(e -> conversionService.convert(e, getBOClass())).toList();
    }

    @Override
    public Page<MicroAppBO> pageQuery(MicroAppBOQuery query, Pageable pageable) {
        return getEntityRepository().findAll(
                        query != null ? hasSpecification(query) ? specification(query) : null : null, pageable)
                .map(e -> conversionService.convert(e, getBOClass()));
    }

    @Override
    public Class<MicroAppBO> getBOClass() {
        return MicroAppBO.class;
    }

    @Override
    public Class<MicroAppEntity> getEntityClass() {
        return MicroAppEntity.class;
    }
}
