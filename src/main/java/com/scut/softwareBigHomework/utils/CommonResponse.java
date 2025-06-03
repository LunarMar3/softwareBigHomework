package com.scut.softwareBigHomework.utils;

import lombok.Data;

@Data
public class CommonResponse {
    private int code;
    private String message;
    private Object data;

}
