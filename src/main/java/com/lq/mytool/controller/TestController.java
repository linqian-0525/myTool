package com.lq.mytool.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    /*@Autowired
    private ISxIndustryService iSxIndustryService;

    @ApiOperation("测试接口")
    @RequestMapping(value = "/ok" ,method = RequestMethod.GET)
    public SxIndustry test(){
        log.info("Ceshi占位符：{}","LQ");
        return iSxIndustryService.getById(1);
    }*/
}
