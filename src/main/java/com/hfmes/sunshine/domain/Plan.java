package com.hfmes.sunshine.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 22:10
 */
@Data
@JsonIgnoreProperties(value = { "handler" })
public class Plan {
    private Integer planId;
    private String oderNo;
    private Integer reqNum;
    private Integer cmpNum;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reqTime = new Date();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cmpTime = new Date();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inTime = new Date();
    private String operator;
    private String source;
    private String type;
    private Integer deptId;
}
