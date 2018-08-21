package com.hfmes.sunshine.service;

import com.hfmes.sunshine.domain.DicItem;

import java.util.List;
import java.util.Map;

public interface DicItemService {
    List<Map<String,String>> getStausTitleByCode();
}
