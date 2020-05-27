<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="keywords" content="知识归纳,专业知识问答,中草药专业知识服务系统">
<meta name="description" content="通过对中草药问题进行分析挖掘，对用户问题进行分类和归纳">
<title>知识归纳-专业知识问答-中草药专业知识服务系统</title>
<link rel=stylesheet type=text/css
	href="${initParam.resources}/resources/exlib/bootstrap/css/bootstrap-responsive.css">
<link rel=stylesheet type=text/css
	href="${initParam.resources}/resources/exlib/bootstrap/css/bootstrap.css">
<link rel=stylesheet type=text/css
	href="${initParam.resources}/resources/exlib/bootstrap/css/bootstrap-combined.min.css">
<link rel=stylesheet type=text/css
	href="${initParam.resources}/resources/qaos/css/common.css" />
<link rel=stylesheet type=text/css
	href="${initParam.resources}/resources/qaos/css/knowledge_conclusion.css" />
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
		<div class="location">当前位置：<a href="${initParam.base}/"> 首页</a> > <a href="/tcm/qaos/home">专业知识问答系统</a> > 知识归纳
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
				<%--<a href="${initParam.base}/qaos/home"> <img--%>
					<%--src="${initParam.resources}/resources/qaos/images/logo.png" />--%>
				<%--</a>--%>
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
								<%--<li class="active"><a--%>
									<%--href="${initParam.base}/qaos/kconclusions">知识归纳</a></li>--%>
								<%--<li><a href="${initParam.base}/qaos/profile">知识网络</a></li>--%>
								<%--<li><a href="${initParam.base}/qaos/trend">问答趋势</a></li>--%>
                                <%--&lt;%&ndash;2018-4-22注释用户分析-该模块已在个人中心中实现&ndash;%&gt;--%>
								<%--&lt;%&ndash;<li><a href="${initParam.base}/qaos/useranalysis">用户分析</a>&ndash;%&gt;--%>
								<%--&lt;%&ndash;</li>&ndash;%&gt;--%>
							<%--</ul>--%>
							<%--<ul class="nav pull-left" style="margin-left: 90px;">--%>
								<%--<li>--%>
									<%--<form class="navbar-form ">--%>
										<%--<input type="text" class="span2" id="search_key">--%>
										<%--<div class="btn-group">--%>
											<%--<a class="btn" href="javascript:void(0);"--%>
												<%--id="subject_search_a">主题搜索</a>--%>
											<%--&lt;%&ndash;<button class="btn dropdown-toggle" data-toggle="dropdown">&ndash;%&gt;--%>
												<%--&lt;%&ndash;<span class="caret"&ndash;%&gt;--%>
													<%--&lt;%&ndash;style="height: 8px; opacity: 1; border-top-color: #000;"></span>&ndash;%&gt;--%>
											<%--&lt;%&ndash;</button>&ndash;%&gt;--%>
											<%--&lt;%&ndash;<ul class="dropdown-menu">&ndash;%&gt;--%>
												<%--&lt;%&ndash;<li><a href="javascript:void(0);" id="tags_search_a">标签搜索</a></li>&ndash;%&gt;--%>
												<%--&lt;%&ndash;<li><a href="javascript:void(0);" id="users_search_a">用户搜索</a></li>&ndash;%&gt;--%>
												<%--&lt;%&ndash;<li><a href="#" id="ask_search_a">问题搜索</a></li>&ndash;%&gt;--%>
											<%--&lt;%&ndash;</ul>&ndash;%&gt;--%>
										<%--</div>--%>
									<%--</form>--%>
								<%--</li>--%>
								<%--<li class="divider-vertical"></li>--%>
							<%--</ul>--%>
							<%--<a class="btn" style="margin-right: 20px; float: right;" href="${initParam.base}/qaos/topics/ask/add"--%>
								<%--id="post_topic">我要提问</a>--%>
						<%--</div>--%>
					<%--</div>--%>
				<%--</div>--%>
			<%--</div>--%>
		<%--</div>--%>
			<div class="sub_search_bar">
				<input type="text" class="span2 sub_search_text" id="search_key">
					<a class="btn sub_search_btn" href="#" id="subject_search_a" style="border-radius: 0">主题搜索</a>
				</div>
			</div>
		<div id="main">
			<div id="content">
				<div style="width: 730px;">
					<div id="head_title">知识归纳</div>
					<div id="kc_tips">提示：先在上方输入框中输入您要找的关键词，点击"主题搜索"，系统将为您可视化展现知识的归纳</div>
					<div>
						<img
							src="${initParam.resources}/resources/qaos/images/tip_line_up.png">
					</div>
					<div>
						<img
							src="${initParam.resources}/resources/qaos/images/knowledgediscovery.png" />
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

<script
	src="${initParam.resources}/resources/exlib/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript"
	src="${initParam.resources}/resources/qaos/js/common.js"></script>

<script type="text/javascript"
	src="${initParam.resources}/resources/qaos/js/knowledge_conclusion.js"></script>
</html>