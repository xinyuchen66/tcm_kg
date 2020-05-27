package com.tcm.web.controller.qaos;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcm.dal.home.dao.RedisDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.tcm.common.qaos.QAConst;
import com.tcm.core.qaos.service.QAAskService;
import com.tcm.core.qaos.service.QAKConclusionService;
import com.tcm.core.qaos.service.QATagService;
import com.tcm.dal.user.po.UserSessionPo;
import com.tcm.module.properties.KeysConst;
import com.tcm.module.properties.PropertiesUtil;
import com.tcm.module.request.RequestUtil;

@Controller
@RequestMapping(value = QAConst.QA_URL_BASE)
public class QAController {
	@Resource
	private QATagService qaTagService;

	@Resource
	private QAAskService qaAskService;

	@Resource
	private QAKConclusionService qakConclusionService;

	@Resource
	private PropertiesUtil propertiesUtil;


	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> test(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", qaTagService.queryTagsRelation());
		return map;
	}

	/**
	 * 知识问答主页
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String qaosHome(HttpServletRequest request,
			HttpServletResponse response) {
		return "/qaos/home";
	}

	@RequestMapping(value = "/trend", method = RequestMethod.GET)
	public String trendAnalysisPage(HttpServletRequest request,
			HttpServletResponse response) {
		return "/qaos/analysis/trend";
	}

	/**
	 * 知识图谱页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @param pageNumber
	 * @param query
	 * @return
	 */
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String profilePage(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "query", defaultValue = "") String query) {
		qaTagService.bindTagsRelationsToPage(modelMap, pageNumber, query);
		return "/qaos/analysis/profile";
	}


	/**
	 * 知识图谱
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @param pageNumber
	 * @param query
	 * @return
	 */
	@RequestMapping(value = "/profilenet", method = RequestMethod.GET)
	public String profileNetPage(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "query", defaultValue = "") String query) {
		qaTagService.bindTagsRelationsToPage(modelMap, pageNumber, query);
		return "/qaos/analysis/profilenet";
	}

	/**
	 * 根据模糊的用户名,查找相关用户,并绑定到页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String getUsersPage(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			@RequestParam(value = "query") String userName) {
		//处理get请求url中文乱码问题
		try{
			String decoderUserName = new String(userName.getBytes("iso8859-1"), "utf-8");
			System.out.println(decoderUserName);
			modelMap.put(QAConst.USERS_LIST,
					qaAskService.queryUsersByFuzzyName(decoderUserName));
		}catch (Exception e){
			System.out.println("decoder error");
		}
//		modelMap.put(QAConst.USERS_LIST,
//				qaAskService.queryUsersByFuzzyName(userName));
		return "qaos/topics/user_list";
	}

	/**
	 * 知识归纳首页
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/kconclusions", method = RequestMethod.GET)
	public String getConclusionsHomePage(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		return "qaos/analysis/knowledge_conclusion";
	}

	/**
	 * 知识归纳查询
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/kcpage", method = RequestMethod.GET)
	public String getConclusionsPage(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			@RequestParam(value = "query", defaultValue = "") String name) {

		return "qaos/analysis/kc_page";
	}

	/**
	 * 根据姓名,精确搜索KConclusion
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/kcsearch", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getConclusions(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			@RequestParam(value = "query", defaultValue = "") String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		//处理get请求url中文乱码问题
		try{
			String decoderName = new String(name.getBytes("iso8859-1"), "utf-8");
			System.out.println(decoderName);
			map.put("object", qakConclusionService.queryKConclusion(name));
		}catch (Exception e){
			System.out.println("decoder error");
		}
//		map.put("object", qakConclusionService.queryKConclusion(name));
		return map;
	}

	/**
	 * 模糊查询tags列表
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @param tagName
	 * @return
	 */
	@RequestMapping(value = "/tags", method = RequestMethod.GET)
	public String getTagsPage(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			@RequestParam(value = "query", defaultValue = "") String tagName) {
		//处理get请求url中文乱码问题
		try{
			String decoderTagName = new String(tagName.getBytes("iso8859-1"), "utf-8");
			System.out.println(decoderTagName);
			modelMap.put(QAConst.TAGS_LIST,
					qaTagService.queryTagsByFuzzyName(decoderTagName));
		}catch (Exception e){
			System.out.println("decoder error");
		}
//		modelMap.put(QAConst.TAGS_LIST,
//				qaTagService.queryTagsByFuzzyName(tagName));
		return "qaos/analysis/tags";
	}

	/**
	 * 暴露给通用搜索的接口; 根据query,返回相关的问题数目和回答数目
	 * 
	 * @param request
	 * @param response
	 * @param query
	 * @return
	 */
	@RequestMapping(value = "/relatedstat", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getRelatedStat(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "query", defaultValue = "") String query) {
		return qaAskService.statNumbers(query);
	}

	/**
	 * 暴露给通用搜索的接口; 根据query,返回相关的问题
	 * 
	 * @param request
	 * @param response
	 * @param query
	 * @return
	 */
	@RequestMapping(value = "/relatedquestions", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> relatedQuestions(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "query") String query,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("questionsContent", qaAskService.queryTopics(query, pageNumber));
		return map;
	}

	@RequestMapping(value = "/useranalysis", method = RequestMethod.GET)
	public String userAnalysis(HttpServletRequest request,
			HttpServletResponse response) {
		UserSessionPo user = RequestUtil.getUserSession(request);
		//String url = propertiesUtil.getValue(KeysConst.WEB_SERVER_TCM)
		//		+ "/user/" + user.getUserId() + "/";
		if (null == user) {
			return "redirect:" + "http://zcy.ckcest.cn/usercenter/cas/authen?backtrack="+request.getRequestURL();
		}
		String url = "http://zcy.ckcest.cn/usercenter/tcmUser/"+user.getUserId()+"/";
		return "redirect:" + url;
	}

}
