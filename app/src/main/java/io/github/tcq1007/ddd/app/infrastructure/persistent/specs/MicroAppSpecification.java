package io.github.tcq1007.ddd.app.infrastructure.persistent.specs;


import io.github.tcq1007.ddd.app.domain.query.MicroAppBOQuery;
import io.github.tcq1007.ddd.app.infrastructure.persistent.entity.MicroAppEntity;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.jpa.domain.Specification;

@Configuration
public class MicroAppSpecification {

    @Bean
    public Converter<MicroAppBOQuery, Specification<MicroAppEntity>> convert() {
        return MicroAppSpecification::build;
    }

    public static Specification<MicroAppEntity> build(MicroAppBOQuery query) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            // 用于存储查询条件的集合
            List<Predicate> predicates = new ArrayList<>();
            String id = query.getId();
            if (StringUtils.isNotEmpty(id)) {
                predicates.add(criteriaBuilder.equal(root.get("id"), id));
            }
            String name = query.getName();
            if (StringUtils.isNotEmpty(name)) {
                // 注意：root.get("name") 中的 "name" 需与 MicroAppEntity 的属性名一致
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            String domain = query.getDomain();
            if (StringUtils.isNotEmpty(domain)) {
                // 注意：root.get("domain") 中的 "domain" 需与 MicroAppEntity 的属性名一致
                predicates.add(criteriaBuilder.equal(root.get("domain"), domain));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
