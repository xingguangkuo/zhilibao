<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>部门设置</title>
<script src="${pageContext.request.contextPath}/ui/js/jquery-1.4.2.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/ui/css/style_cn.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/ui/js/win.js" type="text/javascript"></script>
<script type="text/javascript">
 function forward(strURL){
     window.location=strURL;
 }

 function deleteIds(){
	    document.forms[1].submit();
 }
</script>
</head>
<body>		
<div class="mtitle">
	<div class="mtitle-row">&nbsp;</div>
	<span class="menu_selected">部门设置</span>
</div>
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tabForm">
  <tr>
    <th class="th_head">
		<div id="menuArrow1" style="background:url(${pageContext.request.contextPath}/ui/images/down.gif) no-repeat center;float:left;">&nbsp;</div>
		<div id="menuTitle1" style="font-weight:bold">部门搜索</div>
	</th>
    <th class="th_head" >
	</th>
  </tr>
  <tr>
  <td colspan="2">
<form name="form1" method="post" action="${pageContext.request.contextPath}/sys/sysUserGroupAction_list.do">
<table width="100%" border="0" cellspacing="0" cellpadding="0" name="base" id="base">
	<tr>
    	<td width="38%" nowrap="nowrap">部门名称：
			<input name="name" type="text" id="name" value="" style="width:140px">
    	</td>
  	    <td width="39%" nowrap="nowrap">&nbsp;</td>
  	    <td width="23%" align="center">
		<div class="control">
		<button type='button' class='button' onMouseOver="this.className='button_over';" 
		                                     onMouseOut="this.className='button';"  onClick="document.forms[0].submit()">
		       <img src="${pageContext.request.contextPath}/ui/images/button/sousuo.png" border='0' align='absmiddle'>&nbsp;搜索</button>
		<button type='button' class='button' onMouseOver="this.className='button_over';" onMouseOut="this.className='button';"  
		        onClick="forward('group.do?method=search')">
		        <img src="${pageContext.request.contextPath}/ui/images/button/qingkong.png" border='0' align='absmiddle'>&nbsp;清空</button>
		</div>
		</td>
	</tr>
</table>
</form>
	  </td>
  </tr>
</table>
<br>
<h3><img src="${pageContext.request.contextPath}/ui/images/menu/khlb.png" border="0">&nbsp;部门列表</h3>
<div class="control">

	<button type='button' class='button' onMouseOver="this.className='button_over';" onMouseOut="this.className='button';"  
	        onClick="forward('${pageContext.request.contextPath}/sys/sysUserGroupAction_addLink.do')">
	        <img src="${pageContext.request.contextPath}/ui/images/button/xinjian.png" border='0' align='absmiddle'>&nbsp;新建</button>


	<button type='button' class='button' onMouseOver="this.className='button_over';" onMouseOut="this.className='button';"  
	        onClick="deleteIds()">
	        <img src="${pageContext.request.contextPath}/ui/images/button/shanchu.png" border='0' align='absmiddle'>&nbsp;删除</button>

</div>
<!-- list -->
<div class="border">

<form name="form2" method="post" action="${pageContext.request.contextPath}/sys/sysUserGroupList.do">
<input type="hidden" name="method" value="delete">
<table width="100%" border="0" cellspacing="0" cellpadding="0" id="PowerTable" class="PowerTable">
	<!-- title -->
	<tr>
    	<td width="5%" class="listViewThS1">
   	    <input type="checkbox" id="checkall" name="checkall" value="" class="checkbox" onClick="checkAll()"></td>
  	    <td width="20%" class="listViewThS1">部门名称</td>
  	    <td width="15%" class="listViewThS1">部门负责人</td>
  	    <td width="50%" class="listViewThS1">部门职能</td>
        <td width="10%" class="listViewThS1">人员设置</td>
	</tr>	
			<tr>
		    	<td>
		    	  <input type="checkbox" name="ids" value="12" class="checkbox" onClick="changeCheckCount();">
				</td>
		  	    <td><a href="${pageContext.request.contextPath}/sys/sysUserGroupAction_edit.do">市场部</a></td>
				<td>xxxx</td>
				<td>xxx</td>
				<td><a href="javascript:OpenWin('${pageContext.request.contextPath}/sys/group/usersInGroup.jsp?id=12')">设置</a></td>
			</tr>
		 
			<tr>
		    	<td>
		    	  <input type="checkbox" name="ids" value="13" class="checkbox" onClick="changeCheckCount();">
				</td>
		  	    <td><a href="${pageContext.request.contextPath}/sys/sysUserGroup.do?method=edit&id=13">财务部</a></td>
				<td>王天</td>
				<td></td>
				<td><a href="javascript:OpenWin('${pageContext.request.contextPath}/sys/group/usersInGroup.jsp?id=13')">设置</a></td>
			</tr>
		 
			<tr>
		    	<td>
		    	  <input type="checkbox" name="ids" value="14" class="checkbox" onClick="changeCheckCount();">
				</td>
		  	    <td><a href="${pageContext.request.contextPath}/sys/sysUserGroup.do?method=edit&id=14">企划中心</a></td>
				<td>王天</td>
				<td></td>
				<td><a href="javascript:OpenWin('${pageContext.request.contextPath}/sys/group/usersInGroup.jsp?id=14')">设置</a></td>
			</tr>
			
</table>
</form>
</div>
</body>
<script type="text/javascript">
   function changeCheckCount(){
       var count=0;
	   $("input[type='checkbox'][name='ids']").each(function(index,data){
            if(this.checked){
            	count++;  
            }
	   });
       if(count== $("input[type='checkbox'][name='ids']").length){
    	   $("#checkall").attr("checked","checked");
       }else{
    	   $("#checkall").attr("checked",null);
       }
   }
   
   function  checkAll(){
      if($("#checkall")[0].checked){
    	  $("input[type='checkbox'][name='ids']").attr("checked","checked");
      }else{
    	  $("input[type='checkbox'][name='ids']").attr("checked",null);
      }
   }
 </script>
</html>