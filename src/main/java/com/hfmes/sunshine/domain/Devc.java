package com.hfmes.sunshine.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 20:15
 * 生产设备
 */
@Data
@JsonIgnoreProperties(value = {"handler"})
public class Devc implements Serializable {

    @JsonProperty("devcId")
    private Integer deviceId;
    private Integer deptId;
    private String title;
    private String type;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date buyTime;
    private String status;
    private String memo;
    private Integer taskId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date taskSetDate;
    private String mldStatus;
    private Integer weightTun;

    // 级联属性, 序列化时忽略
    @JsonIgnore
    private Dept dept;
    @JsonIgnore
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
