package com.lq.mytool.controller;

import com.lq.mytool.common.BaseResponse;
import com.lq.mytool.entity.SxTodoInfo;
import com.lq.mytool.service.ISxTodoInfoService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    @Autowired
    private ISxTodoInfoService iSxIndustryService;

    @ApiOperation("测试接口")
    @RequestMapping(value = "/ok" ,method = RequestMethod.GET)
    public BaseResponse<List<SxTodoInfo>> test(){
        log.info("Ceshi占位符：{}","LQ");
        return BaseResponse.success(iSxIndustryService.selectList());
    }
}
