package ua.sirkostya009.ui.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ua.sirkostya009.ui.model.LoginRequest;
import ua.sirkostya009.ui.model.RegistrationRequest;

@FeignClient(name = "auth-service", url = "${backend.urls.auth-client}", dismiss404 = true)
public interface AuthClient {
    @PostMapping("/authenticate")
    String authenticate(@RequestBody LoginRequest request);

    @PostMapping("/register")
    void register(@RequestBody RegistrationRequest request);
}
