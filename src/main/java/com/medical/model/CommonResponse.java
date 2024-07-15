package com.medical.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse {

    public static final String OK = "200";
    public static final String ERROR = "0";
    private String code;
    private String message;
    private Object data;

    public static CommonResponse ok(Object data){
        return new CommonResponse(OK, "",data);
    }

    public static CommonResponse fail(String message){
        return new CommonResponse(ERROR,message,null);
    }
}
