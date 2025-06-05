package com.scut.softwareBigHomework.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse {
    private int code;
    private String message;
    private Object data;

    public CommonResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static CommonResponse success(Object data) {
        return new CommonResponse(200, "success", data);
    }

    public static CommonResponse fail(String message) {
        return new CommonResponse(500, message, null);
    }
}
