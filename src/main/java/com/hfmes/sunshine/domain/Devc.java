package com.hfmes.sunshine.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 20:15
 * 生产设备
 */
@Data
public class Devc {

    private Integer deviceId;
    private Integer deptId;
    private String title;
    private String type;
    private Date buyTime;
    private String status;
    private String memo;
    private Integer taskId;
    private Date taskSetDate;
    private String mldStatus;
    private Integer weightTun;

    // 级联属性
    private Dept dept;
    private Task task;

    @Override
    public String toString() {
        return "Devc{" +
                "deviceId=" + deviceId +
                ", deptId=" + deptId +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", buyTime=" + buyTime +
                ", status='" + status + '\'' +
                ", memo='" + memo + '\'' +
                ", taskId=" + taskId +
                ", taskSetDate=" + taskSetDate +
                ", mldStatus='" + mldStatus + '\'' +
                ", weightTun=" + weightTun +
                '}';
    }
}
