package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.SCOption;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 22:15
 */
public interface SCOptionDao {

    SCOption findBySCOptionId(Integer id);

    List<SCOption> findByRoleId(Integer roleId);

    /**
     * 废弃
     *
     * @param cardNo
     * @param deviceStatus
     * @param mldStatus
     * @param taskStatus
     * @return
     */
    List<SCOption> findByCardNoAndStatus(@Param("cardNo") String cardNo,
                                         @Param("deviceStatus") String deviceStatus,
                                         @Param("mldStatus") String mldStatus,
                                         @Param("taskStatus") String taskStatus);

    Set<SCOption> findByCardNo(@Param("cardNo") String cardNo);

    Set<SCOption> findBySDStatus(@Param("deviceStatus") String deviceStatus);

    Set<SCOption> findBySMStatus(@Param("mldStatus") String mldStatus);

    Set<SCOption> findBySTStatus(@Param("taskStatus") String taskStatus);
}
