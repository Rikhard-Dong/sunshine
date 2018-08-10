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


    @Override
    public boolean updateLocalToServerCount(String devcId, String count) {

        return false;
    }
}
