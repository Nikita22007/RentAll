package ru.rsreu.rentall.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.rsreu.rentall.dto.UserDTO;
import ru.rsreu.rentall.entity.User;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDTO userToUserDTO(User user);
    User userDTOtoUser(UserDTO userDTO);
}
