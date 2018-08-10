package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.MldDtl;

import java.util.List;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 21:03
 */
public interface MldDtlDao {

    /**
     * 获取所有模具
     *
     * @return list of MldDtls
     */
    List<MldDtl> findAll();

    /**
     * 根据模具id获取模具信息
     *
     * @param mdlDtlId mldDtl id
     * @return 模具对象
     */
    MldDtl findByMldDtlId(Integer mdlDtlId);

    /**
     * 获取当前模具对象的状态
     *
     * @param mldDtlId mldDtl id
     * @return 对应模具的状态
     */
    String getStatusByMldId(Integer mldDtlId);
}
