package com.medical.controller.doctor;

import com.alibaba.fastjson.JSONObject;
import com.medical.model.CommonResponse;
import com.medical.properties.JwtProperties;
import com.medical.service.UserService;
import com.medical.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController("doctorUserController")
@RequestMapping("/doctor/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtProperties jwtProperties;

    @PostMapping("/login")
    public CommonResponse login(@RequestBody JSONObject loginParams) {
        Map<String,Object> response = userService.login(loginParams);
        Map<String, Object> claims = new HashMap<>();
        claims.put("account", response.get("account"));
        claims.put("accountAddress", response.get("accountAddress"));
        claims.put("name", response.get("name"));
        claims.put("role", response.get("role"));
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);
        response.put("token", token);
        return CommonResponse.ok(response);
    }

    //注册
    @PostMapping("/register")
    public CommonResponse register(@RequestBody JSONObject registerParams) {
        userService.register(registerParams);
        return CommonResponse.ok(null);
    }

    @GetMapping("/userinfo")
    public CommonResponse getUserInfo() {
        Map<String,Object> response = userService.getUser();
        return CommonResponse.ok(response);
    }
}
