package ua.sirkostya009.userservice.service;

import ua.sirkostya009.userservice.dto.UserPasswordDto;
import ua.sirkostya009.userservice.model.User;

public interface UserService {
    User save(UserPasswordDto dto);

    User findById(String id);

    User findByUsername(String username);

    void disable(String id);

    void enable(String id);
}
