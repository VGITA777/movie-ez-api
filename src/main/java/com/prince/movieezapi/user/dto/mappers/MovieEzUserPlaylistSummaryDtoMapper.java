package com.prince.movieezapi.user.dto.mappers;

import com.prince.movieezapi.user.configurations.SpringDtoMapperConfigs;
import com.prince.movieezapi.user.dto.MovieEzUserPlaylistSummaryDto;
import com.prince.movieezapi.user.models.MovieEzUserPlaylistModel;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(config = SpringDtoMapperConfigs.class)
public interface MovieEzUserPlaylistSummaryDtoMapper {

  @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
  @Mapping(source = "id", target = "id")
  @Mapping(source = "name", target = "name")
  MovieEzUserPlaylistSummaryDto toDto(MovieEzUserPlaylistModel model);

  @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
  @Mapping(source = "id", target = "id")
  @Mapping(source = "name", target = "name")
  MovieEzUserPlaylistModel toModel(MovieEzUserPlaylistSummaryDto dto);
}
