package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.Role;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 16:26
 */
public interface RoleDao {

    Role findById(Integer roleId);

    String findTitleById(Integer roleId);
}
