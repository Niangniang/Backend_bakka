package org.bakka.userservice.Mappers;


import org.bakka.userservice.Dtos.RoleRequestDto;
import org.bakka.userservice.Dtos.RoleResponseDto;
import org.bakka.userservice.Entities.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {


    RoleResponseDto roleToRoleResponseDto(Role role);

    Role roleRequestDtoToRole(RoleRequestDto roleRequestDto);
}
