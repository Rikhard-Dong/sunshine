package com.hfmes.sunshine.service.impl;

import com.hfmes.sunshine.dao.DicItemDao;
import com.hfmes.sunshine.domain.DicItem;
import com.hfmes.sunshine.service.DicItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DicItemServiceImpl implements DicItemService {

    @Autowired
    private DicItemDao dicItemDao;

    @Override
    public List<Map<String,String>> getStausTitleByCode() {
        List<Map<String,String>>list=dicItemDao.findStausByCode();
        return list;
    }
}
