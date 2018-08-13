package com.hfmes.sunshine.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 16:25
 */
@Data
@JsonIgnoreProperties(value = { "handler" })
public class Role {
    private Integer roleId;
    private String title;
    private String memo;
}
