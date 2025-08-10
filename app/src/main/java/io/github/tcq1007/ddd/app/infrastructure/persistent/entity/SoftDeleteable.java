package io.github.tcq1007.ddd.app.infrastructure.persistent.entity;

public interface SoftDeleteable {
    default String softDeleteAttributeName(){return "deleted";}
}
