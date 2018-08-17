package com.hfmes.sunshine.domain;

import com.fasterxml.jackson.annotation.*;
import com.hfmes.sunshine.enums.TaskStatus;
import lombok.Data;

import java.util.Date;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 22:03
 */
@Data
@JsonIgnoreProperties(value = {"handler"})
public class Task implements Cloneable {
    // 数据库字段
    private Integer taskId;
    private Integer planDtlId;
    private Integer matDtlId;
    private Integer devcId;
    private Integer mldDtlId;
    private Integer devOpId;
    private Integer mldOpId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date arrDate = new Date();
    ;
    private Integer num;
    private Float matNum;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime = new Date();
    ;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime = new Date();
    private Integer procNum;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date mldStartTime = new Date();
    ;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date mldEndTime = new Date();
    ;
    private Integer testNum;
    private Integer setNum;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date prdPlanStart = new Date();
    ;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date prdPlanEnd = new Date();
    ;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date mldPlanStart = new Date();
    ;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date mldPlanEnd = new Date();
    ;
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


    public Task() {

    }

    /**
     * 产生新单时调用的构造方法
     *
     * @param task
     */
    public Task(Task task) {
        this.planDtlId = task.getPlanDtlId();
        this.planDtl = task.getPlanDtl();
        this.matDtlId = task.getMatDtlId();
        this.matDtl = task.getMatDtl();
        this.devcId = task.getDevcId();
        this.devc = task.getDevc();
        this.mldDtlId = task.getMldDtlId();
        this.mldDtl = task.getMldDtl();
        this.devOpId = task.getDevOpId();
        this.devOp = task.getDevOp();
        this.mldOpId = task.getMldOpId();
        this.mldOp = task.getMldOp();
        this.arrDate = task.getArrDate();
        this.matNum = task.getMatNum();
        this.setNum = task.getSetNum() - task.getProcNum();
        this.procNum = 0;
        this.testNum = 0;
        this.startTime = new Date();
        this.endTime = new Date();
        this.status = TaskStatus.ST00.toString();
        this.mldStartTime = new Date();
        this.mldEndTime = new Date();
        this.prdPlanStart = new Date();
        this.prdPlanEnd = new Date();
        this.mldPlanStart = new Date();
        this.mldPlanEnd = new Date();
        this.bindNum = 0;
        this.num = task.getNum();
    }


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

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
