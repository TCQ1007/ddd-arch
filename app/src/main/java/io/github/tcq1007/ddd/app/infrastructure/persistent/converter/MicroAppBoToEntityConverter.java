package io.github.tcq1007.ddd.app.infrastructure.persistent.converter;


import io.github.tcq1007.ddd.app.domain.obj.MicroAppBO;
import io.github.tcq1007.ddd.app.infrastructure.persistent.entity.MicroAppEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.extensions.spring.DelegatingConverter;
import org.springframework.core.convert.converter.Converter;

@Mapper(
        componentModel = "spring"
)
public interface MicroAppBoToEntityConverter extends Converter<MicroAppBO, MicroAppEntity> {

    @Override
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "domain", source = "domain")
    @Mapping(target = "basePath", source = "basePath")
    MicroAppEntity convert(MicroAppBO source);

    @InheritInverseConfiguration
    @DelegatingConverter
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "domain", source = "domain")
    @Mapping(target = "basePath", source = "basePath")
    MicroAppBO invertConvert(MicroAppEntity microAppEntity);
}
