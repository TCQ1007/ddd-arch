package io.github.tcq1007.ddd.app.infrastructure.persistent.repository;


import io.github.tcq1007.ddd.app.infrastructure.persistent.entity.MicroAppEntity;
import io.github.tcq1007.ddd.core.repository.BaseEntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MicroAppEntityRepository extends BaseEntityRepository<MicroAppEntity, String> {

}
