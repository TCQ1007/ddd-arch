package io.github.tcq1007.ddd.core.repository;

public interface BaseBORepository<BO> {

    Class<BO> getBOClass();
}
