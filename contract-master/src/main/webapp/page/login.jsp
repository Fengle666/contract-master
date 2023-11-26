<%@ page language="java" import="java.util.*" pageEncoding="utf-8" isELIgnored="false" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base>
    
    <title>合同管理信息系统</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<%@ include file="common.jsp"%>
  </head>
  
  <body>
  
 <nav class="navbar navbar-transparent navbar-absolute">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">合同管理信息系统</a>
        </div>
    </div>
</nav>
  
    <div class="wrapper wrapper-full-page">
    <div class="full-page login-page"  data-color="orange" data-image="${ pageContext.request.contextPath }/picture/full-screen-image-1.jpg">   
        
    <!--   you can change the color of the filter page using: data-color="blue | azure | green | orange | red | purple" -->
        <div class="content">
            <div class="container">
                <div class="row">                   
                    <div class="col-md-4 col-sm-6 col-md-offset-4 col-sm-offset-3">
                        <form id="myForm" novalidate="">
                            
                        <!--   if you want to have the card without animation please remove the ".card-hidden" class   -->
                            <div class="card card-hidden">
                                <div class="header text-center">合同管理信息系统</div>
                                <div class="content">
                                    <div class="form-group">
                                        <label>手机号</label>
                                        <input type="text" isMobile="true" required="required" name="phone" placeholder="请输入手机号" class="form-control">
                                    </div>
                                    <div class="form-group">
                                        <label>密码</label>
                                        <input type="password" required="required" name="password" placeholder="请输入密码" class="form-control">
                                    </div>                                    
                                    <div class="form-group">
                                        <label class="checkbox">
                                            <input type="checkbox" data-toggle="checkbox" value="">
                                            记住我
                                        </label>    
                                    </div>
                                </div>
                                <div class="footer text-center">
                                	<button type="submit" class="btn btn-primary btn-fill btn-wd">登录</button>
                                </div>
                            </div>
                                
                        </form>
                                
                    </div>                    
                </div>
            </div>
        </div>
    	

    </div>                             
       
</div>

  </body>
  <script type="text/javascript">
        $().ready(function(){
            lbd.checkFullPageBackgroundImage();
            setTimeout(function(){
                // after 1000 ms we add the class animated to the login/register card
                $('.card').removeClass('card-hidden');
            }, 700);
            
            //重写验证规则，设置返回中文
            $.validator.messages.required = jQuery.validator.format("请填写必填字段");
            $.validator.messages.equalTo = jQuery.validator.format("两次密码输入不一致");
         	// 手机号码验证
            jQuery.validator.addMethod("isMobile", function(value, element) {
                var length = value.length;
                return (length == 11 && /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(16[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(value));
            }, "请正确填写您的手机号码。");
            

            $('#myForm').validate({
            	submitHandler: function(form) { //通过之后回调
            		//进行ajax传值
            		$.ajax({
                		data:$("#myForm").serialize(),
                		dataType:"text",
                		type:"post",
                		url:"${ pageContext.request.contextPath }/user/login",
                		success:function(data){
                			console.log(data);
                			if(data == "1"){
                				window.location.href = "${ pageContext.request.contextPath }/page/index.jsp";
                			}else{
                				//$("#phoneText").val(data);
                				alert(data);
                			}
                		},
                		error: function(){
                			alert("ajax错误");
                		}
                	});
            	}
            });
            
            
        });
    </script>
</html>
