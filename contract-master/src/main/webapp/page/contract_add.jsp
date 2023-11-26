<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="common.jsp" %>
<style>
<!--

-->
.card .content{
	padding: 10px !important;
}
</style>
<div class="row">
    <div class="col-md-12">
        <div class="card">
            <form id="allInputsFormValidation" class="form-horizontal" novalidate="" enctype="multipart/form-data">
                <div class="content">
                    <legend>增加合同信息</legend>

                    <fieldset>
                        <div class="form-group">
                         	<label class="col-sm-2 control-label">合同编号<star>*</star></label>
                            <div class="col-sm-3">
                                <input class="form-control"
                                       type="text"
                                       name="num"
                                       required="required"
                                />
                            </div>
                            <label class="col-sm-2 control-label">合同名称<star>*</star></label>
                            <div class="col-sm-3">
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
                     		<label class="col-sm-2 control-label">是否付清<star>*</star></label>
                            <div class="col-sm-3">
                                <select class="form-control" name="payState" required="required">
                                	<option value="0">未付清</option>
                                	<option value="1">已付清</option>
                                </select>
                            </div>
                     
                    		<label class="col-sm-2 control-label">合同金额<star>*</star></label>
                            <div class="col-sm-2">
                                <input class="form-control"
                                       type="text"
                                       name="money"
                                       required="required"
                                       isMoney="true"
                                />
                            </div>
                            <label class="control-label">元</label>
                            
                    	</div>
                    </fieldset>
                    
					<fieldset>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">开始时间<star>*</star></label>
                            <div class="col-sm-3">
                                <input class="form-control datetimepicker"
                                       type="text"
                                       name="startTime"
                                       required="required"
                                       placeholder="录入时间"
                                />
                            </div>
                            <label class="col-sm-2 control-label">结束时间<star>*</star></label>
                            <div class="col-sm-3">
                                <input class="form-control datetimepicker"
                                       type="text"
                                       name="endTime"
                                       required="required"
                                       placeholder="录入时间"
                                />
                            </div>
                        </div>
                    </fieldset>
                     <fieldset>
                        <div class="form-group">
                        	<label class="col-sm-2 control-label">合同类型<star>*</star></label>
                            <div class="col-sm-3">
                               <select class="form-control" name="typeId" id="typeId" required="required">
                                </select>
                            </div>
                            <label class="col-sm-2 control-label">上传合同附件<star>*</star></label>
                            <div class="col-sm-3">
                                <input class="form-control"
                                       type="file"
                                       name="files"
                                       required="required"
                                       multiple="multiple"
                                       id="fileField"
                                />
                            </div>
                             <label class="control-label"><code>支持多附件</code></label>
                        </div>
                    </fieldset>
                    <legend>增加提醒信息<small>(提醒开始时间不为空时，录入提醒信息)</small></legend>
 					<fieldset>
                     	<div class="form-group">
                     		<label class="col-sm-2 control-label">提醒名称</label>
                            <div class="col-sm-3">
                                <input class="form-control"
                                       type="text"
                                       name="ename"
                                />
                            </div>
                     		
                    		<label class="col-sm-2 control-label">开始时间</label>
                            <div class="col-sm-3">
                                <input class="form-control datetimepicker"
                                       type="text"
                                       name="estartTime"
                                       placeholder="录入时间"
                                />
                            </div>
                            
                    	</div>
                    </fieldset>
                    <fieldset>
                     	<div class="form-group">
                     		<label class="col-sm-2 control-label">提醒内容</label>
			                 <div class="col-sm-8">
			                     <textarea class="form-control"
			                            type="textare"
			                            name="content"
			                            style="margin: 0px -68.3125px 0px 0px;width: 100%;height: 10%;" aria-required="true"
			                     ></textarea>
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


    $().ready(function(){
   	 	// Init DatetimePicker
        demo.initFormExtendedDatetimepickers();
    	
    	//渲染select,查询模板信息
		$.ajax({
            url: "${ pageContext.request.contextPath }/template/templatelist",
            type: "post",
            dataType: "json",
     
            success: function (data) {
                $("#templateid").html('');//移除原元素
                if (data.length > 0) {
                    $.each(data, function (i) {
                        $("<option value='" + data[i].id + "' >" + data[i].name + "</option>").appendTo("#templateid");//构造html元素并插入
                    });
                    $('#templateid').selectpicker('refresh');
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
     	// 金额验证规则
        jQuery.validator.addMethod("isMoney", function(value, element) {
            return (/(^[1-9](\d+)?(\.\d{1,2})?$)|(^\d\.\d{1,2}$)/.test(value));
        }, "请输入正确的金额（大于0且最多含两位小数）");
		
        $('#allInputsFormValidation').validate({
        	submitHandler: function(form) { //通过之后回调
        		
        		var formdata = new FormData($("#allInputsFormValidation")[0]);
                $.ajax({
                	dataType:"text",
                    type: "post",
                    url: "${ pageContext.request.contextPath }/contract/contractadd",
                    data: formdata,
                    cache: false,
                    processData: false,
                    contentType: false,
                    async: false,
                    success: function(data){
                    	console.log(data);
            			if(data == "1"){
            				window.location.href = "${ pageContext.request.contextPath }/page/contract_list.jsp";
            			}else{
            				alert(data);
            			}
                    },
                    error: function(data){
                        alert( "失败");
                    }
                });
        		
        	},
        });
        
		
        

        
    });


</script>



</html>
