package com.hfmes.sunshine.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 22:10
 */
@Data
public class Plan {
    private Integer planId;
    private String oderNo;
    private Integer reqNum;
    private Integer cmpNum;
    private String status;
    private Date reqTime;
    private Date cmpTime;
    private Date inTime;
    private String opeartor;
    private String source;
    private String type;
    private Integer deptId;
}
