package com.medical.service;

import com.alibaba.fastjson.JSONObject;
import com.medical.model.entity.User;

import java.util.Map;

public interface UserService {
    Map<String,Object> login(JSONObject loginParams);

    void register(JSONObject registerParams);

    Map<String, Object> getUser();

}
