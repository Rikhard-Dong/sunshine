package com.hfmes.sunshine.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Arrays;
import java.util.Date;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 21:22
 * 用户
 */
@Data
@JsonIgnoreProperties(value = { "handler" })
public class Person {

    private Integer personId;
    private String cardNo;
    private String username;
    private String password;
    private String name;
    private String birth;
    private String born;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enDate = new Date();
    private Integer deptId;
    private String memo;
    private String status;
    private String sex;
    private String post;
    private String nation;
    private String marriage;
    private String address;
    private String tel;
    private String IDNumber;
    private Byte[] photo;
    private Integer devcId;

    // 级联属性
    private Dept dept;

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", cardNo='" + cardNo + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", birth='" + birth + '\'' +
                ", born='" + born + '\'' +
                ", enDate=" + enDate +
                ", deptId=" + deptId +
                ", memo='" + memo + '\'' +
                ", status='" + status + '\'' +
                ", sex='" + sex + '\'' +
                ", post='" + post + '\'' +
                ", nation='" + nation + '\'' +
                ", marriage='" + marriage + '\'' +
                ", address='" + address + '\'' +
                ", tel='" + tel + '\'' +
                ", IDNumber='" + IDNumber + '\'' +
                ", photo=" + Arrays.toString(photo) +
                ", devcId=" + devcId +
                '}';
    }
}
