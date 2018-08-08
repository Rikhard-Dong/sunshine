package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.SCOption;

import java.util.List;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 22:15
 */
public interface SCOptionDao {
    SCOption findBySCOptionId(Integer id);

    List<SCOption> findByRoleId(Integer roleId);
}
