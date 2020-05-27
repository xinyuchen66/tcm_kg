package com.tcm.web.controller.qaos;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.tcm.common.qaos.QAConst;
import com.tcm.core.qaos.service.QATagService;
import com.tcm.core.qaos.service.QATrendService;
import com.tcm.dal.qaos.po.TagPo;
import com.tcm.dal.qaos.po.TopicReplyContrastPo;

@Controller
@RequestMapping(value = QAConst.QA_URL_BASE + QAConst.QA_TREND_URL_BASE)
public class QATrendConroller {

	@Resource
	private QATrendService qaTrendService;

	@Resource
	private QATagService qaTagService;

	private static Logger logger = Logger.getLogger(QATrendConroller.class);

	/**
	 * 默认获取当月的搜索词列表
	 * 
	 * @param request
	 * @param response
	 * @param period
	 * @return [ { "label":"麻黄", "nb_visits":78, "nb_hits":397,
	 *         "sum_time_spent":24809, "exit_nb_visits":28,
	 *         "nb_pages_per_search":5.1, "avg_time_on_page":318,
	 *         "bounce_rate":"0%", "exit_rate":"36%" } ... ]
	 */
	@RequestMapping(value = "/keywords", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray getSearchKeyWords(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "period", defaultValue = "month") String period) {
		try {
			return qaTrendService.getSearchKeywords(period,
					QAConst.DEFAULT_SITE_ID);
		} catch (ClientProtocolException e) {
			logger.debug("QATrendConroller-->getSearchKeyWords-->ClientProtocolException");
			return null;
		} catch (IOException e) {
			logger.debug("QATrendConroller-->getSearchKeyWords-->IOException");
			return null;
		}
	}

	/*@RequestMapping(value = "/visits", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray getVisitsStatOfWeek(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "period", defaultValue = "day") String period) {
		try {
			//return qaTrendService.getVisitsOfWeek(period, QAConst.DEFAULT_SITE_ID);
			
		} catch (ClientProtocolException e) {
			logger.debug("QATrendConroller-->getVisitsStatOfWeek-->ClientProtocolException");
			return null;
		} catch (IOException e) {
			logger.debug("QATrendConroller-->getVisitsStatOfWeek-->IOException");
			return null;
		}
	}*/
	
	@RequestMapping(value = "/visits", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray getVisitsStatOfWeek(@RequestParam(value = "start",defaultValue="0") String start,
			@RequestParam(value = "end",defaultValue="0") String end) {
		String result = qaTrendService.getPageviewByDay(start, end);
		JSONArray array = JSONArray.fromObject(result);
		return array;			
	}

	/**
	 * 根据时间,查询某个时间范围内创建的标签; - 时间并未使用，搜索引用数量最多的且属于药方病证的tag
	 * 
	 * Egg. Year:"2013" | Month:"2013-12" | Date:"2013-12-12"
	 * 
	 * @param request
	 * @param response
	 * @param timeFormat
	 * @return
	 *     [
	 *         {
	 *             TagPo
	 *         },
	 *         ...
	 *     ]
	 */
	@RequestMapping(value = "/tags/{timeFormat}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getTagsByPeriod(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "timeFormat") String timeFormat) {
		List<TagPo> list = qaTagService.queryTags(timeFormat);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		return map;
	}

	//暂时查看趋势饼图
	@RequestMapping(value = "/tags/temrank", method = RequestMethod.GET)
	@ResponseBody
	public String getTagsByPeriod(HttpServletRequest request,
											   HttpServletResponse response) {
		String tem = "{'gongxiao': [{'name': '风湿', 'count': 5087}, {'name': '降压', 'count': 1628}, {'name': '消炎', 'count': 990}, {'name': '去除', 'count': 536}, {'name': '止痛', 'count': 360}, {'name': '和血', 'count': 330}, {'name': '消肿', 'count': 186}, {'name': '降血压', 'count': 155}, {'name': '退热', 'count': 143}, {'name': '补血', 'count': 123}, {'name': '和胃', 'count': 117}, {'name': '催乳', 'count': 114}, {'name': '和中', 'count': 108}, {'name': '止咳', 'count': 91}, {'name': '活血', 'count': 89}, {'name': '肝肾', 'count': 88}, {'name': '补肾', 'count': 88}, {'name': '止血', 'count': 85}, {'name': '通气', 'count': 79}, {'name': '利尿', 'count': 72}, {'name': '解毒', 'count': 58}, {'name': '调经', 'count': 57}, {'name': '凉风', 'count': 56}, {'name': '麻醉', 'count': 55}, {'name': '抗菌', 'count': 55}, {'name': '通便', 'count': 54}, {'name': '长骨', 'count': 47}, {'name': '清热', 'count': 45}, {'name': '止泻', 'count': 44}, {'name': '壮骨', 'count': 40}, {'name': '滋养', 'count': 39}, {'name': '生发', 'count': 39}], 'dongwu': [{'name': '狼', 'count': 1269}, {'name': '牛', 'count': 865}, {'name': '马', 'count': 787}, {'name': '犬', 'count': 191}, {'name': '猪', 'count': 140}, {'name': '猫', 'count': 89}, {'name': '蛔虫', 'count': 87}, {'name': '鸭', 'count': 47}, {'name': '驴', 'count': 14}, {'name': '山羊', 'count': 11}, {'name': '狐狸', 'count': 10}, {'name': '鹌鹑', 'count': 9}, {'name': '鸳鸯', 'count': 7}, {'name': '蟋蟀', 'count': 6}, {'name': '珊瑚', 'count': 6}, {'name': '带鱼', 'count': 5}, {'name': '海狗', 'count': 5}, {'name': '黄鳝', 'count': 3}, {'name': '鲤', 'count': 3}, {'name': '牦牛', 'count': 3}, {'name': '狍', 'count': 3}, {'name': '刺猬', 'count': 2}, {'name': '泥鳅', 'count': 2}, {'name': '鳕鱼', 'count': 2}, {'name': '野马', 'count': 2}, {'name': '鳕', 'count': 2}, {'name': '壁虎', 'count': 2}, {'name': '八哥', 'count': 1}, {'name': '蝙蝠', 'count': 1}, {'name': '藏羚', 'count': 1}, {'name': '草鱼', 'count': 1}, {'name': '蝮蛇', 'count': 1}], 'huahewu': [{'name': '秋水仙碱', 'count': 46}, {'name': '茶碱', 'count': 32}, {'name': '芦丁', 'count': 26}, {'name': '阿托品', 'count': 21}, {'name': '麻黄碱', 'count': 20}, {'name': '地高辛', 'count': 17}, {'name': '茄碱', 'count': 15}, {'name': '腺苷', 'count': 14}, {'name': '咖啡因', 'count': 13}, {'name': '奎宁', 'count': 8}, {'name': '士的宁', 'count': 8}, {'name': '富马酸', 'count': 6}, {'name': '川芎嗪', 'count': 6}, {'name': '葛根素', 'count': 6}, {'name': '吗啡', 'count': 6}, {'name': '七叶皂苷', 'count': 5}, {'name': '琥珀酸', 'count': 4}, {'name': '甘草酸', 'count': 4}, {'name': '长春新碱', 'count': 3}, {'name': '天麻素', 'count': 3}, {'name': '麦角胺', 'count': 2}, {'name': '大蒜素', 'count': 2}, {'name': '小檗碱', 'count': 2}, {'name': '棉酚', 'count': 2}, {'name': '加兰他敏', 'count': 2}, {'name': '齐墩果酸', 'count': 2}, {'name': '苦参碱', 'count': 2}, {'name': '山莨菪碱', 'count': 2}, {'name': '斑蝥素', 'count': 2}, {'name': '甜菜碱', 'count': 1}, {'name': '阿魏酸', 'count': 1}, {'name': '青藤碱', 'count': 1}], 'fangji': [{'name': '地黄丸', 'count': 244}, {'name': '降压片', 'count': 166}, {'name': '膏药', 'count': 165}, {'name': '六味地黄丸', 'count': 162}, {'name': '止痛药', 'count': 138}, {'name': '点药', 'count': 103}, {'name': '消渴丸', 'count': 65}, {'name': '药酒', 'count': 63}, {'name': '白酒', 'count': 56}, {'name': '面膏', 'count': 54}, {'name': '葡萄酒', 'count': 48}, {'name': '白凤丸', 'count': 40}, {'name': '乌鸡白凤丸', 'count': 38}, {'name': '逍遥丸', 'count': 36}, {'name': '麻药', 'count': 36}, {'name': '稳心颗粒', 'count': 34}, {'name': '护肝片', 'count': 30}, {'name': '复方丹参片', 'count': 30}, {'name': '速效救心丸', 'count': 30}, {'name': '利胆片', 'count': 26}, {'name': '溃疡散', 'count': 23}, {'name': '珍珠粉', 'count': 22}, {'name': '消炎利胆片', 'count': 22}, {'name': '肾气丸', 'count': 21}, {'name': '三金片', 'count': 19}, {'name': '止痛膏', 'count': 19}, {'name': '健胃消食片', 'count': 19}, {'name': '止血药', 'count': 18}, {'name': '擦药', 'count': 18}, {'name': '益气丸', 'count': 17}, {'name': '风湿膏', 'count': 14}, {'name': '点眼药', 'count': 14}], 'zhiwu': [{'name': '桃', 'count': 1075}, {'name': '扁桃', 'count': 885}, {'name': '茶', 'count': 833}, {'name': '葡萄', 'count': 700}, {'name': '荨麻', 'count': 419}, {'name': '梅', 'count': 309}, {'name': '地黄', 'count': 272}, {'name': '李', 'count': 263}, {'name': '艾', 'count': 255}, {'name': '菊', 'count': 219}, {'name': '丹参', 'count': 205}, {'name': '莲', 'count': 147}, {'name': '杏', 'count': 143}, {'name': '梨', 'count': 129}, {'name': '辣椒', 'count': 124}, {'name': '苹果', 'count': 121}, {'name': '枣', 'count': 119}, {'name': '绿豆', 'count': 111}, {'name': '芦荟', 'count': 106}, {'name': '银杏', 'count': 102}, {'name': '枸杞', 'count': 98}, {'name': '香蕉', 'count': 96}, {'name': '蚕豆', 'count': 90}, {'name': '决明', 'count': 88}, {'name': '黄连', 'count': 87}, {'name': '姜', 'count': 86}, {'name': '决明子', 'count': 80}, {'name': '西洋参', 'count': 78}, {'name': '玫瑰', 'count': 75}, {'name': '西瓜', 'count': 75}, {'name': '罗布麻', 'count': 70}, {'name': '甘草', 'count': 65}], 'jibin': [{'name': '高血压', 'count': 13761}, {'name': '发热', 'count': 11478}, {'name': '出血', 'count': 9685}, {'name': '糖尿病', 'count': 9345}, {'name': '腹泻', 'count': 6989}, {'name': '便秘', 'count': 6757}, {'name': '咳嗽', 'count': 6290}, {'name': '头痛', 'count': 6098}, {'name': '瘤', 'count': 5474}, {'name': '疹', 'count': 5116}, {'name': '感冒', 'count': 4988}, {'name': '腹痛', 'count': 4958}, {'name': '呕吐', 'count': 4645}, {'name': '水肿', 'count': 4616}, {'name': '溃疡', 'count': 4353}, {'name': '斑', 'count': 4000}, {'name': '心脏病', 'count': 3997}, {'name': '耳鸣', 'count': 3867}, {'name': '贫血', 'count': 3780}, {'name': '关节炎', 'count': 3751}, {'name': '恶心', 'count': 3713}, {'name': '口臭', 'count': 3355}, {'name': '甲亢', 'count': 3184}, {'name': '疱疹', 'count': 2975}, {'name': '疮', 'count': 2943}, {'name': '痛经', 'count': 2611}, {'name': '乙肝', 'count': 2570}, {'name': '骨质增生', 'count': 2543}, {'name': '眩晕', 'count': 2398}, {'name': '低血压', 'count': 2224}, {'name': '不孕', 'count': 2218}, {'name': '血尿', 'count': 2208}], 'zhenzhuang': [{'name': '头晕', 'count': 7259}, {'name': '腹泻', 'count': 6989}, {'name': '咳嗽', 'count': 6290}, {'name': '头痛', 'count': 6098}, {'name': '呕吐', 'count': 4645}, {'name': '白带', 'count': 3961}, {'name': '恶心', 'count': 3713}, {'name': '口臭', 'count': 3355}, {'name': '胸闷', 'count': 3027}, {'name': '不孕', 'count': 2218}, {'name': '关节疼痛', 'count': 2152}, {'name': '消瘦', 'count': 2098}, {'name': '闭经', 'count': 2030}, {'name': '便血', 'count': 1940}, {'name': '胸痛', 'count': 1932}, {'name': '心悸', 'count': 1576}, {'name': '浮肿', 'count': 1466}, {'name': '瘫痪', 'count': 1168}, {'name': '腰痛', 'count': 1048}, {'name': '带状疱疹', 'count': 832}, {'name': '头昏', 'count': 816}, {'name': '麻疹', 'count': 712}, {'name': '皮肤瘙痒', 'count': 684}, {'name': '发黄', 'count': 658}, {'name': '小腹痛', 'count': 609}, {'name': '近视', 'count': 574}, {'name': '肥胖', 'count': 509}, {'name': '多梦', 'count': 459}, {'name': '早泄', 'count': 450}, {'name': '小儿腹泻', 'count': 450}, {'name': '偏头痛', 'count': 430}, {'name': '脱发', 'count': 419}], 'kuangwu': [{'name': '铁', 'count': 694}, {'name': '铅', 'count': 168}, {'name': '石膏', 'count': 91}, {'name': '温泉', 'count': 24}, {'name': '水银', 'count': 18}, {'name': '龙骨', 'count': 16}, {'name': '炉甘石', 'count': 10}, {'name': '石灰', 'count': 8}, {'name': '朱砂', 'count': 8}, {'name': '消石', 'count': 7}, {'name': '食盐', 'count': 7}, {'name': '雄黄', 'count': 5}, {'name': '白矾', 'count': 4}, {'name': '理石', 'count': 4}, {'name': '磁石', 'count': 4}, {'name': '琥珀', 'count': 4}], 'xuewei': [{'name': '膝关', 'count': 4441}, {'name': '子宫', 'count': 2384}, {'name': '胆囊', 'count': 1186}, {'name': '太阳', 'count': 701}, {'name': '阑尾', 'count': 403}, {'name': '天突', 'count': 351}, {'name': '会阴', 'count': 234}, {'name': '幽门', 'count': 212}, {'name': '兴奋', 'count': 111}, {'name': '下关', 'count': 104}, {'name': '髋骨', 'count': 85}, {'name': '中枢', 'count': 81}, {'name': '不容', 'count': 73}, {'name': '上星', 'count': 69}, {'name': '球后', 'count': 55}, {'name': '止泻', 'count': 44}, {'name': '阳谷', 'count': 42}, {'name': '大都', 'count': 41}, {'name': '落地', 'count': 41}, {'name': '安眠', 'count': 39}, {'name': '上关', 'count': 37}, {'name': '水分', 'count': 33}, {'name': '大包', 'count': 28}, {'name': '腕骨', 'count': 27}, {'name': '二间', 'count': 24}, {'name': '鱼尾', 'count': 23}, {'name': '日月', 'count': 22}, {'name': '中注', 'count': 22}, {'name': '风池', 'count': 19}, {'name': '中都', 'count': 18}, {'name': '内关', 'count': 16}, {'name': '三间', 'count': 16}]}";
		return tem;
	}
	/**
	 * 搜索引用数量最多的且属于药方病证的tag
	 * 
	 */
	@RequestMapping(value = "/tags/popularTagsApi", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getPopularTagsApi(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("data", qaTagService.queryTags(null));
			map.put("code", 1);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 0);
			map.put("msg", "服务器出错啦");
		}
		return map;
	}
	
	
	/**
	 * 根据年份和关键字,查询当年每月的问答对比情况
	 * 
	 * @param request
	 * @param response
	 * @param year
	 * @param query
	 * @return
	 *     [
	 *         {
	 *             TopicReplyContrastPo
	 *         },
	 *         ...
	 *     ]
	 */
	@RequestMapping(value = "/contrast/{year}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getTopicReplyConst(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "year") String year,
			@RequestParam(value="query") String query) {
		List<TopicReplyContrastPo> list = qaTrendService.queryTopicReplyContrast(year,query);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		return map;
	}
}
