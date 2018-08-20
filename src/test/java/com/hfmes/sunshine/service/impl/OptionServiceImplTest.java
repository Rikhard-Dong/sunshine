package com.hfmes.sunshine.service.impl;

import com.hfmes.sunshine.dto.OptionDTO;
import com.hfmes.sunshine.dto.OptionsDTO;
import com.hfmes.sunshine.dto.Result;
import com.hfmes.sunshine.service.OptionService;
import com.hfmes.sunshine.utils.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 16:21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OptionServiceImplTest {

    @Autowired
    private OptionService optionService;

    @Test
    public void obtainOptions() {
//        List<OptionDTO> result = optionService.obtainOptions("1186200375", 2);
        List<OptionDTO> result = optionService.obtainOptions("1296880245", 2);

        log.debug("{}", result);

        Result result1 = Result.success(result);
        log.debug("result json --> {}", JacksonUtils.toJSon(result1));
    }

    @Test
    public void obtainAllOptions() {

        List<OptionsDTO> options = optionService.obtainAllOptions();
        log.debug("options --> {}", options);

        String result = JacksonUtils.toJSon(options);

        log.warn("{}", result);
    }
}