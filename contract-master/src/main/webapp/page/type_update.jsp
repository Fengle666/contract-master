<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="common.jsp"%>

<div class="row">
	<div class="col-md-12">
		<div class="typed">
			<form id="allInputsFormValidation" class="form-horizontal" 
				method="post" novalidate="">
				<div class="content">
					<legend>修改合同类别</legend>
					<input type="hidden" name="id" value="${type.id }" />
					<input type="hidden" name="state" value="${type.state }" />
					<fieldset>
						<div class="form-group">
							<label class="col-sm-4 control-label">合同类别名称<star>*</star></label>
							<div class="col-sm-6">
								<input class="form-control" type="text" name="type" value="${type.type }"
									required="required" />
							</div>
						</div>
					</fieldset>

				</div>
				<div class="footer text-center">
					<button type="submit" class="btn btn-info btn-fill">修改</button>
					<button type="reset" class="btn btn-info btn-fill">重置</button>
				</div>
			</form>

		</div>
		<!--  end typed  -->
	</div>
	<!-- end col-md-12 -->
</div>
<!-- end row -->



<script type="text/javascript">


$().ready(function(){
	
    //重写验证规则，设置返回中文
    $.validator.messages.required = jQuery.validator.format("请填写必填字段");
	
    $('#allInputsFormValidation').validate({
    	submitHandler: function(form) { //通过之后回调
    		//进行ajax传值
    		$.ajax({
    			data:$("#allInputsFormValidation").serialize(),
            	dataType:"text",
        		type:"post",
        		url:"${ pageContext.request.contextPath }/type/typeupdate",
        		success:function(data){
        			console.log(data);
        			if(data == "1"){
        				window.location.href = "${ pageContext.request.contextPath }/page/type_list.jsp";
        			}else{
        				alert(data);
        			}
        		},
    		});
    	},
    });
	
    
});



</script>

<script>


</script>

</html>
