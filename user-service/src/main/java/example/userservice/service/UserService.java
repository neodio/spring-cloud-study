package example.userservice.service;

import example.userservice.dto.UserDto;
import example.userservice.entity.UserEntity;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto getUserByUserId(String userId);

    Iterable<UserEntity> getUserByAll();
}
