<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
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
			 	<button id="add" class="btn btn-secondary">增加合同提醒信息</button>
				<button id="remove" class="btn btn-secondary">删除合同提醒信息</button> 
			</div>

			<table id="bootstrap-table" class="table" data-url="${ pageContext.request.contextPath }/earlywarning/earlywarninglist">
				<thead>
					<th data-field="checked" data-checkbox="true"></th>
					<th data-field="id" data-formatter="numFormatter">编号</th>
					<th data-field="contracts" data-sortable="true" data-formatter="contractFormatter">合同名称</th>
					<th data-field="name" data-sortable="true">提醒名称</th>
					<th data-field="content" data-sortable="true">提醒内容</th>
					<th data-field="startTime" data-sortable="true">提醒开始时间</th>
					<th data-field="userName" data-sortable="true">设置人</th>
					<th data-field="state" data-sortable="true" data-formatter="stateFormatter">状态</th>
					<th data-field="actions" class="td-actions text-right"
						data-events="operateEvents" data-formatter="operateFormatter">操作</th>
				</thead>
				<tbody>

				</tbody>
			</table>
		</div>
		<!--  end earlywarningd  -->
	</div>
	<!-- end col-md-12 -->
</div>
<!-- end row -->




<script earlywarning="text/javascript">
    var $table = $('#bootstrap-table');

    function numFormatter(value, row, index){
        return index + 1;
    }
    
    function stateFormatter(value, row, index){
    	if(value == 0){
    		return "执行中";
    	}else if(value == 1){
    		return "已关闭";
    	}
    }
    
    function contractFormatter(value, row, index){
    	console.log(value);
    	var date = "";
    	for(var i=0 ; i<value.length;i++){
    		date += value[i].name + " , ";
    	}
    	
    	return date.substring(0, date.length - 2);
    }
    
    function operateFormatter(value, row, index) {
        return [
           /* '<a rel="tooltip" title="View" class="btn btn-simple btn-info btn-icon table-action view" href="javascript:void(0)">',
            '<i class="fa fa-image"></i>',
            '</a>',*/
            '<a rel="tooltip" title="Edit" class="btn btn-simple btn-warning btn-icon table-action edit" href="javascript:void(0)">',
            '<i class="fa fa-edit"></i>',
            '</a>',
            '<a rel="tooltip" title="Remove" class="btn btn-simple btn-danger btn-icon table-action remove" href="javascript:void(0)">',
            '<i class="fa fa-remove"></i>',
            '</a>'
        ].join('');
    }

    $().ready(function(){
        window.operateEvents = {
            'click .edit': function (e, value, row, index) {
               
                location.href = "${ pageContext.request.contextPath }/earlywarning/findById?id="+row.id;
            },
            'click .remove': function (e, value, row, index) {
                //console.log(row);
                $table.bootstrapTable('remove', {
                    field: 'id',
                    values: [row.id]
                });
                $.ajax({
                	dataType:"text",
                	url: "${ pageContext.request.contextPath }/earlywarning/deteteById?id=" + [row.id],
                	earlywarning:"post",
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
                	url:"${ pageContext.request.contextPath }/earlywarning/deteteByIds?ids=" + ids ,
                	earlywarning:"post",
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
            window.location.href="${ pageContext.request.contextPath }/page/earlywarning_add.jsp";
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


    });

</script>

</html>
