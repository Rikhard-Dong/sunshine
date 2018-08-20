package com.hfmes.sunshine.dto;

import com.hfmes.sunshine.dao.SCOptCondDao;
import com.hfmes.sunshine.domain.SCCondition;
import com.hfmes.sunshine.domain.SCMethod;
import com.hfmes.sunshine.domain.SCOptCond;
import com.hfmes.sunshine.domain.SCOption;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 16:13
 */
@Data
@Slf4j
public class OptionsDTO {
    private Integer opId;
    private List<ConditionDto> conditions;
    private List<SCMethod> methods;


    public OptionsDTO(SCOption option, SCOptCondDao scOptCondDao) {
        this.opId = option.getScOptionId();
        conditions = new ArrayList<>();
        for (SCCondition scCondition : option.getScConditions()) {
//            log.debug("option id --> {}, condition id -> {}", option.getScOptionId(), scCondition.getScConditionId());
//            log.debug("scOptCondDao is null --> {}", scOptCondDao == null);

            Boolean value = scOptCondDao.getValueByOptIdAndConditionId(option.getScOptionId(), scCondition.getScConditionId());
            ConditionDto conditionDto = new ConditionDto(scCondition, value);
            SCOptCond scOptCond = scOptCondDao.findByOptionIdAndCondIdAndValue(opId, scCondition.getScConditionId(), value);
            if (scOptCond != null) {
                conditionDto.setNotMatch(scOptCond.getNotMatch());
            }
            conditions.add(conditionDto);
        }
        methods = option.getScMethods();
    }
}
