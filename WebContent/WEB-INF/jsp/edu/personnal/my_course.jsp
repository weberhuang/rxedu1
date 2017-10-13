<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/jsp/frame/comm_css_js.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/frame/header.jsp"%>

	<script type="text/javascript">
	
	var course = {
			teachers:false,
			teacherFormatter :function(value,row,index){
				if(!course.teachers){
					$.ajax({url:'${ctx}/teacher/get?id=course.teacher',async:false,dataType:'json',
						success:function(data){
							course.teachers = data;
					}});
				}
				return $.ad.getName(value,course.teachers,'value');
			},
			
			teachers:null,
			teacherFormatter:function(value){
				if(!course.teachers){
					course.teachers = $('#course_teacher').combobox("getData");
				}
				return $.ad.getName(value,course.teachers);
			}		
	};
	


	</script>
	<div id="course_tb" style="padding: 5px; height: auto">
		<div>
		<form id="report_query_form">
				 
				课程名称: <input class="easyui-textbox" style="width: 100px"
					name="name_like_string">
					
	                               授课老师：
						<input class="easyui-combobox" name="teacher_in_string" id="course_teacher"
						style="width: 100px"
						data-options="
		                    url:'${ctx }/teacher/list?status=s',
		                    method:'get',
		                    valueField:'id',
		                    textField:'name',
		                    panelHeight:'auto',
	                    	multiple:true
	                    ">
	              
				  <a
					href="javascript:$.ad.gridQuery('report_query_form','report_grid')"
					class="easyui-linkbutton" iconCls="icon-search"><s:message
						code="comm.query" />
			      </a>
			      <a href="javascript:void(0)"
						class="easyui-linkbutton" onclick="$.ad.clearForm('report_query_form')"
						style="width: 80px"><s:message code="comm.clear" /></a> 
			     <input type="hidden" name="valid" id="valid"  value ='1'/>
			     
			</form>
		</div>
	</div>

	<table class="easyui-datagrid" id="report_grid" style="width: 920px;"
		data-options="rownumbers:true,singleSelect:false,pagination:true,multiSort:true,selectOnCheck:true,
				url:'${ctx }/course/list?type=c',method:'post',toolbar:'#report_tb'">
		<thead>
		    <tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'name',width:120,sortable:'true'"><font>课程名称</font></th>
				<th data-options="field:'hour',width:60,sortable:'true'"><font>课时 </font></th>
				<th data-options="field:'credit',width:60,sortable:'true'"><font>学分</font></th>
				<th data-options="field:'teacher',width:80,sortable:'true',formatter:course.teacherFormatter"><font>授课老师</font></th>
				<th data-options="field:'descr',width:120,sortable:'true'"><font>课程说明</font></th>			
				<th data-options="field:'createTime',width:140,sortable:'true'"><font>开始时间</font></th>
				<th data-options="field:'createTime',width:140,sortable:'true'"><font>完成时间</font></th>
			</tr>
		</thead>
	</table>	
	
	<%@ include file="/WEB-INF/jsp/frame/footer.jsp"%>
</body>
</html>