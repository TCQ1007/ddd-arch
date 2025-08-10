package io.github.tcq1007.ddd.app.application;

import io.github.tcq1007.ddd.app.infrastructure.persistent.repository.SoftDeleteRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"io.github.tcq1007.ddd.app"})
@EnableJpaRepositories(basePackages = {"io.github.tcq1007.ddd.app"},repositoryBaseClass = SoftDeleteRepositoryImpl.class)
@SpringBootApplication(scanBasePackages = {"io.github.tcq1007.ddd.app"})
public class DddRepositoryDelegateApplication {

    public static void main(String[] args) {
        SpringApplication.run(DddRepositoryDelegateApplication.class, args);
    }

}
