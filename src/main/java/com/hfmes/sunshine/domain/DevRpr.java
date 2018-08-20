package com.hfmes.sunshine.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/14 14:13
 * <p>
 * 设备维修
 */
@Data
@JsonIgnoreProperties(value = {"handler"})
public class DevRpr {
    private Integer devRprId;
    private Integer devcId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date faltTime;
    private String reporter = "";
    private String fault = "";
    private Integer repairerId = 0;
    private String rprName = "";
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime = new Date();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime = new Date();
    private String faltDesc = "";
    private String reprDesc = "";
    private Date descTime = new Date();
}
