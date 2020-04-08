package cc.mrbird.febs.gen.service;

import cc.mrbird.febs.gen.entity.TbUser;

import cc.mrbird.febs.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *  Service接口
 *
 * @author LHY
 * @date 2020-04-08 14:33:47
 */
public interface ITbUserService extends IService<TbUser> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param tbUser tbUser
     * @return IPage<TbUser>
     */
    IPage<TbUser> findTbUsers(QueryRequest request, TbUser tbUser);

    /**
     * 查询（所有）
     *
     * @param tbUser tbUser
     * @return List<TbUser>
     */
    List<TbUser> findTbUsers(TbUser tbUser);

    /**
     * 新增
     *
     * @param tbUser tbUser
     */
    void createTbUser(TbUser tbUser);

    /**
     * 修改
     *
     * @param tbUser tbUser
     */
    void updateTbUser(TbUser tbUser);

    /**
     * 删除
     *
     * @param tbUser tbUser
     */
    void deleteTbUser(TbUser tbUser);
}
