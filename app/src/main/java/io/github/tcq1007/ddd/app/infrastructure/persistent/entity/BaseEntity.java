package io.github.tcq1007.ddd.app.infrastructure.persistent.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.type.NumericBooleanConverter;
@FilterDef(defaultCondition = "is_deleted = 0", autoEnabled = true, name = "unDeletedFilter")
@Filter(name = "unDeletedFilter")
@MappedSuperclass
public class BaseEntity implements SoftDeleteable {

    @Convert(converter = NumericBooleanConverter.class)
    @Column(name = "is_deleted", insertable = false, updatable = false)
    private boolean deleted;
}
