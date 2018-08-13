package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.Dept;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 21:49
 */
public interface DeptDao {

    /**
     * 根据部门id查找
     *
     * @param deptId 部门id
     * @return Dept
     */
    Dept findByDeptId(Integer deptId);
}
