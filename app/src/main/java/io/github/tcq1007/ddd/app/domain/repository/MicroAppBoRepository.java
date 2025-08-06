package io.github.tcq1007.ddd.app.domain.repository;


import io.github.tcq1007.ddd.annotation.DelegateMethod;
import io.github.tcq1007.ddd.annotation.RepositoryMapper;
import io.github.tcq1007.ddd.app.domain.obj.MicroAppBO;
import io.github.tcq1007.ddd.app.domain.query.MicroAppBOQuery;
import io.github.tcq1007.ddd.app.infrastructure.persistent.entity.MicroAppEntity;
import io.github.tcq1007.ddd.core.repository.BaseBORepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RepositoryMapper(entityClass = MicroAppEntity.class)
public interface MicroAppBoRepository extends BaseBORepository<MicroAppBO> {

    @DelegateMethod(jpaMethodName = "findById")
    Optional<MicroAppBO> findById(String id);

    @DelegateMethod(jpaMethodName = "findAll")
    List<MicroAppBO> query(MicroAppBOQuery query);

    @DelegateMethod(jpaMethodName = "findAll")
    Page<MicroAppBO> pageQuery(MicroAppBOQuery query, Pageable pageable);

    @Override
    default Class<MicroAppBO> getBOClass() {
        return MicroAppBO.class;
    }
}
