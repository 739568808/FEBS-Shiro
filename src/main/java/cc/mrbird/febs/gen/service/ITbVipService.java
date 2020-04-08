package cc.mrbird.febs.gen.service;

import cc.mrbird.febs.gen.entity.TbVip;

import cc.mrbird.febs.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *  Service接口
 *
 * @author LHY
 * @date 2020-04-08 14:33:31
 */
public interface ITbVipService extends IService<TbVip> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param tbVip tbVip
     * @return IPage<TbVip>
     */
    IPage<TbVip> findTbVips(QueryRequest request, TbVip tbVip);

    /**
     * 查询（所有）
     *
     * @param tbVip tbVip
     * @return List<TbVip>
     */
    List<TbVip> findTbVips(TbVip tbVip);

    /**
     * 新增
     *
     * @param tbVip tbVip
     */
    void createTbVip(TbVip tbVip);

    /**
     * 修改
     *
     * @param tbVip tbVip
     */
    void updateTbVip(TbVip tbVip);

    /**
     * 删除
     *
     * @param tbVip tbVip
     */
    void deleteTbVip(TbVip tbVip);
}
