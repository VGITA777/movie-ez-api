package com.prince.movieezapi.user.dto.mappers;

import com.prince.movieezapi.user.configurations.SpringDtoMapperConfigs;
import com.prince.movieezapi.user.dto.MovieEzPlaylistContentSummaryDto;
import com.prince.movieezapi.user.models.MovieEzPlaylistContentModel;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(config = SpringDtoMapperConfigs.class) public interface MovieEzPlaylistContentSummaryDtoMapper {

  @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
  @Mapping(source = "id", target = "id")
  @Mapping(source = "trackId", target = "trackId")
  MovieEzPlaylistContentSummaryDto toDto(MovieEzPlaylistContentModel model);
}
