package com.lq.mytool.service;

import com.lq.mytool.entity.SxTodoInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 待办事项 服务类
 * </p>
 *
 * @author LQ
 * @since 2021-06-24
 */
public interface ISxTodoInfoService extends IService<SxTodoInfo> {

    List<SxTodoInfo> selectList();
}
