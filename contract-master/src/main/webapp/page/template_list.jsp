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
				<button id="add" class="btn btn-secondary">增加模板</button>
				<button id="remove" class="btn btn-secondary">删除模板</button>
			</div>

			<table id="bootstrap-table" class="table" data-url="${ pageContext.request.contextPath }/template/templatelist">
				<thead>
					<th data-field="checked" data-checkbox="true"></th>
					<th data-field="id" data-formatter="numFormatter">编号</th>
					<th data-field="name" data-sortable="true">模板名称</th>
					<th data-field="typeName" data-sortable="true">模板类别</th>
					<th data-field="path" data-sortable="true" data-formatter="downFormatter">模板</th>
					<th data-field="state" data-sortable="true" data-formatter="stateFormatter">审核情况</th>
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




<script type="text/javascript">
    var $table = $('#bootstrap-table');

    function numFormatter(value, row, index){
    	return index+1;
    }
    function downFormatter(value, row, index){
    	return "<a href='${ pageContext.request.contextPath }/template/fileDownLoad?id=" + row.id + "'>下载</a>";
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
                	url: "${ pageContext.request.contextPath }/template/deteteById?id=" + [row.id],
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
                	url:"${ pageContext.request.contextPath }/template/deteteByIds?ids=" + ids ,
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
            window.location.href="${ pageContext.request.contextPath }/page/template_add.jsp";
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

<script>


</script>

</html>
