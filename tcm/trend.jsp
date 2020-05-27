<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="keywords" content="趋势分析,专业知识问答,中草药专业知识服务系统">
<meta name="description" content="基于用户行为跟踪，并利用用户的回答数据，统计和分析中草药药物主题的趋势">
<title>趋势分析-专业知识问答-中草药专业知识服务系统</title>
<!-- css -->
<link rel=stylesheet type=text/css
	href="${initParam.resources}/resources/exlib/bootstrap/css/bootstrap-responsive.css">
<link rel=stylesheet type=text/css
	href="${initParam.resources}/resources/exlib/bootstrap/css/bootstrap.css">
<link rel=stylesheet type=text/css
	href="${initParam.resources}/resources/exlib/bootstrap/css/bootstrap-combined.min.css">
<link rel=stylesheet type=text/css
	href="${initParam.resources}/resources/qaos/css/common.css" />
<link rel=stylesheet type=text/css
	href="${initParam.resources}/resources/qaos/css/trend.css" />
</head>
<body>
<!-- nav header -->
<div>
	<%@include file="/WEB-INF/views/commonpages/nav_header.jsp"%>
</div><!-- nav header end -->

<!-- logo search -->
<%@include file="/WEB-INF/views/commonpages/logo_search.jsp"%>
<!-- logo search end -->

<!-- nav resource -->
<%@include file="/WEB-INF/views/commonpages/resource_nav.jsp"%>
<!-- nav resource end -->

<div class="main-container">
	<div class="location">当前位置：<a href="${initParam.base}/"> 首页</a> > <a href="/tcm/qaos/home">专业知识问答系统</a> > 趋势分析
		<a class="icon ion-help-circled" href="/tcm/user/getmanual#qaos" target="_blank"></a>
	</div>
</div>
	<div id="container">
		<%--<div>--%>
			<%--<%@include file="/WEB-INF/views/commonpages/nav_header.jsp"%>--%>
		<%--</div>--%>
		<%--<div id="logo_part"--%>
			<%--style="background-image: url(${initParam.resources}/resources/qaos/images/top_back.png);">--%>
			<%--<div id="logo_content">--%>
				<%--<a href="${initParam.base}/qaos/home"><img--%>
					<%--src="${initParam.resources}/resources/qaos/images/logo.png" /></a>--%>
			<%--</div>--%>
		<%--</div>--%>
		<%--<div id="nav_menu">--%>
			<%--<div class="navbar">--%>
				<%--<div class="navbar-inner">--%>
					<%--<div class="container-fluid">--%>
						<%--<a data-target=".navbar-responsive-collapse"--%>
							<%--data-toggle="collapse" class="btn btn-navbar"><span--%>
							<%--class="icon-bar"></span><span class="icon-bar"></span><span--%>
							<%--class="icon-bar"></span></a>--%>
						<%--<div class="nav-collapse collapse navbar-responsive-collapse">--%>
							<%--<ul class="nav" id="menu_ul">--%>
								<%--<li><a href="${initParam.base}/qaos/topics/ask/list?page=1&sortBy=101">提问空间</a></li>--%>
								<%--<li><a href="${initParam.base}/qaos/kconclusions">知识归纳</a></li>--%>
								<%--<li><a href="${initParam.base}/qaos/profile">知识网络</a></li>--%>
								<%--<li class="active"><a href="${initParam.base}/qaos/trend">问答趋势</a></li>--%>
								<%--&lt;%&ndash;2018-4-22注释用户分析-该模块已在个人中心中实现&ndash;%&gt;--%>
								<%--&lt;%&ndash;<li><a href="${initParam.base}/qaos/useranalysis">用户分析</a>&ndash;%&gt;--%>
								<%--&lt;%&ndash;</li>&ndash;%&gt;--%>
							<%--</ul>--%>
							<%--<ul class="nav pull-left" style="margin-left: 90px;">--%>
								<%--&lt;%&ndash;<li>&ndash;%&gt;--%>
									<%--&lt;%&ndash;<form class="navbar-form ">&ndash;%&gt;--%>
										<%--&lt;%&ndash;<input type="text" class="span2" id="search_key">&ndash;%&gt;--%>
										<%--&lt;%&ndash;<div class="btn-group">&ndash;%&gt;--%>
											<%--&lt;%&ndash;<a class="btn" href="javascript:void(0);"&ndash;%&gt;--%>
												<%--&lt;%&ndash;id="subject_search_a">主题搜索</a>&ndash;%&gt;--%>
											<%--&lt;%&ndash;<button class="btn dropdown-toggle" data-toggle="dropdown">&ndash;%&gt;--%>
												<%--&lt;%&ndash;<span class="caret"&ndash;%&gt;--%>
													<%--&lt;%&ndash;style="height: 8px; opacity: 1; border-top-color: #000;"></span>&ndash;%&gt;--%>
											<%--&lt;%&ndash;</button>&ndash;%&gt;--%>
											<%--&lt;%&ndash;<ul class="dropdown-menu">&ndash;%&gt;--%>
												<%--&lt;%&ndash;<li><a href="javascript:void(0);" id="tags_search_a">标签搜索</a></li>&ndash;%&gt;--%>
												<%--&lt;%&ndash;<li><a href="javascript:void(0);" id="users_search_a">用户搜索</a></li>&ndash;%&gt;--%>
												<%--&lt;%&ndash;<li><a href="#" id="ask_search_a">问题搜索</a></li>&ndash;%&gt;--%>
											<%--&lt;%&ndash;</ul>&ndash;%&gt;--%>
										<%--&lt;%&ndash;</div>&ndash;%&gt;--%>
									<%--&lt;%&ndash;</form>&ndash;%&gt;--%>
								<%--&lt;%&ndash;</li>&ndash;%&gt;--%>
								<%--<li class="divider-vertical"></li>--%>
							<%--</ul>--%>
							<%--<a class="btn" style="margin-right: 20px; float: right;" href="${initParam.base}/qaos/topics/ask/add"--%>
								<%--id="post_topic">我要提问</a>--%>
						<%--</div>--%>
					<%--</div>--%>
				<%--</div>--%>
			<%--</div>--%>
		<%--</div>--%>

		<div id="main">
			<div id="content">
				<div class="tabbable">
					<ul class="nav nav-tabs">
						<li class="active"><a href="#whole_trend" data-toggle="tab"><span
								class="tab_title">总&nbsp;体 标 签&nbsp;趋&nbsp;势</span></a></li>
						<%--<li><a href="#individual_trend" id="individual_trend_a"--%>
							<%--data-toggle="tab"><span class="tab_title">个&nbsp;体&nbsp;趋&nbsp;势</span></a>--%>
						<%--</li>--%>
					</ul>
					<div class="tab-content" style="margin-top: -20px;">
						<div class="tab-pane active" id="whole_trend" style="">
							<div id="whole_menu">
								<ul class="trend_nav">
									<%--<li class="active"><a href="#visitor_stat_part"--%>
										<%--id="visitor_stat_a">访问量</a></li>--%>
										<%--li><a href="#tags_pie_gongxiao">功效饼状图</a></li--%>
										<li><a href="#tags_pie_dongwu">动物饼状图</a></li>
										<li><a href="#tags_pie_huahewu">化合物饼状图</a></li>
										<li><a href="#tags_pie_fangji">方剂饼状图</a></li>
										<li><a href="#tags_pie_zhiwu">植物饼状图</a></li>
										<li><a href="#tags_pie_jibin">疾病饼状图</a></li>
										<li><a href="#tags_pie_zhenzhuang">症状饼状图</a></li>
										<li><a href="#tags_pie_kuangwu">矿物饼状图</a></li>
										<li><a href="#tags_pie_xuewei">穴位饼状图</a></li>
								</ul>
							</div>
							<%--div id="tags_pie_gongxiao" name="tags_pie_gongxiao"></div--%>
							<div id="tags_pie_dongwu" name="tags_pie_dongwu"></div>
							<div id="tags_pie_huahewu" name="tags_pie_huahewu"></div>
							<div id="tags_pie_fangji" name="tags_pie_fangji"></div>
							<div id="tags_pie_zhiwu" name="tags_pie_zhiwu"></div>
							<div id="tags_pie_jibin" name="tags_pie_jibin"></div>
							<div id="tags_pie_zhenzhuang" name="tags_pie_zhenzhuang"></div>
							<div id="tags_pie_kuangwu" name="tags_pie_kuangwu"></div>
							<div id="tags_pie_xuewei" name="tags_pie_xuewei"></div>
						</div>

					</div>
				</div>
			</div>
		</div>
		<div>
			<%@include file="/WEB-INF/views/commonpages/footer.jsp"%>
		</div>
	</div>
</body>

<%--<script type="text/javascript"
	src="${initParam.resources}/resources/common/jquery_1_8_3.js"></script>--%>
<script type="text/javascript"
	src="${initParam.resources}/resources/common/ajax.js"></script>
<script type="text/javascript"
	src="${initParam.resources}/resources/exlib/highcharts/js/highcharts.js"></script>

<script
	src="${initParam.resources}/resources/exlib/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript"
	src="${initParam.resources}/resources/qaos/js/common.js"></script>

<script type="text/javascript"
	src="${initParam.resources}/resources/qaos/js/trend.js"></script>
</html>