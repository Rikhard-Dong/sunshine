package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.MldDtl;

import java.util.List;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 21:03
 */
public interface MldDtlDao {

    List<MldDtl> findAll();

    MldDtl findByMldDtlId(Integer mdlDtlId);
}
