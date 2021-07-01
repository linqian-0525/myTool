package com.lq.mytool;

import com.alibaba.fastjson.JSON;
import com.lq.mytool.utils.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class HttpClientTest {
    @Test
    public void testHttpClient(){
        String param = "parentOrgTypeIdList";
        String value = "20,25";

        Map<String, Object> params = new HashMap<>();
        // 机构大类id
        // 银行: 20
        // 非银行机构 25
        // 管理机构 10
        params.put("parentOrgTypeIdList", "20,25");
        params.put("rootType", "2");
        Map<String, String> responseMap;
        try {
            // 管理后台-获取所有银行机构列表接口
            responseMap = HttpClientUtil.post("http://jrzhfw.zjjf.org.cn/api/openinterface" + "/sysOrganization/api/listOrgsByOrgType", params, null);

            //log.info("####获取所有银行机构列表接口.传参:{},返回",UserContextHolder.getToken(), responseMap);
        } catch (Exception e) {

        }
    }
}
