package com.hfmes.sunshine.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 22:03
 */
@Data
public class Task {
    private Integer taskId;
    private Integer planDtlId;
    private Integer metDtlId;
    private Integer devcId;
    private Integer mldDtlId;
    private Integer devOpId;
    private Integer mldOpId;
    private Date arrDate;
    private Integer num;
    private Float matNum;
    private Date startTime;
    private Date endTime;
    private Integer procNum;
    private String status;
    private Date mldStartTime;
    private Date mldEndTime;
    private Integer testNum;
    private Date prdPlanStart;
    private Date prdPlanEnd;
    private Date mldPlanStart;
    private Date mldPlanEnd;
    private Integer bindNum;
}
