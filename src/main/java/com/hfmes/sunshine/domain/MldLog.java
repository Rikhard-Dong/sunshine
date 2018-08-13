package com.hfmes.sunshine.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/13 16:13
 */
@Data
@JsonIgnoreProperties(value = { "handler" })
public class MldLog {
    private Integer mldLogId;
    private Integer mldDtlId;
    private Integer taskId;
    private Integer opId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date opTime;
    private String opDesc;
    private String opName;
    private String opType;
}
