package com.hfmes.sunshine.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 22:34
 */
@Data
@JsonIgnoreProperties(value = { "handler" })
public class DicItem {
    private Integer dicItemId;
    private String title;
    private String code;
    private Integer dicTypeId;
    private String memo;
}
