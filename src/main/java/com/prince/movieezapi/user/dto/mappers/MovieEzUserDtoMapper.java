package com.prince.movieezapi.user.dto.mappers;

import com.prince.movieezapi.user.configurations.SpringDtoMapperConfigs;
import com.prince.movieezapi.user.dto.MovieEzUserDto;
import com.prince.movieezapi.user.models.MovieEzUserModel;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(config = SpringDtoMapperConfigs.class,
        uses = {MovieEzUserRoleDtoMapper.class, MovieEzUserPlaylistSummaryDtoMapper.class})
public interface MovieEzUserDtoMapper {

  @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
  @Mapping(source = "id", target = "id")
  @Mapping(source = "username", target = "username")
  @Mapping(source = "email", target = "email")
  @Mapping(source = "playlists", target = "playlists")
  @Mapping(source = "roles", target = "roles")
  MovieEzUserDto toDto(MovieEzUserModel model);

  @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
  @Mapping(source = "id", target = "id")
  @Mapping(source = "username", target = "username")
  @Mapping(source = "email", target = "email")
  @Mapping(source = "playlists", target = "playlists")
  @Mapping(source = "roles", target = "roles")
  @Mapping(target = "password", ignore = true)
  @Mapping(target = "isAccountNonExpired", ignore = true)
  @Mapping(target = "isAccountNonLocked", ignore = true)
  @Mapping(target = "isCredentialsNonExpired", ignore = true)
  @Mapping(target = "isEnabled", ignore = true)
  MovieEzUserModel toModel(MovieEzUserDto dto);
}
