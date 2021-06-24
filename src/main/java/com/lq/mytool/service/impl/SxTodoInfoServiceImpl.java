package com.lq.mytool.service.impl;

import com.lq.mytool.entity.SxTodoInfo;
import com.lq.mytool.mapper.SxTodoInfoMapper;
import com.lq.mytool.service.ISxTodoInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 待办事项 服务实现类
 * </p>
 *
 * @author LQ
 * @since 2021-06-24
 */
@Service
public class SxTodoInfoServiceImpl extends ServiceImpl<SxTodoInfoMapper, SxTodoInfo> implements ISxTodoInfoService {

    @Override
    public List<SxTodoInfo> selectList() {
        return this.baseMapper.todoInfoList();
    }
}
