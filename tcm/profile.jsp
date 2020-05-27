<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="keywords" content="提问信息,专业知识问答,中草药专业知识服务系统">
	<meta name="description" content="收集和整理有关中草药的药物、方剂、疾病、证候方面的提问">
	<title>知识网络-专业知识问答-中草药专业知识服务系统</title>
	<!-- css -->
	<%-- <link rel=stylesheet type=text/css href="${initParam.resources}/resources/exlib/bootstrap/css/bootstrap.css"> --%>
	<link rel=stylesheet type=text/css href="${initParam.resources}/resources/exlib/bootstrap/css/bootstrap.css">
	<link rel=stylesheet type=text/css href="${initParam.resources}/resources/exlib/simple_pagination/simplePagination.css">
	<link rel=stylesheet type=text/css href="${initParam.resources}/resources/common/css/common.css">
	<link rel=stylesheet type=text/css href="${initParam.resources}/resources/qaos/css/copyNavCommon.css">
	<link rel=stylesheet type=text/css href="${initParam.resources}/resources/qaos/css/qaListSenior.css">
	<link rel=stylesheet type=text/css href="${initParam.resources}/resources/home/css/newhome.css">
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

<!-- main content -->
<div class="main-container">
	<div class="location">当前位置：<a href="${initParam.base}/"> 首页</a> > <a href="/tcm/qaos/home">专业知识问答系统</a> > 知识网络
		<a class="icon ion-help-circled" href="/tcm/user/getmanual#qaos" target="_blank"></a>
	</div>

	<div style="text-align:center;">
		<select id="choose_type" style="border: #379bc3 1px solid;line-height: 35px;padding: 0 8px;width: 80px;height: 35px;margin:auto;">
			<option>综合</option>
			<option>疾病</option>
		</select>
		<input id="inputWord" style="border: #379bc3 1px solid;line-height: 35px;padding: 0 8px;width: 360px;margin:-4px;">
		<a id="enter"href="javascript:void(0)" onclick="window.app.enter()" style="display: inline-block;width: 108px;height: 37px;box-sizing: border-box;line-height: 34px;font-size: 16px;text-align: center;background: #379bc3;    color: #fff;">
			检索<i class="icon ion-search"></i>
		</a>
	</div>

	<div class="main bg2">
		<div id='app'>
			<div class="no-data text-center" v-if="noData">没有数据！</div>
			<div v-else id="main" style="width: 120%;height: 800px;margin-left: -10%;margin-right: auto"></div>
		</div>
	</div>
</div>

<div class="article" id="qaTitle" style="margin:0 auto;width:400px;">
	<div class="article-item health">
		<div style="text-align: center; visibility: hidden; " class="title-box" id="qaname"><span class="title active" >有关问题</span></div>
		<ul class="content" id="qaList">
		</ul>
	</div>
</div>
<!-- footer -->
<div class="home-footer">
	<%@include file="/WEB-INF/views/commonpages/footer.jsp"%>
</div>

</body>
<!-- js -->
<script src="${initParam.resources}/resources/common/ajax.js"></script>
<script>
	$(document).keyup(function(event){
		if(event.keyCode ==13){
			$("#enter").trigger("click");
		}
	});
</script>
<!-- exlib -->
<%-- <script src="${initParam.resources}/resources/exlib/jquery/2.1.3/jquery.min.js"></script>  --%>
<script src="${initParam.resources}/resources//search/js/bootstrap.min.js"></script>
<%-- <script src="${initParam.resources}/resources/exlib/bootstrap3/js/bootstrap.min.js"></script>  --%>
<script type="text/javascript" src="${initParam.resources}/resources/analysis/js/tagCloud.js"></script>
<script src="${initParam.resources}/resources/exlib/simple_pagination/jquery.simplePagination.js"></script>
<script src="${initParam.resources}/resources/qaos/js/copyNavCommon.js"></script>
<%--<script src="${initParam.resources}/resources/exlib/d3/d3.v3.js"></script>--%>
<%--<script src="${initParam.resources}/resources/exlib/d3/d3.layout.cloud.js"></script>--%>
<script src="${initParam.resources}/resources/qaos/js/echarts.min.js"></script>
<script src="${initParam.resources}/resources/qaos/js/axios.min.js"></script>
<script src="${initParam.resources}/resources/qaos/js/vue.js"></script>
<script src="${initParam.resources}/resources/qaos/js/knowledge.js"></script>
<script>
	$("#inputWord").attr("value",window.app.input);
</script>
</html>