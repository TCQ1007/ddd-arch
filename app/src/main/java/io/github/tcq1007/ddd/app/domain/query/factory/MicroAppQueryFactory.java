package io.github.tcq1007.ddd.app.domain.query.factory;


import io.github.tcq1007.ddd.app.domain.query.MicroAppBOQuery;
import io.github.tcq1007.ddd.app.domain.query.MicroAppBOQuery.MicroAppBOQueryBuilder;
import io.github.tcq1007.ddd.app.interfaces.model.MicroAppQueryRequestDTO;

public class MicroAppQueryFactory {

    public static MicroAppBOQuery build(MicroAppQueryRequestDTO requestDTO) {
        MicroAppBOQueryBuilder builder = MicroAppBOQuery.builder();
        if (requestDTO == null) {
            return builder.build();
        }
        return builder.id(requestDTO.getId())
                .name(requestDTO.getName())
                .domain(requestDTO.getDomain())
                .build();
    }
}
