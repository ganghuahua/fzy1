<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/backend/common/taglib.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/backend/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>CMS 后台管理工作平台</title>
<style type="text/css">
<!--
body {
	margin-left: 3px;
	margin-top: 0px;
	margin-right: 3px;
	margin-bottom: 0px;
}
.STYLE1 {
	color: #e1e2e3;
	font-size: 12px;
}
.STYLE6 {color: #000000; font-size: 12; }
.STYLE10 {color: #000000; font-size: 12px; }
.STYLE19 {
	color: #344b50;
	font-size: 12px;
}
.STYLE21 {
	font-size: 12px;
	color: #3b6375;
}
.STYLE22 {
	font-size: 12px;
	color: #295568;
}
A:active,A:visited,A:link {
	COLOR: #0629FD;
	TEXT-DECORATION: none
}

A:hover {
	COLOR: #FF6600;
	TEXT-DECORATION: none
}

A.relatelink:active,A.relatelink:visited,A.relatelink:link { 
	color: white;
	TEXT-DECORATION: none
}

A.relatelink:hover {
	COLOR: #FF6600;
	TEXT-DECORATION: none
}

td {
	font-size: 12px;
	color: #003366;
	height:24px
}

.STYLE1 a{
	COLOR: white;
}
.STYLE1 A:active,.STYLE1 A:visited,.STYLE1 A:link {
	COLOR: white;
	TEXT-DECORATION: none
}
.STYLE1 a:hover{
	COLOR: red;
}
-->
</style>
<script type="text/javascript">
function SelectALL(field){
	var checkBoxList = document.getElementsByName("checkbox2");
	
	for (var i=0;i<checkBoxList.length;i++){
		if(field.checked){
			checkBoxList[i].checked = true;
		}else{
			checkBoxList[i].checked = false;
		}
	}
}

function del(){
	//判断有哪些checkbox被选中
	var checkBoxList = document.getElementsByName("checkbox2");
	
	var url ="ArticleServlet?method=del";
	
	var checkIds = [];
	for (var i =0;i<checkBoxList.length;i++){
		if(checkBoxList[i].checked){
			checkIds[checkIds.length] = checkBoxList[i].value;
		}
	}
	
	for (var i = 0;i<checkIds.length;i++){
			url = url +"&id=" +checkIds[i];
	}
	window.location = url;

}

function selectPagesize(field){
	window.location = document.getElementById("firstPage").href+"&pagesize="+field.value;
}
</script>
</head>
<body>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="30"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="24" bgcolor="#353c44"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="6%" height="19" valign="bottom"><div align="center"><img src="images/tb.gif" width="14" height="14" /></div></td>
                <td width="94%" valign="bottom"><span class="STYLE1"> 网站文章信息列表</span></td>
              </tr>
            </table></td>
            <td><div align="right"><span class="STYLE1">
             &nbsp;&nbsp;<img src="images/add.gif" width="10" height="10" /> <a href="ArticleServlet?method=addInput">添加</a>   
             &nbsp;&nbsp;<img src="images/edit.gif" width="10" height="10" /> <a href="#">发布</a>
             &nbsp; <img src="images/del.gif" width="10" height="10" /> <a href="javascript:del()">删除</a>    &nbsp;&nbsp;   &nbsp;
             </span><span class="STYLE1"> &nbsp;</span></div></td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
  <tr>
  	<td>
  	<form action="ArticleServlet" method="POST">
  		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
  				<td width="500" style="text-align: right; word-break:break-all;">按文章标题进行搜索:<input type="text" name="title" value="${param.title}"><input type="submit" name="submit" value="查询"></td>
  			</tr>
  		</table>
  	</form>	
  	</td>
  </tr>
  <tr>
    <td>
    <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#a8c7ce">
      <tr>
        <td width="4%" height="20" bgcolor="d3eaef" class="STYLE10"><div align="center">
          <input type="checkbox" name="checkbox" id="checkbox" onclick = "SelectALL(this)" />
        </div></td>
        <td width="100" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">文章标题</span></div></td>
        <td width="50" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">文章来源</span></div></td>
        <td width="50" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">点击量</span></div></td>
        <td width="50" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">留言数</span></div></td>
        <td width="70" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">创建时间</span></div></td>
        <td width="70" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">更新时间</span></div></td>
        <td width="70" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">发布时间</span></div></td>
        <td width="100" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">基本操作</span></div></td>
      </tr>
      <c:if test="${not empty pv.datas}">
      <c:forEach items="${pv.datas}" var="a">
      <tr>
        <td height="20" bgcolor="#FFFFFF"><div align="center">
          <input type="checkbox" name="checkbox2"  value = "${a.id}"/>
        </div></td>
        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center"><a href="ArticleServlet?method=updateInput&id=${a.id}" title="点击查看和编辑文章">${a.title}</a></div></td>
        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">http://www.leadfar.org</div></td>
        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">20</div></td>
        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">5</div></td>
        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">2010-07-19</div></td>
        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">2010-07-19</div></td>
        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">2010-07-19</div></td>
        <td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE21">
        <a href="#" title="点击发布文章">发布</a> | 
        <a href="ArticleServlet?method=del&id=${a.id}" title="点击删除文章">删除</a> |
        <a href="ArticleServlet?method=updateInput&id=${a.id}" title="点击编辑文章">编辑</a>
        </div></td>
      </tr>
      </c:forEach>
      </c:if>
    </table></td>
  </tr>
  <tr>
    <td height="30">
<pg:pager url ="ArticleServlet" items="${pv.total}" maxPageItems="${pagesize}" export="currentPageNumber=pageNumber">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="33%"><div align="left"><span class="STYLE22">&nbsp;&nbsp;&nbsp;&nbsp;共有<strong>${pv.total}</strong> 条记录，
        																				当前第<strong> ${currentPageNumber}</strong> 页，
        																				共<pg:last> <strong>${pageNumber}</strong></pg:last> 页</span></div></td>
        <td width="67%" align=right vAlign="center" noWrap>
			  		<pg:param name="title"/>
			  		<pg:first ><a id="firstPage" href="${pageUrl}">首页</a></pg:first>
			  		<pg:prev><a href="${pageUrl}">上一页</a></pg:prev>
			  		<pg:pages>
			  			<c:choose>
			  				<c:when test="${currentPageNumber eq pageNumber}">
			  					<font color="red">${pageNumber}</font>
			  				</c:when>
			  				<c:otherwise>
			  					<a href="${pageUrl}">${pageNumber}</a>
			  				</c:otherwise>
			  			</c:choose>	
			  		</pg:pages>
			  		<pg:next><a href="${pageUrl}">下一页</a></pg:next>
			  		<pg:last><a href="${pageUrl}">尾页</a></pg:last>
				<select name="pagesize" onchange="selectPagesize(this)" >
				<c:forEach begin="5" end="50" step="5" var="i">
					<option value="${i}" <c:if test="${i eq pagesize}"> selected</c:if>>
						${i}
					</option>
				</c:forEach>
				</select>
      </td>
      </tr>
    </table>
</pg:pager>
    </td>
  </tr>
</table>
</body>
</html>

