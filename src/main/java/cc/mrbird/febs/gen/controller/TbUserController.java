package cc.mrbird.febs.gen.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.gen.entity.TbUser;
import cc.mrbird.febs.gen.service.ITbUserService;
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
 * @date 2020-04-08 14:33:47
 */
@Slf4j
@Validated
@Controller
public class TbUserController extends BaseController {

    @Autowired
    private ITbUserService tbUserService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "tbUser")
    public String tbUserIndex(){
        return FebsUtil.view("tbUser/tbUser");
    }

    @GetMapping("tbUser")
    @ResponseBody
    @RequiresPermissions("tbUser:list")
    public FebsResponse getAllTbUsers(TbUser tbUser) {
        return new FebsResponse().success().data(tbUserService.findTbUsers(tbUser));
    }

    @GetMapping("tbUser/list")
    @ResponseBody
    @RequiresPermissions("tbUser:list")
    public FebsResponse tbUserList(QueryRequest request, TbUser tbUser) {
        Map<String, Object> dataTable = getDataTable(this.tbUserService.findTbUsers(request, tbUser));
        return new FebsResponse().success().data(dataTable);
    }

    @ControllerEndpoint(operation = "新增TbUser", exceptionMessage = "新增TbUser失败")
    @PostMapping("tbUser")
    @ResponseBody
    @RequiresPermissions("tbUser:add")
    public FebsResponse addTbUser(@Valid TbUser tbUser) {
        this.tbUserService.createTbUser(tbUser);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "删除TbUser", exceptionMessage = "删除TbUser失败")
    @GetMapping("tbUser/delete")
    @ResponseBody
    @RequiresPermissions("tbUser:delete")
    public FebsResponse deleteTbUser(TbUser tbUser) {
        this.tbUserService.deleteTbUser(tbUser);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改TbUser", exceptionMessage = "修改TbUser失败")
    @PostMapping("tbUser/update")
    @ResponseBody
    @RequiresPermissions("tbUser:update")
    public FebsResponse updateTbUser(TbUser tbUser) {
        this.tbUserService.updateTbUser(tbUser);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改TbUser", exceptionMessage = "导出Excel失败")
    @PostMapping("tbUser/excel")
    @ResponseBody
    @RequiresPermissions("tbUser:export")
    public void export(QueryRequest queryRequest, TbUser tbUser, HttpServletResponse response) {
        List<TbUser> tbUsers = this.tbUserService.findTbUsers(queryRequest, tbUser).getRecords();
        ExcelKit.$Export(TbUser.class, response).downXlsx(tbUsers, false);
    }
}
