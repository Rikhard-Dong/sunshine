package com.hfmes.sunshine.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 22:03
 */
@Data
@JsonIgnoreProperties(value = {"handler"})
public class Task {
    // 数据库字段
    private Integer taskId;
    private Integer planDtlId;
    private Integer matDtlId;
    private Integer devcId;
    private Integer mldDtlId;
    private Integer devOpId;
    private Integer mldOpId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date arrDate = new Date();;
    private Integer num;
    private Float matNum;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime = new Date();;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime = new Date();
    private Integer procNum;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date mldStartTime = new Date();;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date mldEndTime = new Date();;
    private Integer testNum;
    private Integer setNum;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date prdPlanStart = new Date();;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date prdPlanEnd = new Date();;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date mldPlanStart = new Date();;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date mldPlanEnd = new Date();;
    private Integer bindNum;

    //其他冲压数量  包括架模/维修等数量
    private Integer otherNum;

    // 级联属性
    private PlanDtl planDtl;
    private MatDtl matDtl;
    private Devc devc;
    //    @JsonIgnore
    private Person devOp;
    //    @JsonIgnore
    private Person mldOp;
    //    @JsonIgnore
    private MldDtl mldDtl;

//    @JsonGetter(value = "mdlDtl")
//    public MldDtl getMldDtlDetail() {
//        return mldDtl;
//    }

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
