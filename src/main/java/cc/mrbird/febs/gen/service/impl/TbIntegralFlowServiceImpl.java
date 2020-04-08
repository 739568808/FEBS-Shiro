package cc.mrbird.febs.gen.service.impl;

import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.gen.entity.TbIntegralFlow;
import cc.mrbird.febs.gen.mapper.TbIntegralFlowMapper;
import cc.mrbird.febs.gen.service.ITbIntegralFlowService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 *  Service实现
 *
 * @author LHY
 * @date 2020-04-08 16:48:24
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TbIntegralFlowServiceImpl extends ServiceImpl<TbIntegralFlowMapper, TbIntegralFlow> implements ITbIntegralFlowService {

    @Autowired
    private TbIntegralFlowMapper tbIntegralFlowMapper;

    @Override
    public IPage<TbIntegralFlow> findTbIntegralFlows(QueryRequest request, TbIntegralFlow tbIntegralFlow) {
        LambdaQueryWrapper<TbIntegralFlow> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        Page<TbIntegralFlow> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<TbIntegralFlow> findTbIntegralFlows(TbIntegralFlow tbIntegralFlow) {
	    LambdaQueryWrapper<TbIntegralFlow> queryWrapper = new LambdaQueryWrapper<>();
		// TODO 设置查询条件
		return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public void createTbIntegralFlow(TbIntegralFlow tbIntegralFlow) {
        this.save(tbIntegralFlow);
    }

    @Override
    @Transactional
    public void updateTbIntegralFlow(TbIntegralFlow tbIntegralFlow) {
        this.saveOrUpdate(tbIntegralFlow);
    }

    @Override
    @Transactional
    public void deleteTbIntegralFlow(TbIntegralFlow tbIntegralFlow) {
        LambdaQueryWrapper<TbIntegralFlow> wrapper = new LambdaQueryWrapper<>();
	    // TODO 设置删除条件
	    this.remove(wrapper);
	}
}
