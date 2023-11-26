<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="common.jsp" %>

<div class="row">
    <div class="col-md-12">
        <div class="card">
            <form id="allInputsFormValidation" class="form-horizontal" novalidate="">
                <div class="content">
                    <legend>增加合同提醒信息</legend>
                    <input type="text" name="id" value="${earlywarning.id }" />
                    <input type="text" name="isDelete" value="${earlywarning.isDelete }" />
                    <input type="text" name="state" value="${earlywarning.state }" />
                    <input type="text" name="userRoleId" value="${earlywarning.userRoleId }" />
					<fieldset>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">选择合同<star>*</star></label>
                            <div class="col-sm-6">
                            	<select multiple data-title="请选择" id="contractId" required="required" name="contractId" class="selectpicker" data-style="btn-info btn-fill btn-block" data-menu-style="dropdown-blue">
                                </select>
                            </div>
                        </div>
                    </fieldset>
					
                    <fieldset>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">提醒名称<star>*</star></label>
                            <div class="col-sm-6">
                                <input class="form-control"
                                       type="text"
                                       name="name"
                                       required="required"
                                       value="${earlywarning.name }"
                                />
                            </div>
                        </div>
                    </fieldset>
                     <fieldset>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">提醒内容<star>*</star></label>
                            <div class="col-sm-6">
                                <textarea class="form-control"
                                       name="content"
                                       required="required"
                                       value="${earlywarning.content }"
                                >${earlywarning.content }</textarea>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">提醒开始时间<star>*</star></label>
                            <div class="col-sm-6">
                                <input class="form-control datetimepicker"
                                       type="text"
                                       name="startTime"
                                       required="required"
                                       value="${earlywarning.startTime }"
                                />
                            </div>
                        </div>
                    </fieldset>

                </div>
                <div class="footer text-center">
                    <button type="submit" id="mysubmit" class="btn btn-info btn-fill">修改</button>
                    <button type="reset" class="btn btn-info btn-fill">重置</button>
                </div>
            </form>

        </div><!--  end typed  -->
    </div> <!-- end col-md-12 -->
</div> <!-- end row -->

        
<script type="text/javascript">


//渲染select,查询模板信息
$.ajax({
    url: "${ pageContext.request.contextPath }/contract/contractlistadd",
    type: "post",
    dataType: "json",
    success: function (data) {
        $("#contractId").html('');//移除原元素
        if (data.length > 0) {
            $.each(data, function (i) {
                $("<option value='" + data[i].id + "'  >" + data[i].name + "</option>").appendTo("#contractId");//构造html元素并插入
            });
          	//select元素值选中判定
            $.each($("#contractId option"), function (i) {
            	<c:forEach items="${earlywarning.contracts }" var="s">
             		if("${s.id }" == $(this).val()){
             			$(this).attr("selected","selected");
             		}
             	</c:forEach>	 
            });
            
            $('#contractId').selectpicker('refresh');
        }
    }
});


    $().ready(function(){

   	 	// Init DatetimePicker
        demo.initFormExtendedDatetimepickers();
    	
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
            		url:"${ pageContext.request.contextPath }/earlywarning/earlywarningupdate",
            		success:function(data){
            			console.log(data);
            			if(data == "1"){
            				window.location.href = "${ pageContext.request.contextPath }/page/earlywarning_list.jsp";
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
