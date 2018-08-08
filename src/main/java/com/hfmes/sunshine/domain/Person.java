package com.hfmes.sunshine.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 21:22
 * 用户
 */
@Data
public class Person {

    private Integer personId;
    private String cardNo;
    private String username;
    private String password;
    private String name;
    private String birth;
    private String born;
    private Date enDate;
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
    private Byte[] image;
    private Integer devcId;
}
