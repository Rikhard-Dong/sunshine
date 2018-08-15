package com.hfmes.sunshine.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/13 8:32
 */
@Data
@JsonIgnoreProperties(value = {"handler"})
public class StatusData {
    private Integer statusDataId;
    private Integer statusTypeId;
    private String curStatus;
    private String nextStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date start;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date stop;
    private Integer hold;
    private Integer count;
    private String eventType;
    private String eventName;
    private Integer opId;
    private Integer devId;
    private Integer taskId;
    private Integer mldId;
}
