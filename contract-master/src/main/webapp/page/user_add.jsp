<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="common.jsp" %>

<div class="row">
    <div class="col-md-12">
        <div class="card">
            <form id="allInputsFormValidation" class="form-horizontal" novalidate="">
                <div class="content">
                    <legend>增加人员信息</legend>

                    <fieldset>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">用户名称<star>*</star></label>
                            <div class="col-sm-6">
                                <input class="form-control"
                                       type="text"
                                       name="name"
                                       required="required"
                                />
                            </div>
                           <!-- <div class="col-sm-4"><code>required</code></div>-->
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">手机号<star>*</star></label>
                            <div class="col-sm-6">
                                <input class="form-control"
                                       type="text"
                                       name="phone"
                                       required="required"
                                       isMobile="true"
                                />
                            </div>
                            <div class="col-sm-4"><code id="phoneText"></code></div>
                        </div>
                    </fieldset>

                    <fieldset>
                    	<div class="form-group">
                            <label class="col-sm-4 control-label">选择用户角色<star>*</star></label>
                            <div class="col-sm-6">
                                <select class="form-control" id="roleid" name="roleId" required="required">
                                </select>
                            </div>
                        </div>
                    </fieldset>


                </div>
                <div class="footer text-center">
                    <button type="submit" id="mysubmit" class="btn btn-info btn-fill">增加</button>
                    <button type="reset" class="btn btn-info btn-fill">重置</button>
                    <button type="button" class="btn btn-info btn-fill">取消</button>
                </div>
            </form>

        </div><!--  end card  -->
    </div> <!-- end col-md-12 -->
</div> <!-- end row -->

        
<script type="text/javascript">


    $().ready(function(){

    	
    	//渲染select,查询角色信息
		$.ajax({
            url: "${ pageContext.request.contextPath }/role/rolelist",
            type: "post",
            dataType: "json",
            success: function (data) {
                $("#roleid").html('');//移除原元素
                if (data.length > 0) {
                    $.each(data, function (i) {
                        $("<option value='" + data[i].id + "' >" + data[i].role + "</option>").appendTo("#roleid");//构造html元素并插入
                    });
                    $('#roleid').selectpicker('refresh');
                }
                else {
                    $.MsgShow(data.Message, "fail");
                }
            }
        });
    	
        //重写验证规则，设置返回中文
        $.validator.messages.required = jQuery.validator.format("请填写必填字段");
        $.validator.messages.equalTo = jQuery.validator.format("两次密码输入不一致");
        // 手机号码验证
        jQuery.validator.addMethod("isMobile", function(value, element) {
            var length = value.length;
            return (length == 11 && /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(16[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(value));
        }, "请正确填写您的手机号码。");
        
		
        $('#allInputsFormValidation').validate({
        	submitHandler: function(form) { //通过之后回调
        		//进行ajax传值
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
            				alert(data);
            			}
            		},
        		});
        	},
        });
        
		
        

        
    });


</script>



</html>
