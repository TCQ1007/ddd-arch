package io.github.tcq1007.ddd.app.infrastructure.persistent.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.*;
import org.hibernate.type.NumericBooleanConverter;

@Entity
@Table(name = "micro_app")
@Data
public class MicroAppEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "domain")
    private String domain;

    @Column(name = "base_path")
    private String basePath;

}
