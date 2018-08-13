package com.hfmes.sunshine.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 21:47
 */
@Data
@JsonIgnoreProperties(value = { "handler" })
public class Dept {
    private Integer deptId;
    private String title;
    private String code;
    private Integer parent;
    private String type;

    // 级联属性
    private List<Person> persons;

    @Override
    public String toString() {
        return "Dept{" +
                "deptId=" + deptId +
                ", title='" + title + '\'' +
                ", code='" + code + '\'' +
                ", parent=" + parent +
                ", type='" + type + '\'' +
                '}';
    }
}
