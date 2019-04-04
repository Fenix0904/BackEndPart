package com.site.backend.controller.fake;

import com.site.backend.domain.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FakeSwaggerLoginController {

    @ApiOperation("Login.")
    @PostMapping("/login")
    public void fakeLogin(@RequestBody User user) {
        throw new IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.");
    }
}
