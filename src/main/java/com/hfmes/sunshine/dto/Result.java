package com.hfmes.sunshine.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 10:04
 */
@Data
public class Result<T> {
    private Integer errcode;
    private String errmsg;
    private T data;


    public static Result success() {
        return newResult(0, "ok", null);
    }

    public static <T> Result success(T data) {
        return newResult(0, "ok", data);
    }

    public static Result fail(Integer errcode) {
        return newResult(errcode, null, null);
    }

    public static <T> Result newResult(Integer errcode, String errmsg, T data) {
        Result result = new Result();
        result.setErrcode(errcode);
        result.setErrmsg(errmsg);
        result.setData(data);
        return result;
    }
}
