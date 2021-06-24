package com.lq.mytool.mapper;

import com.lq.mytool.entity.SxTodoInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 待办事项 Mapper 接口
 * </p>
 *
 * @author LQ
 * @since 2021-06-24
 */
public interface SxTodoInfoMapper extends BaseMapper<SxTodoInfo> {

    List<SxTodoInfo> todoInfoList();
}
