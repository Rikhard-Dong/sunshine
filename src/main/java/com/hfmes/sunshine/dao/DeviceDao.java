package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.Devc;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 20:34
 */
public interface DeviceDao {

    Devc findByDeviceId(Integer id);
}
