package io.github.tcq1007.ddd.app.domain.query;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MicroAppBOQuery {

    private String id;
    private String name;
    private String domain;
}
