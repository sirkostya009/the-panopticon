package ua.sirkostya009.userservice.service;

import ua.sirkostya009.userservice.dto.AuthenticationRequest;

public interface AuthenticationService {
    String authenticate(AuthenticationRequest request);
}
