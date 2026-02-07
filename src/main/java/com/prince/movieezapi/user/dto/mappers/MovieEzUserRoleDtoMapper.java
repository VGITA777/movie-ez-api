package com.prince.movieezapi.user.dto.mappers;

import com.prince.movieezapi.user.configurations.SpringDtoMapperConfigs;
import com.prince.movieezapi.user.dto.MovieEzUserRoleDto;
import com.prince.movieezapi.user.models.MovieEzUserRoleModel;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(config = SpringDtoMapperConfigs.class)
public interface MovieEzUserRoleDtoMapper {

  @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
  @Mapping(source = "id", target = "id")
  @Mapping(source = "description", target = "description")
  MovieEzUserRoleDto toDto(MovieEzUserRoleModel model);

  @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
  @Mapping(source = "id", target = "id")
  @Mapping(source = "description", target = "description")
  @Mapping(target = "user", ignore = true)
  MovieEzUserRoleModel toModel(MovieEzUserRoleDto dto);
}
