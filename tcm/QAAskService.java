package com.tcm.core.qaos.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

import com.tcm.dal.qaos.po.RSSPo;
import com.tcm.dal.qaos.po.ReplyPo;
import com.tcm.dal.qaos.po.TagTopicMapPo;
import com.tcm.dal.qaos.po.TopicPo;
import com.tcm.dal.user.po.AwardPo;
import com.tcm.dal.user.po.UserInfoPo;

public interface QAAskService {
	/**
	 * 根据topicId删除主题
	 * 
	 * @param topicId
	 * @return
	 */
	boolean deleteTopic(long topicId);

	/**
	 * 对主题点赞
	 * 
	 * @param topicId
	 * @return
	 */
	boolean supportTopic(long topicId);


	/**
	 * 对主题拍砖
	 * 
	 * @param topicId
	 * @return
	 */
	boolean opposeTopic(long topicId);

	/**
	 * 把主题设置成不可编辑
	 * 
	 * @param topicId
	 * @return
	 */
	boolean setNotEditable(long topicId);

	/**
	 * 根据用户ID,分页请求提问列表
	 * 
	 * @param userId
	 * @param pageNumber
	 * @return { "size":11, "list":[ {TopicPo}, {}, …… ] }
	 */
	Map<String, Object> getAsks(long userId, int pageNumber);

	/**
	 * 根据用户ID分页请求标签列表
	 * 
	 * @param userId
	 * @param pageNumber
	 * @return { "size":100, "list":[ {TagPo}, ... ] }
	 */
	Map<String, Object> getTags(long userId, int pageNumber);

	/**
	 * 根据回复，查找回复所属的主题
	 * 
	 * @param userId
	 * @param pageNumber
	 * @return { "size":11, "list":[ {TopicPo}, {}, …… ] }
	 */
	Map<String, Object> getTopicsByReplys(long userId, int pageNumber);

	/**
	 * 分页获得提问列表
	 * 
	 * @param pageNumber
	 * @return
	 */
	void addAsksList(ModelMap modelMap, int pageNumber, String query,int sortBy,int disCate, String tag);

	/**
	 * 分页获得提问列表
	 * 
	 * @param pageNumber
	 * @return
	 */
	List<TopicPo> getAsks(int pageNumber, String query);

	/**
	 * 根据query,请求topics
	 * 
	 * @param query
	 * @return
	 */
	List<TopicPo> queryAsks(String query);

	/**根据请求参数，查询符合条件的总数据量
	 * @param map
	 * @return
	 */
	int queryAsksCnt(Map<String, Object> map);
	
	/**查询一个科室包含的疾病列表
	 * @param departId
	 * @return
	 */
	List<Long> getDisList(int departId);
	/**
	 * 根据query,分页请求topics
	 * 
	 * @param query
	 * @return
	 */
	List<TopicPo> queryAsksByPage(Map<String, Object> map);

	/**
	 * 分页请求topics
	 * 
	 * @return
	 */
	List<TopicPo> queryAsks();

	/**
	 * 添加新主题
	 * 
	 * @param map
	 *            {"topicTitle":"xxxx","topicBody":"xxxx"}
	 * @return 新主题的ID
	 */
	long addNewTopic(HttpServletRequest request, TopicPo topicPo);

	/**
	 * 查询某一主题的回复
	 * 
	 * @param topicId
	 * @param pageNumber
	 * @return
	 */
	Map<String, Object> getReplys(int topicId, int pageNumber);

	/**
	 * QAOS,提问时，上传图片
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	String uploadTopicImages(HttpServletRequest request, HttpServletResponse response) throws IOException;

	/**
	 * 把ID为askId的主题内容添加到modelMap
	 * 
	 * @param askId
	 * @param modelMap
	 */
	void addTopic(int askId, ModelMap modelMap);

	/**
	 * 2019 添加一条新回复，并返还replyId
	 * 
	 * @param request
	 * @param replyPo
	 * @param topicId
	 * @return
	 */
	long addNewReply(HttpServletRequest request, ReplyPo replyPo, long topicId);

	/**
	 * 将tag字符串数组插入到数据库中
	 * 
	 * @param tagArray
	 * @param userId
	 * @return
	 */
	boolean addTags(String[] tagArray, long userId);

	/**
	 * 把topicId,tagId映射插入到qa_tag_topic
	 * 
	 * @param list
	 * @param topicId
	 * @return
	 */
	boolean addTagTopicMap(List<TagTopicMapPo> list, long topicId);

	/**
	 * 根据tagId查找相关的问题,并绑定到页面
	 * 
	 * @param modelMap
	 * @param pageNumber
	 * @param tagId
	 */
	void bindTopicsToPage(ModelMap modelMap, int pageNumber, int tagId);

	/**
	 * 根据tagName查找相关的问题,并绑定到页面
	 * 
	 * @param modelMap
	 * @param pageNumber
	 * @param tagId
	 */
	void bindTopicsToPage(ModelMap modelMap, int pageNumber, String tagName);

	/**
	 * 寻找与ID为topicId的问题的相关问题
	 * 
	 * @param topicId
	 * @return
	 */
	List<TopicPo> queryRelatedTopics(int topicId);

	/**
	 * 根据模糊的用户名,查找相关用户
	 * 
	 * @param userName
	 * @return
	 */
	List<UserInfoPo> queryUsersByFuzzyName(String userName);

	/**
	 * 获取同时包含两个标签的topics
	 * 
	 * @param firstTagName
	 * @param secondTagName
	 * @return
	 */
	List<TopicPo> queryTopics(int pageNumber, String firstTagName, String secondTagName);

	/**
	 * 获取同时包含两个标签的topics绑定到页面
	 * 
	 * @param modelMap
	 * @param pageNumber
	 * @param firstTagName
	 * @param secondTagName
	 */
	void bindTopicsToPage(ModelMap modelMap, int pageNumber, String firstTagName, String secondTagName);

	/**
	 * 获得提问总数
	 * 
	 * @return
	 */
	long getTopicsCnt();
	
	/**获取分类的词典
	 * @return
	 */
	Map<String, Object> getClasses();

	/**
	 * 根据查询的关键字,返回问题数和回答数
	 * 
	 * @param query
	 * @return map{"answerNum": 79, "questionNum": 395})
	 */
	Map<String, Object> statNumbers(String query);

	/**
	 * 根据query,分页请求Topics
	 * 
	 * @param query
	 * @param pageNumber
	 * @return
	 */
	List<TopicPo> queryTopics(String query, int pageNumber);

	/**
	 * 查询相近的title
	 * 
	 * @param title
	 * @return
	 */
	List<TopicPo> querySimilarTitles(String title);

	/**
	 * 添加一条新的订阅;返回生成的Id
	 * 
	 * @param userId
	 * @param topicId
	 * @return
	 */
	int addRSS(RSSPo rss);

	/**
	 * 根据id软删除一条RSS
	 * 
	 * @param id
	 * @return
	 */
	boolean removeRSS(int id);

	/**
	 * 软删除一条RSS
	 * 
	 * @param rss
	 * @return
	 */
	boolean removeRSS(RSSPo rss);

	/**
	 * 分页获取某个用户订阅的topics
	 * 
	 * @param userId
	 * @param page
	 * @return map:{"size":100,"list":[{topicPo},{}]}
	 */
	Map<String, Object> queryRSSTopics(long userId, int page);

	/**
	 * 检查订阅是否存在
	 * 
	 * @param rss
	 * @return
	 */
	boolean checkRSS(RSSPo rss);

	/**
	 * 筛选最近的用户奖励记录
	 * 
	 * @param limit
	 *            返回多少条结果
	 * @param awardId
	 *            >0时,触发筛选
	 * @return
	 */
	List<AwardPo> queryAqards(int limit, int awardId);

	/**
	 * 获取过滤表
	 * 
	 * @return List<String>
	 */
	List<String> getFliterWords();

	/**
	 * 对String内容进行过滤
	 * 
	 * @return String
	 */
	String doFliter(String txt);

}
