<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
 <%request.setCharacterEncoding("UTF-8");response.setContentType("text/html;charset=utf-8"); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">

<head>
<meta charset="utf-8">
<!-- 显示地声明如果用ie浏览器的化，要用最新的版本的视图引擎来渲染页面 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<title>角色管理页面</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/lib/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/lib/datetimepicker/css/bootstrap-datetimepicker.css">
<!--[if lt IE 9]>
      <script src="${pageContext.request.contextPath}/static/lib/html5shiv/html5shiv.min.js"></script>
      <script src="${pageContext.request.contextPath}/static/lib/respond/respond.min.js"></script>
    <![endif]-->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/font/iconfont.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/user.css">
</head>

<body>
	<div class="container-fluid">
		<div class="row">
			<div class="search_title_bar">收索</div>
		</div>
		<div class="row">
			<form id="search_form" class="form-inline" action="${pageContext.request.contextPath}/admin/roleSearch.html" method="post">
				<div class="form-group">
					<label>角色信息:</label> <input class="form-control input-sm" type="text" name="roleInfo" placeholder="角色名称/代码">
				</div>
			</form>
		</div>
		<div id="search_btn" class="row">
			<button id="searchBtn" type="button" class="btn btn-default">查询</button>
			<button type="button" data-toggle="modal" data-target="#addRoleModal" class="btn btn-default">添加</button>
			<button id="batchDelUsersBtn" type="button" class="btn btn-default">删除</button>
		</div>
		<div id="content_table" class="row">
			<div class="table-responsive">
				<table class="table table-bordered table-hover">
					<tr>
						<td style="width: 30px;"><input type="checkbox" class="chkall" onclick="chkall();"></td>
						<td>角色名称</td>
						<td>角色代码</td>
						<td style="width: 100px;">操作</td>
					</tr>
					<c:forEach items="${roleDatasByPager.list}" var="role">
					<tr>
						<td><input value="${role.id}" type="checkbox" name="chkuser" class="chkone" onclick="chkone();"></td>
						<td>${role.name}</td>
						<td>${role.code}</td>
						<td><a href="#updateRoleModal" data-toggle="modal">编辑</a> <a href="#">删除</a></td>
					</tr>
				   </c:forEach>	
				</table>
			</div>
		</div>
		<div id="pager" class="row">
			<p class="pull-left">
				总共有<span>  ${roleDatasByPager.total} </span>记录，当前页<span> ${roleDatasByPager.pageNum} / ${roleDatasByPager.pages} </span>页
			</p>
			<div class="btngroup pull-right">
				<button type="button" class="btn btn-default">首页</button>
				<button type="button" class="btn btn-default">上一页</button>
				<button type="button" class="btn btn-default">上一页</button>
				<button type="button" class="btn btn-default">尾页</button>
			</div>
		</div>
	</div>
	<!-- Modal 添加 -->
	<div class="modal fade" data-backdrop="false" id="addRoleModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span>&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">添加角色</h4>
				</div>
				<div class="modal-body">
					<form id="addUserFrom" action="${pageContext.request.contextPath}/admin/addRole.html" method="post">
						<div class="form-group">
							<label>角色名称：</label> <input type="text" name="name" class="form-control" placeholder="角色名称">
						</div>
						<div class="form-group">
							<label>角色代码：</label> <input type="text" name="code" class="form-control" placeholder="角色代码">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="addUserBtn" type="button" class="btn btn-primary">添加角色</button>
				</div>
			</div>
		</div>
	</div>
	<!-- Modal 编辑 -->
	<div class="modal fade" data-backdrop="false" id="updateRoleModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span>&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">编辑角色</h4>
				</div>
				<div class="modal-body">
					<form action="#">
						<div class="form-group">
							<label>角色名称：</label> <input type="text" name="username" class="form-control" placeholder="角色名称">
						</div>
						<div class="form-group">
							<label>角色代码：</label> <input type="text" name="username" class="form-control" placeholder="角色代码">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary">编辑角色</button>
				</div>
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/static/lib/jquery/jquery.min.js"></script>
	<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
	<script src="${pageContext.request.contextPath}/static/lib/bootstrap/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/lib/datetimepicker/js/bootstrap-datetimepicker.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/lib/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/user.js"></script>
</body>

</html>