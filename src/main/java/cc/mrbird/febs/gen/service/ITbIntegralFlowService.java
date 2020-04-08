package cc.mrbird.febs.gen.service;

import cc.mrbird.febs.gen.entity.TbIntegralFlow;

import cc.mrbird.febs.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *  Service接口
 *
 * @author LHY
 * @date 2020-04-08 14:33:57
 */
public interface ITbIntegralFlowService extends IService<TbIntegralFlow> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param tbIntegralFlow tbIntegralFlow
     * @return IPage<TbIntegralFlow>
     */
    IPage<TbIntegralFlow> findTbIntegralFlows(QueryRequest request, TbIntegralFlow tbIntegralFlow);

    /**
     * 查询（所有）
     *
     * @param tbIntegralFlow tbIntegralFlow
     * @return List<TbIntegralFlow>
     */
    List<TbIntegralFlow> findTbIntegralFlows(TbIntegralFlow tbIntegralFlow);

    /**
     * 新增
     *
     * @param tbIntegralFlow tbIntegralFlow
     */
    void createTbIntegralFlow(TbIntegralFlow tbIntegralFlow);

    /**
     * 修改
     *
     * @param tbIntegralFlow tbIntegralFlow
     */
    void updateTbIntegralFlow(TbIntegralFlow tbIntegralFlow);

    /**
     * 删除
     *
     * @param tbIntegralFlow tbIntegralFlow
     */
    void deleteTbIntegralFlow(TbIntegralFlow tbIntegralFlow);
}
