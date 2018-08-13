package com.hfmes.sunshine.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/13 10:36
 */
@Data
@JsonIgnoreProperties(value = {"handler"})
public class PlanDtl {
    private Integer planDtlId;
    private Integer planId;
    private Integer prdtTypeId;
    private Integer reqNum;
    private String explain;
    private Integer cmpNum;
    private String status;
    private Integer devReq;
    private Integer mldReq;
    private String color;
    private String lr;
    private String mark;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reqTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cmpTime;
    private String pin;
}
