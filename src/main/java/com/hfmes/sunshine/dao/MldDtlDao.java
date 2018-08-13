package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.MldDtl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 21:03
 */
public interface MldDtlDao {

    /* ****************************************
     * update
     ******************************************/

    /**
     * 更新模具状态
     *
     * @param mldDtlId 模具id
     * @param status   更新后的状态
     * @return 影响行数
     */
    Integer updateStatus(@Param("mldDtlId") Integer mldDtlId,
                         @Param("status") String status);

    /* ****************************************
     * select
     ******************************************/

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
