package com.hfmes.sunshine.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/14 14:18
 * 模具维修
 */
@Data
@JsonIgnoreProperties(value = {"handler"})
public class MldRpr {
    private Integer mldRprId;
    private Integer mldDtlId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date faltTime;
    private String reporter = "";
    private String fault = "";
    private String rprName = "";
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime = new Date();
    private Integer rprId = 0;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime = new Date();
    private String faltDesc = "";
    private String reprDesc = "";
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date descTime = new Date();
}
