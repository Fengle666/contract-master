<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="common.jsp"%>

<div class="row">
	<div class="col-md-12">
		<div class="card">
			<form id="allInputsFormValidation" class="form-horizontal" 
				method="post" novalidate="">
				<div class="content">
					<legend>修改密码</legend>
					<input type="hidden" name="id" value="${user.id }" />
					<input type="hidden" name="isDelete" value="${user.isDelete }" />
					<input  type="hidden" name="name" value="${user.name }" />
					<input  type="hidden" name="phone" value="${user.phone }" />
					<input  type="hidden" name="roleId" value="${user.roleId }" />

					<fieldset>
						<div class="form-group">
							<label class="col-sm-4 control-label">旧密码<star>*</star></label>
							<div class="col-sm-6">
								<input class="form-control" value=""
									 type="password" required="true" isOldPassword="true" />
							</div>
						</div>
					</fieldset>
					<fieldset>
						<div class="form-group">
							<label class="col-sm-4 control-label">新密码<star>*</star></label>
							<div class="col-sm-6">
								<input class="form-control" name="password" 
									id="registerPassword" type="password" required="true" />
							</div>
						</div>
					</fieldset>
					<fieldset>
						<div class="form-group">
							<label class="col-sm-4 control-label">确认密码<star>*</star></label>
							<div class="col-sm-6">
								<input class="form-control"  id="registerPasswordConfirmation"
                                               type="password"
                                               required="true"
                                               equalTo="#registerPassword"/>
							</div>
						</div>
					</fieldset>


				</div>
				<div class="footer text-center">
					<button type="submit" class="btn btn-info btn-fill">修改</button>
					<button type="button" id="reset" class="btn btn-info btn-fill">重置密码</button>
				</div>
			</form>

		</div>
		<!--  end card  -->
	</div>
	<!-- end col-md-12 -->
</div>
<!-- end row -->



<script type="text/javascript">


$().ready(function(){
	
	$("#reset").click(function(){
		$.ajax({
			data:$("#allInputsFormValidation").serialize(),
        	dataType:"text",
    		type:"post",
    		url:"${ pageContext.request.contextPath }/user/resetPassword",
    		success:function(data){
    			console.log(data);
    			if(data == "1"){
    				alert("修改成功");
    				window.parent.location.href = "${ pageContext.request.contextPath }/user/loginout";
    				//window.location.href = "${ pageContext.request.contextPath }/user/loginout";
    			}else{
    				alert(data);
    			}
    		},
		});
	});
	
    //重写验证规则，设置返回中文
    $.validator.messages.required = jQuery.validator.format("请填写必填字段");
    $.validator.messages.equalTo = jQuery.validator.format("两次密码输入不一致");
    // 手机号码验证
    jQuery.validator.addMethod("isMobile", function(value, element) {
        var length = value.length;
        return (length == 11 && /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(value));
    }, "请正确填写您的手机号码。");
    
    // 手机号码验证
    jQuery.validator.addMethod("isOldPassword", function(value, element) {
        return '${user.password }' == value;
    }, "旧密码错误。");
	
    
    
    $('#allInputsFormValidation').validate({
    	submitHandler: function(form) { //通过之后回调
    		//进行ajax传值
    		$.ajax({
    			data:$("#allInputsFormValidation").serialize(),
            	dataType:"text",
        		type:"post",
        		url:"${ pageContext.request.contextPath }/user/userupdate",
        		success:function(data){
        			console.log(data);
        			if(data == "1"){
        				alert("修改成功");
        				window.parent.location.href = "${ pageContext.request.contextPath }/user/loginout";
        				//window.location.href = "${ pageContext.request.contextPath }/page/user_list.jsp";
        			}else{
        				alert(data);
        			}
        		},
        		error:function(){
        			alert("网络连接错误");
        		}
    		});
    	},
    });
	
    
});



</script>

<script>


</script>

</html>
