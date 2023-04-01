package ua.sirkostya009.userservice.service;

import ua.sirkostya009.userservice.dto.UserPasswordDto;

public interface RegistrationService {
    void register(UserPasswordDto request);

    void confirm(String uuid);
}
