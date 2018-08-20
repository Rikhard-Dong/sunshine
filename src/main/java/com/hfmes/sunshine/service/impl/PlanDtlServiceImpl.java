package com.hfmes.sunshine.service.impl;

import com.hfmes.sunshine.dao.PlanDtlDao;
import com.hfmes.sunshine.domain.PlanDtl;
import com.hfmes.sunshine.service.PlanDtlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PlanDtlServiceImpl implements PlanDtlService {

    private final PlanDtlDao planDtlDao;

    @Autowired
    public PlanDtlServiceImpl(PlanDtlDao planDtlDao) {
        this.planDtlDao = planDtlDao;
    }


    @Override
    public PlanDtl getPlanDtlById(int planDtlId) {
        return planDtlDao.findById(planDtlId);
    }
}
