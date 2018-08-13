package com.hfmes.sunshine.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/13 13:23
 */
@Data
@JsonIgnoreProperties(value = { "handler" })
public class MatDtl {
    private Integer matDtlId;
    private String barCode;
    private Integer matTypeId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inDate = new Date();
    private Integer num;
    private String supplier;
    private String status;
    private String memo;

    // 级联属性
    private MatType matType;

    @Override
    public String toString() {
        return "MatDtl{" +
                "matDtlId=" + matDtlId +
                ", barCode='" + barCode + '\'' +
                ", matTypeId=" + matTypeId +
                ", inDate=" + inDate +
                ", num=" + num +
                ", supplier='" + supplier + '\'' +
                ", status='" + status + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }
}
