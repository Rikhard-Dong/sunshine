package com.hfmes.sunshine.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/17 22:35
 */
@Controller
@Slf4j
public class IndexController {

    @GetMapping("/")
    public ModelAndView index() {
        log.warn("访问首页");
        log.error("访问首页");
        return new ModelAndView("index");
    }
}
