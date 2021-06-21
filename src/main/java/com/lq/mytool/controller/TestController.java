package com.lq.mytool.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @ApiOperation("测试接口")
    @RequestMapping(value = "/ok" ,method = RequestMethod.GET)
    public String test(){
        log.info("Ceshi占位符：{}","LQ");
        return "success";
    }
}
