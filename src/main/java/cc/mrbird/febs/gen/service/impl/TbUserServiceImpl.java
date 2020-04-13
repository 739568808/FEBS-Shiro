package cc.mrbird.febs.gen.service.impl;

import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.gen.entity.TbIntegralFlow;
import cc.mrbird.febs.gen.entity.TbUser;
import cc.mrbird.febs.gen.mapper.TbUserMapper;
import cc.mrbird.febs.gen.service.ITbUserService;
import org.apache.commons.lang3.StringUtils;
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
 * @date 2020-04-09 22:17:08
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser> implements ITbUserService {

    @Autowired
    private TbUserMapper tbUserMapper;

    @Override
    public IPage<TbUser> findTbUsers(QueryRequest request, TbUser tbUser) {
        LambdaQueryWrapper<TbUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.setEntity(tbUser);
        if (null!=tbUser.getPhone()){
            queryWrapper.like(TbUser::getPhone,tbUser.getPhone());
            tbUser.setPhone(null);
        }
        if (!StringUtils.isEmpty(tbUser.getRealName())){
            queryWrapper.like(TbUser::getRealName,tbUser.getRealName());
            tbUser.setRealName(null);
        }
        // TODO 设置查询条件
        Page<TbUser> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<TbUser> findTbUsers(TbUser tbUser) {
	    LambdaQueryWrapper<TbUser> queryWrapper = new LambdaQueryWrapper<>();
		// TODO 设置查询条件
        queryWrapper.setEntity(tbUser);
		return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public void createTbUser(TbUser tbUser) {
        this.save(tbUser);
    }

    @Override
    @Transactional
    public void updateTbUser(TbUser tbUser) {
        this.saveOrUpdate(tbUser);
    }

    @Override
    @Transactional
    public void deleteTbUser(TbUser tbUser) {
        LambdaQueryWrapper<TbUser> wrapper = new LambdaQueryWrapper<>();
	    // TODO 设置删除条件
	    this.remove(wrapper);
	}
}
