package com.hfmes.sunshine.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 20:56
 */
@Data
public class MldDtl {
    private Integer mldDtlId;
    private Integer mldTypeId;
    private String barCode;
    private String title;
    private String code;
    private String spec;
    private String unit;
    private String type;
    private Float cycle;
    private Float weight;
    private Integer num;
    private String model;
    private Date enDate;
    private String operator;
    private String status;
    private String memo;
    private Float capacity;
}
