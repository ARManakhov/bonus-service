package dev.sirosh.bonus_service.mapper;

import dev.sirosh.bonus_service.dto.UserDto;
import dev.sirosh.bonus_service.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);

    List<UserDto> toUserDto(List<User> user);

}
