package cc.mrbird.febs.gen.service.impl;

import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.gen.entity.TbVip;
import cc.mrbird.febs.gen.mapper.TbVipMapper;
import cc.mrbird.febs.gen.service.ITbVipService;
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
 * @date 2020-04-09 22:17:10
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TbVipServiceImpl extends ServiceImpl<TbVipMapper, TbVip> implements ITbVipService {

    @Autowired
    private TbVipMapper tbVipMapper;

    @Override
    public IPage<TbVip> findTbVips(QueryRequest request, TbVip tbVip) {
        LambdaQueryWrapper<TbVip> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        Page<TbVip> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<TbVip> findTbVips(TbVip tbVip) {
	    LambdaQueryWrapper<TbVip> queryWrapper = new LambdaQueryWrapper<>();
		// TODO 设置查询条件
		return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public void createTbVip(TbVip tbVip) {
        this.save(tbVip);
    }

    @Override
    @Transactional
    public void updateTbVip(TbVip tbVip) {
        this.saveOrUpdate(tbVip);
    }

    @Override
    @Transactional
    public void deleteTbVip(TbVip tbVip) {
        LambdaQueryWrapper<TbVip> wrapper = new LambdaQueryWrapper<>();
	    // TODO 设置删除条件
	    this.remove(wrapper);
	}
}
