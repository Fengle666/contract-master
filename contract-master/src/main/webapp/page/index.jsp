<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

    <title>合同管理信息系统</title>
	<%@ include file="common.jsp" %>
    <style>
        .table th,.table td{ text-align:center;vertical-align:middle!important;}
    </style>
</head>
<body>

<div class="wrapper">
<div class="sidebar" data-color="orange" data-image="${ pageContext.request.contextPath }/picture/full-screen-image-1.jpg">
     <div class="logo">
         <a href="#" class="logo-text">合同管理信息系统</a>
     </div>
     <div class="logo logo-mini">
         <a href="#" class="logo-text">
             Ct
         </a>
     </div>

     <div class="sidebar-wrapper">
         <ul class="nav">
             <li>
               <a href="#" onclick="setMenu('${ pageContext.request.contextPath }/page/user_info.jsp')">
                   <i class="pe-7s-angle-right"></i> 个人信息
               </a>
           </li> 
          
             <!-- 管理员权限 -->
             <% if("1".equals(session.getAttribute("roleid"))){%>
              <li>
	               <a href="#" onclick="setMenu('${ pageContext.request.contextPath }/page/log_list.jsp')">
	                   <i class="pe-7s-angle-right"></i> 操作日志
	               </a>
	           </li> 
             	<li>
                 <a data-toggle="collapse" href="#usersExamples">
                     <i class="pe-7s-angle-right"></i>
                     <p>人员管理
                         <b class="caret"></b>
                     </p>
                 </a>
                 <div class="collapse" id="usersExamples">
                     <ul class="nav">
                         <li><a onclick="setMenu('${ pageContext.request.contextPath }/page/user_list.jsp')" href="#">人员列表</a></li>
                         <li><a onclick="setMenu('${ pageContext.request.contextPath }/page/user_add.jsp')" href="#">增加人员信息</a></li>
                     </ul>
                 </div>
             </li>
             <li>
                 <a data-toggle="collapse" href="#typesExamples">
                     <i class="pe-7s-angle-right"></i>
                     <p>合同模板管理
                         <b class="caret"></b>
                     </p>
                 </a>
                 <div class="collapse" id="typesExamples">
                     <ul class="nav">
                      	 <li><a onclick="setMenu('${ pageContext.request.contextPath }/page/template_list_lib.jsp')" href="#">合同模板档案库</a></li>
                         <li><a onclick="setMenu('${ pageContext.request.contextPath }/page/template_list.jsp')" href="#">合同模板列表</a></li>
                         <li><a onclick="setMenu('${ pageContext.request.contextPath }/page/template_add.jsp')" href="#">增加合同模板</a></li>
                     </ul>
                 </div>
             </li>
             <li>
                 <a data-toggle="collapse" href="#suppliersExamples">
                     <i class="pe-7s-angle-right"></i>
                     <p>合同管理
                         <b class="caret"></b>
                     </p>
                 </a>
                 <div class="collapse" id="suppliersExamples">
                     <ul class="nav">
                         <li><a onclick="setMenu('${ pageContext.request.contextPath }/page/contract_list.jsp')" href="#">合同列表</a></li>
                         <li><a onclick="setMenu('${ pageContext.request.contextPath }/page/contract_add.jsp')" href="#">增加合同信息</a></li>
                     </ul>
                 </div>
             </li>
             <li>
                 <a data-toggle="collapse" href="#ordersExamples">
                     <i class="pe-7s-angle-right"></i>
                     <p>合同类别管理
                         <b class="caret"></b>
                     </p>
                 </a>
                 <div class="collapse" id="ordersExamples">
                     <ul class="nav">
                         <li><a onclick="setMenu('${ pageContext.request.contextPath }/page/type_list.jsp')" href="#">合同类别列表</a></li>
                         <li><a onclick="setMenu('${ pageContext.request.contextPath }/page/type_add.jsp')" href="#">增加合同类别</a></li>
                     </ul>
                 </div>
             </li>
              <li>
                 <a data-toggle="collapse" href="#stuffsExamples">
                     <i class="pe-7s-angle-right"></i>
                     <p>审批管理
                         <b class="caret"></b>
                     </p>
                 </a>
                 <div class="collapse" id="stuffsExamples">
                     <ul class="nav">
                         <li><a onclick="setMenu('${ pageContext.request.contextPath }/page/approval_list.jsp')" href="#">审批记录</a></li>
                         <li><a onclick="setMenu('${ pageContext.request.contextPath }/page/template_list_admin.jsp')" href="#">待审批合同模板列表</a></li>
                     </ul>
                 </div>
             </li>
              
             <%} %>
             <!-- 合同录入人员权限 -->
             <% if("2".equals(session.getAttribute("roleid"))){%>
             <li>
                 <a data-toggle="collapse" href="#suppliersExamples">
                     <i class="pe-7s-angle-right"></i>
                     <p>合同管理
                         <b class="caret"></b>
                     </p>
                 </a>
                 <div class="collapse" id="suppliersExamples">
                     <ul class="nav">
                         <li><a onclick="setMenu('${ pageContext.request.contextPath }/page/contract_list.jsp')" href="#">合同列表</a></li>
                         <li><a onclick="setMenu('${ pageContext.request.contextPath }/page/contract_add.jsp')" href="#">增加合同信息</a></li>
                     </ul>
                 </div>
             </li>
             <li>
                 <a data-toggle="collapse" href="#ordersExamples">
                     <i class="pe-7s-angle-right"></i>
                     <p>合同类别管理
                         <b class="caret"></b>
                     </p>
                 </a>
                 <div class="collapse" id="ordersExamples">
                     <ul class="nav">
                         <li><a onclick="setMenu('${ pageContext.request.contextPath }/page/type_list.jsp')" href="#">合同类别列表</a></li>
                         <li><a onclick="setMenu('${ pageContext.request.contextPath }/page/type_add.jsp')" href="#">增加合同类别</a></li>
                     </ul>
                 </div>
             </li>
             <li>
                 <a data-toggle="collapse" href="#earlywarningsExamples">
                     <i class="pe-7s-angle-right"></i>
                     <p>合同提醒信息管理
                         <b class="caret"></b>
                     </p>
                 </a>
                 <div class="collapse" id="earlywarningsExamples">
                     <ul class="nav">
                     	 <li><a onclick="setMenu('${ pageContext.request.contextPath }/page/earlywarning_list_run.jsp')" href="#">提醒</a></li>
                         <li><a onclick="setMenu('${ pageContext.request.contextPath }/page/earlywarning_list.jsp')" href="#">合同提醒信息列表</a></li>
                         <li><a onclick="setMenu('${ pageContext.request.contextPath }/page/earlywarning_add.jsp')" href="#">增加合同提醒信息</a></li>
                     </ul>
                 </div>
             </li>
             <li>
                 <a data-toggle="collapse" href="#typesExamples">
                     <i class="pe-7s-angle-right"></i>
                     <p>合同模板管理
                         <b class="caret"></b>
                     </p>
                 </a>
                 <div class="collapse" id="typesExamples">
                     <ul class="nav">
                     	 <li><a onclick="setMenu('${ pageContext.request.contextPath }/page/template_list_lib.jsp')" href="#">合同模板档案库</a></li>
                         <li><a onclick="setMenu('${ pageContext.request.contextPath }/page/template_list.jsp')" href="#">合同模板列表</a></li>
                         <li><a onclick="setMenu('${ pageContext.request.contextPath }/page/template_add.jsp')" href="#">增加合同模板</a></li>
                     </ul>
                 </div>
             </li>
             <%} %>
             <!-- 审批人员 -->
             <% if("3".equals(session.getAttribute("roleid"))){%>
             <li>
                 <a data-toggle="collapse" href="#stuffsExamples">
                     <i class="pe-7s-angle-right"></i>
                     <p>审批管理
                         <b class="caret"></b>
                     </p>
                 </a>
                 <div class="collapse" id="stuffsExamples">
                     <ul class="nav">
                         <li><a onclick="setMenu('${ pageContext.request.contextPath }/page/approval_list.jsp')" href="#">审批记录</a></li>
                         <li><a onclick="setMenu('${ pageContext.request.contextPath }/page/template_list_admin.jsp')" href="#">待审批合同模板列表</a></li>
                     </ul>
                 </div>
             </li>
             <%} %>
             <li>
                 <a data-toggle="collapse" href="#statisticsExamples">
                     <i class="pe-7s-angle-right"></i>
                     <p>统计管理
                         <b class="caret"></b>
                     </p>
                 </a>
                 <div class="collapse" id="statisticsExamples">
                     <ul class="nav">
                         <li><a onclick="setMenu('${ pageContext.request.contextPath }/page/statistics.jsp')" href="#">合同统计</a></li>
                     </ul>
                 </div>
             </li>
         </ul>
     </div>
</div>

    <div class="main-panel">
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <div class="navbar-minimize">
                    <button id="minimizeSidebar" class="btn btn-warning btn-fill btn-round btn-icon">
                        <i class="fa fa-ellipsis-v visible-on-sidebar-regular"></i>
                        <i class="fa fa-navicon visible-on-sidebar-mini"></i>
                    </button>
                </div>
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <!-- <a class="navbar-brand" href="#">Bootstrap Table</a>-->
                </div>
                <div class="collapse navbar-collapse">

                    <form class="navbar-form navbar-left navbar-search-form" role="search">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-search"></i></span>
                            <input type="text" value="" class="form-control" placeholder="Search...">
                        </div>
                    </form>

                    <ul class="nav navbar-nav navbar-right">


                        <li class="dropdown dropdown-with-icons">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                <i class="fa fa-user"></i>
                                <p class="hidden-md hidden-lg">
                                    个人信息
                                    <b class="caret"></b>
                                </p>
                            </a>
                            <ul class="dropdown-menu dropdown-with-icons">
                               <li>
                                    <a href="#" onclick="setMenu('${ pageContext.request.contextPath }/page/user_info.jsp')">
                                        <i class="fa fa-archive"></i> 个人信息
                                    </a>
                                </li> 
                                <li>
                                    <a href="#" onclick="setMenu('${ pageContext.request.contextPath }/user/findByIdToPassword?id=<%=session.getAttribute("userid")%>')">
                                        <i class="pe-7s-help1"></i> 修改密码
                                    </a>
                                </li> 
                                <li>
                                    <a href="${ pageContext.request.contextPath }/user/loginout" class="text-danger" >
                                        <i class="pe-7s-close-circle"></i>
                                        退出
                                    </a>
                                </li>
                            </ul>
                        </li>

                    </ul>

                </div>
            </div>
        </nav>

        <div class="content">
        	 <div class="container-fluid">
           <!-- <iframe id="iframe-page-content" src="${ pageContext.request.contextPath }/page/user_list.jsp" width="100%" frameborder="0" border="0" marginwidth="0" marginheight=" 0" scrolling="no" allowtransparency="yes"></iframe> -->
				<iframe src="${ pageContext.request.contextPath }/page/user_info.jsp" scrolling="no" onload="setIframeHeight(this)" id="bodyCenter" name="workspace" frameborder="0" width="100%" height="50%" style="min-height:800px;" ></iframe>
			</div>
        </div>


    </div>
</div>


</body>

<script type="text/javascript">
/**iframe 自适应大小函数 */
function setIframeHeight(iframe) {
    if (iframe) {
	    var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
	    if (iframeWin.document.body) {
	       iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
	    }
    }
};
/** 设置ifrema 切换路径函数 */
function setMenu(menusrc){
	$("#bodyCenter").attr("src",menusrc);
}
/** 动态切换导航栏 */
$("ul li a").bind("click",function(){
	$("ul li").removeClass("active");
	$(this).parent().addClass("active");

});

</script>

<script>


</script>

</html>
