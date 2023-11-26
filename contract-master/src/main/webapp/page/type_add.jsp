<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="common.jsp" %>

<div class="row">
    <div class="col-md-12">
        <div class="card">
            <form id="allInputsFormValidation" class="form-horizontal" novalidate="">
                <div class="content">
                    <legend>增加合同类别信息</legend>

                    <fieldset>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">合同类别名称<star>*</star></label>
                            <div class="col-sm-6">
                                <input class="form-control"
                                       type="text"
                                       name="type"
                                       required="required"
                                />
                            </div>
                        </div>
                    </fieldset>

                </div>
                <div class="footer text-center">
                    <button type="submit" id="mysubmit" class="btn btn-info btn-fill">增加</button>
                    <button type="reset" class="btn btn-info btn-fill">重置</button>
                </div>
            </form>

        </div><!--  end typed  -->
    </div> <!-- end col-md-12 -->
</div> <!-- end row -->

        
<script type="text/javascript">


    $().ready(function(){

        //重写验证规则，设置返回中文
        $.validator.messages.required = jQuery.validator.format("请填写必填字段");
        $.validator.messages.equalTo = jQuery.validator.format("两次密码输入不一致");
		
        $('#allInputsFormValidation').validate({
        	submitHandler: function(form) { //通过之后回调
        		//进行ajax传值
        		$.ajax({
        			data:$("#allInputsFormValidation").serialize(),
                	dataType:"text",
            		type:"post",
            		url:"${ pageContext.request.contextPath }/type/typeadd",
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
        
		
        
       /*  $("#mysubmit").bind("click",function(){
        	$.ajax({
        		data:$("#allInputsFormValidation").serialize(),
        		dataType:"text",
        		type:"post",
        		url:"${ pageContext.request.contextPath }/user/useradd",
        		
        		success:function(data){
        			console.log(data);
        			if(data == "1"){
        				window.location.href = "${ pageContext.request.contextPath }/page/user_list.jsp";
        			}else{
        				//$("#phoneText").val(data);
        				alert(data);
        			}
        		},
        		error: function(){
        			alert("ajax错误");
        		}
        	});
        }); */
        
    });


</script>



</html>
