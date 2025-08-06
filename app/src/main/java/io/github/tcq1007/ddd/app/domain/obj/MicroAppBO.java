package io.github.tcq1007.ddd.app.domain.obj;

import lombok.Data;

@Data
public class MicroAppBO {
    private String id;
    private String name;
    private String domain;
    private String basePath;
}
