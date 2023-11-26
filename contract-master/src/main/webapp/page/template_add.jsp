<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="common.jsp" %>

<div class="row">
    <div class="col-md-12">
        <div class="card">
            <form id="allInputsFormValidation" class="form-horizontal" novalidate="" enctype="multipart/form-data">
                <div class="content">
                    <legend>增加模板信息</legend>

                    <fieldset>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">模板名称<star>*</star></label>
                            <div class="col-sm-6">
                                <input class="form-control"
                                       type="text"
                                       name="name"
                                       required="required"
                                />
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">模板类别<star>*</star></label>
                            <div class="col-sm-6">
                                <select class="form-control" id="typeId" name="typeId" required="required">
                                </select>
                            </div>
                        </div>
                    </fieldset>
                    

                    <fieldset>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">上传模板文件<star>*</star></label>
                            <div class="col-sm-6">
                                <input class="form-control"
                                       type="file"
                                       name="file"
                                       required="required"
                                       id="fileField"
                                />
                            </div>
                            <div class="col-sm-4"><code id="phoneText"></code></div>
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

    	//渲染select,查询模板信息
		$.ajax({
            url: "${ pageContext.request.contextPath }/type/typelist",
            type: "post",
            dataType: "json",
            success: function (data) {
                $("#typeId").html('');//移除原元素
                if (data.length > 0) {
                    $.each(data, function (i) {
                        $("<option value='" + data[i].id + "' >" + data[i].type + "</option>").appendTo("#typeId");//构造html元素并插入
                    });
                    $('#typeId').selectpicker('refresh');
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
            return (length == 11 && /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(value));
        }, "请正确填写您的手机号码。");
        
		
        $('#allInputsFormValidation').validate({
        	submitHandler: function(form) { //通过之后回调
        		//进行ajax传值
        		var formdata = new FormData($("#allInputsFormValidation")[0]);
        		$.ajax({
        			data:formdata,
                	dataType:"text",
            		type:"post",
            		url:"${ pageContext.request.contextPath }/template/templateadd",
           		    cache: false,
                    processData: false,
                    contentType: false,
                    async: false,
            		success:function(data){
            			//console.log(data);
            			if(data == "1"){
            				window.location.href = "${ pageContext.request.contextPath }/page/template_list.jsp";
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
