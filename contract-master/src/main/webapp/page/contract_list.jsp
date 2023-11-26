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
				<button id="add" class="btn btn-secondary">增加合同</button>
				<button id="remove" class="btn btn-secondary">删除合同</button>
			</div>
		
			<table id="bootstrap-table" class="table" data-url="${ pageContext.request.contextPath }/contract/contractlist">
				<thead>
					<th data-field="checked" data-checkbox="true"></th>
					<th data-field="id" data-formatter="numFormatter">编号</th>
					<th data-field="typeName" data-sortable="true">合同类型</th>
					<th data-field="name" data-sortable="true">合同名称</th>
					<th data-field="startTime" data-sortable="true">开始时间</th>
					<th data-field="endTime" data-sortable="true">结束时间</th>
					<th data-field="money" data-sortable="true">合同金额</th>
					<th data-field="userName" data-sortable="true">起草人</th>
					<th data-field="changeStatus" data-sortable="true" data-formatter="changeStatusFormatter">变更</th>
					<th data-field="state" data-sortable="true" data-formatter="statesFormatter">状态</th>
					<th data-field="url" data-sortable="true" data-formatter="urlFormatter">附件</th>
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
    <div class="modal-content" style="height: 60% !important;">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">附件信息</h4>
      </div>
      <div class="modal-body">
      	<table  id="my-table" class="table" >
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
      <div class="modal-footer">
        <button type="button" class="btn btn-default btn-simple" data-dismiss="modal">取消</button>
   <!--      <button type="button" class="btn btn-success btn-fill">全部下载</button> -->
      </div>
    </div>
  </div>
</div>


<script type="text/javascript">
    var $table = $('#bootstrap-table');
    var $mytable = $('#my-table');

    function numFormatter(value, row, index){
    	return index+1;
    }
    
    function statesFormatter(value, row, index){
    	if(value == 0){
    		return "执行中";
    	}else if(value == 1){
    		return "完成";
    	}else if(value == 2){
    		return "废止";
    	}else{
    		return "";
    	}
    }
    //合同附件列表
    function sendId(value){
    	 $mytable.bootstrapTable( "refresh", {url: "${ pageContext.request.contextPath }/contract/enclosurelist?contractid=" + value });
    	 $mytable.bootstrapTable({
             undefinedText: 'N/A',//未定义文本
             url: "${ pageContext.request.contextPath }/contract/enclosurelist?contractid=" + value ,
             clickToSelect: true,
             pagination: true,
             searchAlign: 'right',
             pageSize: 5,
             clickToSelect: false,
             pageList: [5,10,25,50,100],
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
    	 
    }
    
    function changeStatusFormatter(value, row, index){
    	if(value == 0){
    		return "未变更";
    	}else{
    		return "已变更";
    	}
    }
    
    function urlFormatter(value, row, index){
    	return '<button type="button" data-toggle="modal" data-target="#myModal" class="btn btn-xs btn-fill btn-primary shenhe" onclick=sendId("' + row.id + '")>查看</button>';
    }
    
    function downFormatter(value, row, index){
    	return "<a href='${ pageContext.request.contextPath }/contract/fileDownLoad?id=" + row.id + "'>下载</a>";
    }
    
    function operateFormatter(value, row, index) {
    	
    	//执行中可以修改、删除
    	if(row.state == 0){
    		return [
 		            '<a rel="tooltip" title="Edit" class="btn btn-simple btn-warning btn-icon table-action edit" href="javascript:void(0)">',
 		            '<i class="fa fa-edit"></i>',
 		            '</a>',
 		            '<a rel="tooltip" title="Remove" class="btn btn-simple btn-danger btn-icon table-action remove" href="javascript:void(0)">',
 		            '<i class="fa fa-remove"></i>',
 		            '</a>'
 		        ].join('');
    	}
    	//已完成只能查看
    	if(row.state == 1){
    		return [
  		            '<a rel="tooltip" title="查看" class="btn btn-simple btn-warning btn-icon table-action info" href="javascript:void(0)">',
  		            '<i class="fa fa-eye"></i>',
  		            '</a>'
  		        ].join('');
    	}
    	//废止可以删除
    	if(row.state == 2){
    		return [
  		            '<a rel="tooltip" title="Remove" class="btn btn-simple btn-danger btn-icon table-action remove" href="javascript:void(0)">',
  		            '<i class="fa fa-remove"></i>',
  		            '</a>'
  		        ].join('');
    	}
        
    }

    $().ready(function(){
        window.operateEvents = {
            'click .edit': function (e, value, row, index) {
               /* info = JSON.stringify(row);
                alert(row.id);
                swal('You click edit icon, row: ', info);
                console.log(info);*/
                location.href = "${ pageContext.request.contextPath }/contract/findById?id="+row.id;
            },
            'click .info': function (e, value, row, index) {
                /* info = JSON.stringify(row);
                 alert(row.id);
                 swal('You click edit icon, row: ', info);
                 console.log(info);*/
                 location.href = "${ pageContext.request.contextPath }/contract/findByIdInfo?id="+row.id;
             },
            
            'click .remove': function (e, value, row, index) {
                //console.log(row);
                $table.bootstrapTable('remove', {
                    field: 'id',
                    values: [row.id]
                });
                $.ajax({
                	dataType:"text",
                	url: "${ pageContext.request.contextPath }/contract/deteteById?id=" + [row.id],
                	type:"post",
                	success: function(data){
                		alert(data);
                		$table.bootstrapTable( "refresh");
                	}
                });
            }
        };
        /**删除方法*/
        $("#remove").click(function () {
            var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
                return row.id;
            });
            if(ids != ""){
            	$.ajax({
                	//data:{"ids":ids},
                	dataType:"text",
                	url:"${ pageContext.request.contextPath }/contract/deteteByIds?ids=" + ids ,
                	type:"post",
                	success: function(data){
                		alert(data);
                		$table.bootstrapTable( "refresh");
                	}
                });
            }
            
            $table.bootstrapTable('remove', {
                field: 'id',
                values: ids,
            });
        });
        /**增加方法*/
        $("#add").click(function () {
            window.location.href="${ pageContext.request.contextPath }/page/contract_add.jsp";
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
            $mytable.bootstrapTable('resetView');
        });


    });

</script>

<script>


</script>

</html>
