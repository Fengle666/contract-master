<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="common.jsp"%>

<div class="row">
	<div class="col-md-12">
		<div class="card">
			<form id="allInputsFormValidation" class="form-horizontal" 
				 novalidate="">
				<div class="content">
					<legend>用户个人信息</legend>
					<input type="hidden" name="id" value="" />
					<input type="hidden" name="state" value="" />
					<fieldset>
						<div class="form-group">
							<label class="col-sm-4 control-label">用户名称<star>*</star></label>
							<div class="col-sm-6">
								<input class="form-control" type="text" name="name"
									 />
							</div>
						</div>
					</fieldset>

					<fieldset>
						<div class="form-group">
							<label class="col-sm-4 control-label">手机号<star>*</star></label>
							<div class="col-sm-6">
								<input class="form-control" type="text" name="phone"
									required="required" />
							</div>
						</div>
					</fieldset>

					<fieldset>
						<div class="form-group">
							<label class="col-sm-4 control-label">密码<star>*</star></label>
							<div class="col-sm-6">
								<input class="form-control" name="password"
									id="registerPassword" type="text"  />
							</div>
						</div>
					</fieldset>

					<fieldset>
						<div class="form-group">
							<label class="col-sm-4 control-label">选择用户角色<star>*</star></label>
							<div class="col-sm-6">
								<select class="form-control" id="myselect" required="required" onfocus="this.defaultIndex=this.selectedIndex;" onchange="this.selectedIndex=this.defaultIndex;" name="roleId">
								</select>
							</div>
							<!--<div class="col-sm-4"><code>url="true"</code></div>-->
						</div>
					</fieldset>


				</div>
				<!-- <div class="footer text-center">
					<button type="submit" class="btn btn-info btn-fill">修改</button>
					<button type="reset" class="btn btn-info btn-fill">重置</button>
					
				</div> -->
			</form>

		</div>
		<!--  end card  -->
	</div>
	<!-- end col-md-12 -->
</div>
<!-- end row -->



<script type="text/javascript">


    $().ready(function(){
		//查询个人信息页面
        $.ajax({
        	dataType:"json",
        	url: "${ pageContext.request.contextPath }/user/userInfo",
        	type:"post",
        	success: function(data){
        		$("input[name='id']").val(data.id);
        		$("input[name='state']").val(data.state);
        		$("input[name='name']").val(data.name);
        		$("input[name=phone]").val(data.phone);
        		$("input[name=password]").val(data.password);
        		if(data.roleId == 1){
        			 $("<option value='1' >管理员</option>").appendTo("#myselect");//构造html元素并插入
        		}else if(data.roleId == 2){
        			$("<option value='2'>合同录入人员</option>").appendTo("#myselect");//构造html元素并插入
        		}else if(data.roleId == 3){
        			 $("<option value='3' >审批人员</option>").appendTo("#myselect");//构造html元素并插入
        		}
        	}
        });
		
        //重写验证规则，设置返回中文
        $.validator.messages.required = jQuery.validator.format("请填写必填字段");
        // 手机号码验证
        jQuery.validator.addMethod("isMobile", function(value, element) {
            var length = value.length;
            return (length == 11 && /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(value));
        }, "请正确填写您的手机号码。");
        
		
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
            				window.location.href = "${ pageContext.request.contextPath }/page/user_info.jsp";
            			}else{
            				alert(data);
            			}
            		},
            		error:function(){
            			alert("通信失败!");
            		}
        		});
        	},
        });
		
        
    });


</script>

<script>


</script>

</html>
