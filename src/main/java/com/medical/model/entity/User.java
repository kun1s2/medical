package com.medical.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class User {
    private String account;
    private String password;
    private String accountAddress;
    private Integer role;
}
