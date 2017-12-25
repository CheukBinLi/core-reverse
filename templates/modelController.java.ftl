package ${context.className};

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.midea.jiebao.jframework.core.base.ResponseResult;
import com.midea.jiebao.jframework.core.persistent.mybatisplus.plugins.Page;
import com.midea.jiebao.report.service.RBdEmsBusiOrgService;
import com.midea.jiebao.report.util.ParamUtil;

@Controller
@RequestMapping(value = "/xxx/${context.className?uncap_first}")
public class ${context.className}Controller extends BaseController
{
	@Autowired
	private ${context.className}Service ${context.className?uncap_first}Service;

	/**
     * @param queryParam
		name	组织名称	string	模糊匹配
		pageNumber	页码	number	
		pageSize	每页记录数	number	
     * @return
     */
    @RequestMapping(value = "/pageQuery",method = RequestMethod.POST)
    public Object pageQuery(@RequestBody(required = false) Map<String,Object> queryParam){
        queryParam = null == queryParam ? new HashMap<String, Object>() : queryParam;
        try {
			UserInfo user = SessionUtils.getUser();
            return ResponseResult.createSuccessResult("", ${context.className?uncap_first}Service.pageQuery(user.getTenantId(),getPage(queryParam),queryParam));
        }catch (Exception e){
            return ResponseResult.createFailResult(e.getMessage(),null);
        }
    }
}
