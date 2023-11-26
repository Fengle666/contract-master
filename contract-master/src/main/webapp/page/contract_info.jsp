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
                    <legend>修改合同信息</legend>
					<input type="hidden" name="id" value="${contract.id }" />
					
					<input type="hidden" name="changeStatus" value="1" />
					<input type="hidden" name="userRoleId" value="${contract.userRoleId }" />
					<input type="hidden" name="isDelete" value="${contract.isDelete }" />
                    <fieldset>
                        <div class="form-group">
                         	<label class="col-sm-2 control-label">合同编号<star>*</star></label>
                            <div class="col-sm-3">
                            	<label class="control-label">${contract.num }</label>
                            </div>
                            <label class="col-sm-2 control-label">合同名称<star>*</star></label>
                            <div class="col-sm-3">
                            	<label class="control-label">${contract.name }</label>
                            </div>
                        </div>
                    </fieldset>
                     <fieldset>
                     	<div class="form-group">
                     		<label class="col-sm-2 control-label">是否付清<star>*</star></label>
                            <div class="col-sm-3">
                            	<label class="control-label">
                            		<c:if test="${contract.payState eq 0  }">否</c:if>
                            		<c:if test="${contract.payState eq 1  }">是</c:if>
                            	</label>
                            </div>
                     
                    		<label class="col-sm-2 control-label">合同金额<star>*</star></label>
                            <div class="col-sm-2">
                            	<label class="control-label">${contract.money }元</label>
                            </div>
                            
                    	</div>
                    </fieldset>
                    
					<fieldset>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">开始时间<star>*</star></label>
                            <div class="col-sm-3">
                            	<label class="control-label">${contract.startTime }</label>
                            </div>
                            <label class="col-sm-2 control-label">结束时间<star>*</star></label>
                            <div class="col-sm-3">
                            	<label class="control-label">${contract.endTime }</label>
                            </div>
                        </div>
                    </fieldset>
                     <fieldset>
                        <div class="form-group">
                        	<label class="col-sm-2 control-label">合同类型<star>*</star></label>
                            <div class="col-sm-3">
                            	<label class="control-label" id="type"></label>
                            </div>
                            <label class="col-sm-2 control-label">合同状态<star>*</star></label>
                            <div class="col-sm-3">
                            	<label class="control-label">
                            		<c:if test="${contract.state eq 0  }">执行</c:if>
                            		<c:if test="${contract.state eq 1  }">完成</c:if>
                            		<c:if test="${contract.state eq 2  }">废止</c:if>
                            	</label>
                            </div>
                            
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group">
                        	<label class="col-sm-2 control-label">合同附件<star>*</star></label>
                            <div class="col-sm-8">
                               <table  id="my-table" class="table" data-url="${ pageContext.request.contextPath }/contract/enclosurelist?contractid=${contract.id }">
									<thead>
										<th data-field="id" data-formatter="numFormatter">编号</th>
										<th data-field="name" data-sortable="true">附件名称</th>
										<th data-field="time" data-sortable="true">上传时间</th>
										<th data-field="actions" class="td-actions text-right"
											data-events="operateEvents" data-formatter="downFormatter">操作</th>
									</thead>
									<tbody>
								
									</tbody>
								</table>
                            </div>
                        </div>
                    </fieldset>
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
        if (data.length > 0) {
            $.each(data, function (i) {
            	if('${contract.typeId}' == data[i].id){
            		$("#type").text(data[i].type);
            	}
                
            });
        }
    }
});

function downFormatter(value, row, index){
	return "<a href='${ pageContext.request.contextPath }/contract/fileDownLoad?id=" + row.id + "'>下载</a>";
}
function numFormatter(value, row, index){
	return index+1;
}
    $().ready(function(){
    	var $mytable = $('#my-table');
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
    	
    	
		$mytable.bootstrapTable({
            undefinedText: 'N/A',//未定义文本
            //url: "${ pageContext.request.contextPath }/contract/enclosurelist?contractid=" + ${contract.id } ,
            clickToSelect: true,
            pagination: true,
            searchAlign: 'right',
            pageSize: 4,
            clickToSelect: false,
            pageList: [4,10,25,50,100],
            height:250,
            formatShowingRows: function(pageFrom, pageTo, totalRows){
                //do nothing here, we don't want to show the text "showing x of y from..."
            },
            formatRecordsPerPage: function(pageNumber){
                return pageNumber + "条/页";
            },
            icons: {
                refresh: 'fa fa-refresh',
                toggle: 'fa fa-th-list',
                columns: 'fa fa-columns',
                detailOpen: 'fa fa-plus-circle',
                detailClose: 'fa fa-minus-circle'
            }
        });
      //activate the tooltips after the data table is initialized
        $('[rel="tooltip"]').tooltip();

        $(window).resize(function () {
            $table.bootstrapTable('resetView');
            $mytable.bootstrapTable('resetView');
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
                    url: "${ pageContext.request.contextPath }/contract/contractupdate",
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
