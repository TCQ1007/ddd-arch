package io.github.tcq1007.ddd.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseEntityRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

}
