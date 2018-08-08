package com.hfmes.sunshine.domain;

import lombok.Data;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 22:34
 */
@Data
public class DicItem {
    private Integer dicItemId;
    private String title;
    private String code;
    private Integer dicTypeId;
    private String memo;
}
