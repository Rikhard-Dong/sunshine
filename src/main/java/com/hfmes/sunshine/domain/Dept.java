package com.hfmes.sunshine.domain;

import lombok.Data;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 21:47
 */
@Data
public class Dept {
    private Integer deptId;
    private String title;
    private String code;
    private Integer parent;
    private String type;

}
