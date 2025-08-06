package io.github.tcq1007.ddd.app.interfaces.model;

import lombok.Data;

@Data
public class MicroAppQueryRequestDTO {

    private String id;
    private String name;
    private String domain;
}
