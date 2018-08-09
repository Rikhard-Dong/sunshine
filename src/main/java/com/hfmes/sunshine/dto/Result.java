package com.hfmes.sunshine.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 10:04
 */
@Data
public class Result {
    private Integer errcode;
    private String errmsg;
    private Map<String, Object> data = new HashMap<>();

    public Result addData(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public Result success() {
        return newResult(0, "ok", null);
    }

    public Result success(Map<String, Object> data) {
        return newResult(0, "ok", data);
    }

    public Result fail(Integer errcode) {
        return newResult(errcode, null, null);
    }

    public Result newResult(Integer errcode, String errmsg, Map<String, Object> data) {
        Result result = new Result();
        result.setErrcode(errcode);
        result.setErrmsg(errmsg);
        result.setData(data);
        return result;
    }
}
