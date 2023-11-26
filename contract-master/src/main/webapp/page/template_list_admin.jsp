<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"  isELIgnored="false"%>
<%@ include file="common.jsp"%>
 <style>
    .table th,.table td{ text-align:center;vertical-align:middle!important;}
    .fixed-table-body{
    	height:53% !important;
    	overflow: auto;
    }
</style>

<div class="row">
	<div class="col-md-12">
		<div class="card">

			<div class="toolbar">
				<!--        Here you can write extra buttons/actions for the toolbar              -->
				<button type="button" id="add" class="btn btn-secondary"  >批量审核</button>
			</div>

			<table id="bootstrap-table" class="table" data-url="${ pageContext.request.contextPath }/template/templatelistadmin">
				<thead>
					<th data-field="checked" data-checkbox="true"></th>
					<th data-field="id" data-formatter="numFormatter">编号</th>
					<th data-field="name" data-sortable="true">模板名称</th>
					<th data-field="typeName" data-sortable="true">模板类别</th>
					<th data-field="path" data-sortable="true" data-formatter="downFormatter">模板</th>
					<th data-field="userName" data-sortable="true">模板起草人</th>
					<th data-field="time" data-sortable="true">模板起草时间</th>
					<th data-field="actions" class="td-actions text-right"
						data-events="operateEvents" data-formatter="operateFormatter">操作</th>
				</thead>
				<tbody>

				</tbody>
			</table>
		</div>
		<!--  end card  -->
	</div>
	<!-- end col-md-12 -->
</div>
<!-- end row -->

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">审核</h4>
      </div>
       <form id="allInputsFormValidation" class="form-horizontal" novalidate="">
      <div class="modal-body">
      <input type="hidden" id="templateId"  name="templateIds" />
         <fieldset>
             <div class="form-group">
                 <label class="col-sm-2 control-label">审核意见<star>*</star></label>
                 <div class="col-sm-6">
                     <textarea class="form-control"
                            type="textare"
                            name="opinion"
                            required="required"
                            style="margin: 0px -68.3125px 0px 0px; width: 340px; height: 172px;"
                     ></textarea>
                 </div>
             </div>
         </fieldset>
         <fieldset>
             <div class="form-group">
                 <label class="col-sm-2 control-label">审批状态<star>*</star></label>
                 <div class="col-sm-6">
                    <label class="radio">
                         <input type="radio" data-toggle="radio" name="state" value="1" checked="checked" />通过
                     </label>
                     <label class="radio">
                         <input type="radio" data-toggle="radio" name="state" value="2" />不通过
                     </label>
                 </div>
             </div>
         </fieldset>
      
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default btn-simple" data-dismiss="modal">取消</button>
        <button type="submit" class="btn btn-success btn-fill">保存</button>
      </div>
      </form>
    </div>
  </div>
</div>



<script type="text/javascript">
    var $table = $('#bootstrap-table');

    function numFormatter(value, row, index){
    	return index+1;
    }
    function downFormatter(value, row, index){
    	return "<a href='${ pageContext.request.contextPath }/approval/fileDownLoad?id=" + row.id + "'>下载</a>";
    }
    
    function stateFormatter(value, row, index){
    	if(value == 0){
    		return "未审核";
    	}else if(value == 1){
    		return "已通过";
    	}
    	else if(value == 2){
    		return "未通过";
    	}
    }
    
    function sendId(value){
    	$("#templateId").val(value);
    }
    
    function operateFormatter(value, row, index) {
    	return [
    	        '<button type="button" data-toggle="modal" data-target="#myModal" class="btn btn-xs btn-fill btn-primary shenhe" onclick=sendId("' + row.id + '")>审核</button> '
    	        ].join('');
    }

    $().ready(function(){
    	
    	
    	
        window.operateEvents = {
            'click .edit': function (e, value, row, index) {
               /* info = JSON.stringify(row);
                alert(row.id);
                swal('You click edit icon, row: ', info);
                console.log(info);*/
                location.href = "${ pageContext.request.contextPath }/template/findById?id="+row.id;
            },
            'click .remove': function (e, value, row, index) {
                //console.log(row);
                $table.bootstrapTable('remove', {
                    field: 'id',
                    values: [row.id]
                });
                $.ajax({
                	dataType:"text",
                	url: "${ pageContext.request.contextPath }/approval/deteteById?id=" + [row.id],
                	type:"post",
                	success: function(data){
                		alert(data);
                		$table.bootstrapTable( "refresh");
                	}
                });
            }
        };
        /**删除方法*/
        $("#add").click(function () {
            var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
                return row.id;
            });
            if(ids != ""){
            	$("#add").attr("data-toggle","modal");
            	$("#add").attr("data-target","#myModal");
            	$("#templateId").val(ids);
            	$("#add").one("click",function(){});
            	//data-toggle="modal" data-target="#myModal"
            	/* $.ajax({
                	//data:{"ids":ids},
                	dataType:"text",
                	url:"${ pageContext.request.contextPath }/approval/deteteByIds?ids=" + ids ,
                	type:"post",
                	success: function(data){
                		alert(data);
                		$table.bootstrapTable( "refresh");
                	}
                }); */
            }else{
            	alert("请选择审批模板！");
            	$("#add").removeAttr("data-toggle");
            	$("#add").removeAttr("data-target");
            }
        });
      

        $table.bootstrapTable({
            undefinedText: 'N/A',//未定义文本
           // url: dat ,
            toolbar: ".toolbar",//工具按钮用哪个容器
            clickToSelect: true,
            showRefresh: true,
            search: true,
            //showToggle: true,
            //showColumns: true,
            pagination: true,
            searchAlign: 'right',
            searchPlaceholder: '模糊搜索',
            pageSize: 8,
            clickToSelect: false,
            pageList: [8,10,25,50,100],
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
            		url:"${ pageContext.request.contextPath }/approval/approvaladd",
           		    cache: false,
                    processData: false,
                    contentType: false,
                    async: false,
            		success:function(data){
            			//console.log(data);
            			if(data == "1"){
            				window.location.href = "${ pageContext.request.contextPath }/page/template_list_admin.jsp";
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
