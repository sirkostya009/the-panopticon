package ua.sirkostya009.ui.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import ua.sirkostya009.ui.model.User;

@FeignClient(name = "user-service", url = "${backend.urls.user-client}")
public interface UserClient {
    @GetMapping("/self")
    User self(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);
}
