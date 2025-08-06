package io.github.tcq1007.ddd.app.application.mapper;


import io.github.tcq1007.ddd.app.domain.obj.MicroAppBO;
import io.github.tcq1007.ddd.app.interfaces.model.MicroAppQueryResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MicroAppBoToDtoMapper {

    MicroAppQueryResponseDTO boToDto(MicroAppBO bo);
}
