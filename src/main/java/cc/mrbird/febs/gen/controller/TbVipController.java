package cc.mrbird.febs.gen.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.gen.entity.TbVip;
import cc.mrbird.febs.gen.service.ITbVipService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 *  Controller
 *
 * @author LHY
 * @date 2020-04-09 22:17:10
 */
@Slf4j
@Validated
@Controller
public class TbVipController extends BaseController {

    @Autowired
    private ITbVipService tbVipService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "tbVip")
    public String tbVipIndex(){
        return FebsUtil.view("tbVip/tbVip");
    }

    @GetMapping("tbVip")
    @ResponseBody
    @RequiresPermissions("tbVip:list")
    public FebsResponse getAllTbVips(TbVip tbVip) {
        return new FebsResponse().success().data(tbVipService.findTbVips(tbVip));
    }

    @GetMapping("tbVip/list")
    @ResponseBody
    @RequiresPermissions("tbVip:list")
    public FebsResponse tbVipList(QueryRequest request, TbVip tbVip) {
        Map<String, Object> dataTable = getDataTable(this.tbVipService.findTbVips(request, tbVip));
        return new FebsResponse().success().data(dataTable);
    }

    @ControllerEndpoint(operation = "新增TbVip", exceptionMessage = "新增TbVip失败")
    @PostMapping("tbVip")
    @ResponseBody
    @RequiresPermissions("tbVip:add")
    public FebsResponse addTbVip(@Valid TbVip tbVip) {
        this.tbVipService.createTbVip(tbVip);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "删除TbVip", exceptionMessage = "删除TbVip失败")
    @GetMapping("tbVip/delete")
    @ResponseBody
    @RequiresPermissions("tbVip:delete")
    public FebsResponse deleteTbVip(TbVip tbVip) {
        this.tbVipService.deleteTbVip(tbVip);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改TbVip", exceptionMessage = "修改TbVip失败")
    @PostMapping("tbVip/update")
    @ResponseBody
    @RequiresPermissions("tbVip:update")
    public FebsResponse updateTbVip(TbVip tbVip) {
        this.tbVipService.updateTbVip(tbVip);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改TbVip", exceptionMessage = "导出Excel失败")
    @PostMapping("tbVip/excel")
    @ResponseBody
    @RequiresPermissions("tbVip:export")
    public void export(QueryRequest queryRequest, TbVip tbVip, HttpServletResponse response) {
        List<TbVip> tbVips = this.tbVipService.findTbVips(queryRequest, tbVip).getRecords();
        ExcelKit.$Export(TbVip.class, response).downXlsx(tbVips, false);
    }
}
