package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.Devc;

import java.util.List;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 20:34
 */
public interface DevcDao {

    Devc findByDeviceId(Integer id);

    List<Devc> findAll();
}
