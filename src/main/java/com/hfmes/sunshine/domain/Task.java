package com.hfmes.sunshine.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 22:03
 */
@Data
public class Task {
    // 数据库字段
    private Integer taskId;
    private Integer planDtlId;
    private Integer matDtlId;
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
    private Integer setNum;
    private Date prdPlanStart;
    private Date prdPlanEnd;
    private Date mldPlanStart;
    private Date mldPlanEnd;
    private Integer bindNum;

    //其他冲压数量  包括架模/维修等数量
    private Integer otherNum;

    // 级联属性, 序列化时忽略
    @JsonIgnore
    private Person devOp;
    @JsonIgnore
    private Person mldOp;

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", planDtlId=" + planDtlId +
                ", matDtlId=" + matDtlId +
                ", devcId=" + devcId +
                ", mldDtlId=" + mldDtlId +
                ", devOpId=" + devOpId +
                ", mldOpId=" + mldOpId +
                ", arrDate=" + arrDate +
                ", num=" + num +
                ", matNum=" + matNum +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", procNum=" + procNum +
                ", status='" + status + '\'' +
                ", mldStartTime=" + mldStartTime +
                ", mldEndTime=" + mldEndTime +
                ", testNum=" + testNum +
                ", setNum=" + setNum +
                ", prdPlanStart=" + prdPlanStart +
                ", prdPlanEnd=" + prdPlanEnd +
                ", mldPlanStart=" + mldPlanStart +
                ", mldPlanEnd=" + mldPlanEnd +
                ", bindNum=" + bindNum +
                '}';
    }
}
