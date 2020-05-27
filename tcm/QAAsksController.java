package com.tcm.web.controller.qaos;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tcm.common.qaos.QAConst;
import com.tcm.core.qaos.service.QAAskService;
import com.tcm.core.search.service.QuestionService;
import com.tcm.dal.home.dao.RedisDao;
import com.tcm.dal.qaos.po.ReplyPo;
import com.tcm.dal.qaos.po.TopicPo;
import com.tcm.module.properties.KeysConst;
import com.tcm.module.properties.PropertiesUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = QAConst.QA_URL_BASE + QAConst.QA_TOPICSS_URL_BASE
		+ "/ask")
public class QAAsksController {
	@Resource
	private QAAskService qaAskService;
	@Resource
	private PropertiesUtil propertiesUtil;
	@Resource
	private QuestionService questionService;
	@Resource
	private RedisDao redisDao;

	/**
	 * 2019新版--高级问题搜索页面
	 *
	 * full-url:/qaos/topics/ask/qasearch
	 *
	 * AJAX:否
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/qasearchsenior", method = RequestMethod.GET)
	public String getSeniorSearchPage(HttpServletRequest request,
							  HttpServletResponse response) {
		return "/qaos/qa_search_senior";
	}

	/**
	 * 2019新版--自动问答页面
	 *
	 * full-url:/qaos/topics//askautoanswer
	 *
	 * AJAX:否
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/autoanswer", method = RequestMethod.GET)
	public String getAutoAnswerPage(HttpServletRequest request,
									  HttpServletResponse response) {
		return "/qaos/qa_search_answer";
	}

	/**
	 * 2019新版--问题搜索页面
	 *
	 * full-url:/qaos/topics/qasearch
	 *
	 * AJAX:否
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/qasearch", method = RequestMethod.GET)
	public String getSearchPage(HttpServletRequest request,
								HttpServletResponse response) {
		return "/qaos/qa_search";
	}

	/**
	 * 2019新版--问题详细页面
	 *
	 * full-url:/qaos/topics/qadetail
	 *
	 * AJAX:否
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/qadetail", method = RequestMethod.GET)
	public String getSearchDetailPage(HttpServletRequest request,
								HttpServletResponse response) {
		return "/qaos/qa_search_detail";
	}

	/**
	 * 2019新版--根据问题id查询问题详细信息
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/qadetailinfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getQaDetail(HttpServletRequest request,
										  HttpServletResponse response, @RequestBody Map<String, Object> map) throws Exception {
		return questionService.getQuestionDetail(map);
	}

	/**
	 * 2019新版--根据问题id查询问题详细信息
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/qareply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getQaDetailReply(HttpServletRequest request,
										   HttpServletResponse response, @RequestBody Map<String, Object> map) throws Exception {
		return questionService.getQuestionDetailReply(map);
	}

	/**
	 * 2019 添加新的问题回复
	 *
	 * @param request
	 * @param response
	 * @param replyPo
	 * @return 回复的Id
	 */
	@RequestMapping(value = "/{questionId}/addreplys", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addQuestionReply(HttpServletRequest request,
												HttpServletResponse response, @RequestBody ReplyPo replyPo,@PathVariable(value = "questionId") int questionId) throws Exception {
//		logger.info(replyPo);
//		int qaId = Integer.parseInt((String) map.get(QAID));
		long replyId = questionService.addReply(request, replyPo, questionId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("replyId", replyId);
//		System.out.println(replyId);
		return map;
	}

	/**
	 * 2019 点赞solr中的某一回答
	 * @return
	 */
	@RequestMapping(value = "/support", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> supportReply(HttpServletRequest request,
											HttpServletResponse response, @RequestBody Map<String, Object> map) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("flag", questionService.supportReply(map));
		return m;
	}

	@RequestMapping(value = "/{askId}/supportuser", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, Object> supportUserReply(HttpServletRequest request,
											HttpServletResponse response,
											@PathVariable(value = "askId") long topicId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", questionService.supportUserReply(topicId));
		return map;
	}

	@RequestMapping(value = "/{askId}/opposeuser", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, Object> opposeUserReply(HttpServletRequest request,
												HttpServletResponse response,
												@PathVariable(value = "askId") long topicId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", questionService.opposeUserReply(topicId));
		return map;
	}


	/**
	 * 2019新版--根据条件返回问题列表  用于问题搜索
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/qalist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getQaList(HttpServletRequest request,
										  HttpServletResponse response, @RequestBody Map<String, Object> map) throws Exception {
		return questionService.getQuestionList(map);
	}

	/**
	 * 2019新版--问题提问页面
	 *
	 * full-url:/qaos/topics/qaask
	 *
	 * AJAX:否
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/qaask", method = RequestMethod.GET)
	public String getAskPage(HttpServletRequest request,
							  HttpServletResponse response) {
		return "/qaos/topics/topics_list";
	}

	/**
	 * 2019新版--查找同时包含firstTagName和secondTagName的topics,并绑定到页面
	 */
	@RequestMapping(value="/qatags", method = RequestMethod.GET)
	public String getTagsPages(HttpServletRequest request, HttpServletResponse response,
							   ModelMap modelMap,
							   @RequestParam(value = "tag1") String firstTagName,
							   @RequestParam(value = "tag2") String secondTagName){
		//处理get请求url中文乱码问题
		try{
			String decoderFirstTagName = new String(firstTagName.getBytes("iso8859-1"), "utf-8");
			String decoderSecondTagName = new String(secondTagName.getBytes("iso8859-1"), "utf-8");
			modelMap.put("firstTagName", decoderFirstTagName);
			modelMap.put("secondTagName", decoderSecondTagName);
		}catch (Exception e){
			System.out.println("decoder error");
		}
		return "/qaos/qa_search_senior";
	}

	/**
	 * 2019新版--添加一个新提问,返回主题的ID
	 *
	 * @param request
	 * @param response

	 *            { "topicTitle":"xxxx", "topicBody":"xxxx" }
	 * @return {"qa_id":1, "qa_q":str}
	 */
	@RequestMapping(value = "/qaadd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> qaNewTopic(HttpServletRequest request,
										HttpServletResponse response, @RequestBody TopicPo topicPo) {
		long topicId = questionService.addNewQaTopic(request, topicPo);
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("qa_id", topicId);
		map2.put("qa_q", topicPo.getTopicTitle());
		return map2;
	}

	@RequestMapping(value = "qaidlistsearch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> qaIdListSearch(HttpServletRequest request, HttpServletResponse response, @RequestBody  Map<String, Object>map) throws Exception {
		return questionService.getQuestionByIdList(map);
	}

	/**
	 * 以下三个函数用于转移在10.15.82.71中对于知识图谱查询
	 */
	@RequestMapping(value = "/getKGmain", method = RequestMethod.GET)
	public void getKnowledgeGraphNode(HttpServletRequest request,
							   HttpServletResponse response,
							   @RequestParam(value = "entity") String entity) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String quickRes = redisDao.get(entity);
		if(quickRes!=null) {
			response.getWriter().print(quickRes);
			return;
		}
		PrintWriter wirte = null;
		StringBuffer buffer = new StringBuffer();
		String res="";
		JsonObject json = new JsonObject();
		try {
			wirte = response.getWriter();

			URL url = new URL("http://10.15.82.71:5787/main?entity="+ URLEncoder.encode(entity, "utf-8"));
			HttpURLConnection urlCon= (HttpURLConnection)url.openConnection();
			if(200==urlCon.getResponseCode()){
				InputStream is = urlCon.getInputStream();
				InputStreamReader isr = new InputStreamReader(is,"utf-8");
				BufferedReader br = new BufferedReader(isr);
				String str = null;
				while((str = br.readLine())!=null){
					buffer.append(str);
				}
				br.close();
				isr.close();
				is.close();
				res = buffer.toString();
				JsonParser parse =new JsonParser();
				json = (JsonObject) parse.parse(res);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			wirte.print(json);
			wirte.flush();
			wirte.close();
		}
		if(res.length()>100)redisDao.setWithTime(entity, res, 7200);
		response.getWriter().print(json);
	}

	@RequestMapping(value = "/getKGdisease", method = RequestMethod.GET)
	public void getKnowledgeGraphNode2(HttpServletRequest request,
									  HttpServletResponse response,
									  @RequestParam(value = "entity") String entity) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String quickRes = redisDao.get("d"+entity);
		if(quickRes!=null) {
			response.getWriter().print(quickRes);
			return;
		}
		PrintWriter wirte = null;
		StringBuffer buffer = new StringBuffer();
		String res="";
		JsonObject json = new JsonObject();
		try {
			wirte = response.getWriter();

			URL url = new URL("http://10.15.82.71:5787/disease?entity="+ URLEncoder.encode(entity, "utf-8"));
			HttpURLConnection urlCon= (HttpURLConnection)url.openConnection();
			if(200==urlCon.getResponseCode()){
				InputStream is = urlCon.getInputStream();
				InputStreamReader isr = new InputStreamReader(is,"utf-8");
				BufferedReader br = new BufferedReader(isr);
				String str = null;
				while((str = br.readLine())!=null){
					buffer.append(str);
				}
				br.close();
				isr.close();
				is.close();
				res = buffer.toString();
				JsonParser parse =new JsonParser();
				json = (JsonObject) parse.parse(res);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			wirte.print(json);
			wirte.flush();
			wirte.close();
		}
		if(res.length()>100)redisDao.setWithTime("d"+entity, res, 7200);
		response.getWriter().print(json);
	}

	@RequestMapping(value = "/getKGlist", method = RequestMethod.GET)
	public void getKnowledgeGraphNode3(HttpServletRequest request,
									  HttpServletResponse response,
									  @RequestParam(value = "id") String id) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter wirte = null;
		StringBuffer buffer = new StringBuffer();
		String res="";
		JsonObject json = new JsonObject();
		try {
			wirte = response.getWriter();

			URL url = new URL("http://10.15.82.71:5787/QAlist?id="+ id);
			HttpURLConnection urlCon= (HttpURLConnection)url.openConnection();
			if(200==urlCon.getResponseCode()){
				InputStream is = urlCon.getInputStream();
				InputStreamReader isr = new InputStreamReader(is,"utf-8");
				BufferedReader br = new BufferedReader(isr);
				String str = null;
				while((str = br.readLine())!=null){
					buffer.append(str);
				}
				br.close();
				isr.close();
				is.close();
				res = buffer.toString();
				JsonParser parse =new JsonParser();
				json = (JsonObject) parse.parse(res);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			wirte.print(json);
			wirte.flush();
			wirte.close();
		}
		response.getWriter().print(json);
	}
	/**
	 * 问题空间，查看提问的列表;分页请求提问列表
	 * 
	 * full-url:/qaos/topics/ask?page={pageNumber}
	 * 
	 * AJAX:否
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String getTopicsListPage(HttpServletRequest request,
			ModelMap modelMap,
			@RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(value = "query", defaultValue = "") String query,
			@RequestParam(value = "sortBy", defaultValue="-1") int sortBy,
			@RequestParam(value = "disCate", defaultValue="-1") int disCate) {
		try{
			String decoderQuery = new String(query.getBytes("iso8859-1"), "utf-8");
			System.out.println(decoderQuery);
			qaAskService.addAsksList(modelMap, pageNumber, decoderQuery,sortBy,disCate, "");
		}catch (Exception e){
			System.out.println("decoder error");
		}
//		qaAskService.addAsksList(modelMap, pageNumber, query,sortBy,disCate, "");
		return "/qaos/topics/topics_list";
	}

	/**
	 * 问题空间，查看提问的列表;分页请求提问列表
	 * 
	 * full-url:/qaos/topics/ask?page={pageNumber}
	 * 
	 * AJAX:否
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/metas", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getTopicsList(
			HttpServletRequest request,
			HttpServletResponse response,
			ModelMap modelMap,
			@RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(value = "query", defaultValue = "") String query) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", qaAskService.getAsks(pageNumber, query));
		return map;
	}

	@RequestMapping(value = "/questions", method = RequestMethod.GET)
	public void getTopicsList(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(value = "query", defaultValue = "") String query,
			@RequestParam String callback) throws IOException {
		PrintWriter out = response.getWriter();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("questions", qaAskService.getAsks(pageNumber, query));
		JSONObject resultJSON = JSONObject.fromObject(map); //根据需要拼装json
		out.println(callback + "(" + resultJSON.toString(1, 1) + ")");// 返回jsonp格式数据
		out.flush();
		out.close();
	}

	/**
	 * 根据提问的ID,删除该提问
	 * 
	 * full-url:/qaos/topics/ask/{askId}/delete
	 * 
	 * AJAX:是
	 * 
	 * @param request
	 * @param response
	 * @param topicId
	 * @return
	 */
	@RequestMapping(value = "/{askId}/delete", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Object> deleteTopic(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "askId") long topicId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", qaAskService.deleteTopic(topicId));
		return map;
	}

	/**
	 * 对某个主题点赞
	 * 
	 * @param request
	 * @param response
	 * @param topicId
	 * @return
	 */
	@RequestMapping(value = "/{askId}/support", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, Object> supportTopic(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "askId") long topicId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", qaAskService.supportTopic(topicId));
		return map;
	}

	/**
	 * 对某个主题拍砖
	 * 
	 * @param request
	 * @param response
	 * @param topicId
	 * @return
	 */
	@RequestMapping(value = "/{askId}/oppose", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, Object> opposeTopic(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "askId") long topicId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", qaAskService.opposeTopic(topicId));
		return map;
	}

	/**
	 * 把主题设置成不可编辑
	 * 
	 * @param request
	 * @param response
	 * @param topicId
	 * @return
	 */
	@RequestMapping(value = "/{askId}/finalize", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, Object> finalizeTopic(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "askId") long topicId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", qaAskService.setNotEditable(topicId));
		return map;
	}

	/**
	 * 添加一个新提问,返回主题的ID
	 * 
	 * @param request
	 * @param response

	 *            { "topicTitle":"xxxx", "topicBody":"xxxx" }
	 * @return {"flag":true,"topicId":1}
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> newTopic(HttpServletRequest request,
			HttpServletResponse response, @RequestBody TopicPo topicPo) {
		long topicId = qaAskService.addNewTopic(request, topicPo);
		boolean flag = (topicId > 0) ? true : false;
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("flag", flag);
		map2.put("topicId", topicId);
		return map2;
	}

	/**
	 * 查看某个提问的详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/{askId}", method = RequestMethod.GET)
	public String getTopicInfoPage(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			@PathVariable(value = "askId") int askId) {
		/* 添加{userId}的详细内容 */
		qaAskService.addTopic(askId, modelMap);
		System.out.println(propertiesUtil
				.getValue(KeysConst.SMART_QA));
		modelMap.put("SmartQA", propertiesUtil
				.getValue(KeysConst.SMART_QA));
		return "/qaos/topics/ask_info";
	}

	/**
	 * 提问页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addTopicPage(HttpServletRequest request,
			HttpServletResponse response) {
		return "/qaos/topics/ask";
	}

	/**
	 * 分页请求某个主题的回复
	 * 
	 * @param request
	 * @param response
	 * @param topicId
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping(value = "/{askId}/replys/{pageNumber}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getReplysList(HttpServletRequest request,
			HttpServletResponse response, @PathVariable("askId") int topicId,
			@PathVariable("pageNumber") int pageNumber) {
		return qaAskService.getReplys(topicId, pageNumber);
	}

	/**
	 * 添加新的回复
	 * 
	 * @param request
	 * @param response
	 * @param replyPo
	 * @param topicId
	 * @return 回复的Id
	 */
	@RequestMapping(value = "/{askId}/replys/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addReply(HttpServletRequest request,
			HttpServletResponse response, @RequestBody ReplyPo replyPo,
			@PathVariable("askId") int topicId) {
		long replyId = qaAskService.addNewReply(request, replyPo, topicId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("replyId", replyId);
		return map;
	}

	/**
	 * 根据tagName,查询相关的topic列表,并绑定到页面中
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @param pageNumber
	 * @param tagName
	 * @return
	 */
	@RequestMapping(value = "/t_{tagName}", method = RequestMethod.GET)
	public String getTopicsPageByTagName(
			HttpServletRequest request,
			HttpServletResponse response,
			ModelMap modelMap,
			@RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(value = "sortBy", defaultValue="-1") int sortBy,
			@RequestParam(value = "disCate", defaultValue="-1") int disCate,
			@PathVariable("tagName") String tagName) {
		try{
			String decoderTagName = new String(tagName.getBytes("iso8859-1"), "utf-8");
			System.out.println(decoderTagName);
			qaAskService.addAsksList(modelMap, pageNumber, "",sortBy,disCate,decoderTagName);
		}catch (Exception e){
			System.out.println("decoder error");
		}
//		qaAskService.addAsksList(modelMap, pageNumber, "",sortBy,disCate,tagName);
		return "/qaos/topics/topics_list";
	}

	/**
	 * 查找同时包含firstTagName和secondTagName的topics,并绑定到页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @param pageNumber
	 * @param tagName
	 * @return
	 */
	@RequestMapping(value = "/tagrelation", method = RequestMethod.GET)
	public String getTopicsPageByTagRelation(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "tag1") String firstTagName,
			@RequestParam(value = "tag2") String secondTagName) {
		//处理get请求url中文乱码问题
		try{
			String decoderFirstTagName = new String(firstTagName.getBytes("iso8859-1"), "utf-8");
			String decoderSecondTagName = new String(secondTagName.getBytes("iso8859-1"), "utf-8");
			System.out.println(decoderFirstTagName);
			System.out.println(decoderSecondTagName);
			qaAskService.bindTopicsToPage(modelMap, pageNumber, decoderFirstTagName,
					decoderSecondTagName);
		}catch (Exception e){
			System.out.println("decoder error");
		}
//		qaAskService.bindTopicsToPage(modelMap, pageNumber, firstTagName,
//				secondTagName);
		return "/qaos/topics/topics_list";
	}

	/**
	 * 获得提问的总数
	 * 
	 * @param request
	 * @param response
	 * @return map {"sum":1000}
	 */
	@RequestMapping(value = "/sum", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getReplysSum(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sum", qaAskService.getTopicsCnt());
		return map;
	}
	
	@RequestMapping(value = "/getClasses", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getClasses() {
		Map<String, Object> map =  qaAskService.getClasses();
		return map;
	}

	/**
	 * 模糊查询topic的title
	 * 
	 * @param request
	 * @param response
	 * @param tagName
	 * @return
	 */
	@RequestMapping(value = "/similartitles", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> selectTagsByName(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "q") String title) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", qaAskService.querySimilarTitles(title));
		return map;
	}

	/**
	 * 查询用户奖励记录
	 * 
	 * @param request
	 * @param response
	 * @return { "list":[ { awardPo }, { }, ... ] }
	 */
	@RequestMapping(value = "/awardslog", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> awardsLog(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", qaAskService.queryAqards(10, 0));
		return map;
	}

}
