package com.hfmes.sunshine.service.impl;

import com.hfmes.sunshine.domain.Devc;
import com.hfmes.sunshine.service.CountNumService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class CountNumServiceImpl implements CountNumService{

    @Autowired
    private Map<Integer, Devc> devcs;

    @Autowired
    Map<Integer, Integer> countNums;

    @Override
    public boolean updateLocalToServerCount(String devcId, String count) {
        Devc devc=devcs.get(Integer.parseInt(devcId));

        if(devc==null){
            return false;
        }else{
            int num=countNums.get(Integer.parseInt(devcId));
            num=num+Integer.parseInt(count);
            if(devc.getTask().getStatus().equals("ST10")){
                devc.getTask().setProcNum(devc.getTask().getProcNum()+Integer.parseInt(count));
            }else{
                devc.getTask().setTestNum(devc.getTask().getTestNum()+Integer.parseInt(count));
            }
            try {
                devcs.put(devc.getDeviceId(),devc);
                countNums.put(devc.getDeviceId(),num);
                return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }

        }
    }
}
