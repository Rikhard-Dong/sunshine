package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.Dept;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 21:49
 */
public interface DeptDao {

    Dept findByDeptId(Integer deptId);
}
