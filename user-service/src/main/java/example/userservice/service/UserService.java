package example.userservice.service;

import example.userservice.dto.UserDto;

public interface UserService {

    UserDto createUser(UserDto userDto);
}
