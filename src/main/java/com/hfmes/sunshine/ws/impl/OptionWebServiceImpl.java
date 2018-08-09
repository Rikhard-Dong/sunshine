package com.hfmes.sunshine.ws.impl;

import com.hfmes.sunshine.dto.OptionsDTO;
import com.hfmes.sunshine.dto.Result;
import com.hfmes.sunshine.service.OptionService;
import com.hfmes.sunshine.utils.JacksonUtils;
import com.hfmes.sunshine.ws.OptionWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import java.util.List;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 14:37
 */
@Component
@WebService
public class OptionWebServiceImpl implements OptionWebService {

    @Autowired
    private OptionService optionService;

    @Override
    public String obtainOp(String cardNo, Integer deviceId) {
        List<OptionsDTO> result = optionService.obtainOptions(cardNo, deviceId);
        return JacksonUtils.toJSon(Result.success(result));
    }
}
