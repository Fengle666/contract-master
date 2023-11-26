
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Title</title>
    <%@ include file="common.jsp"%>
  </head>
  <body>
    <div style="margin-top: 20px;width: 100%;display: flex;flex-direction: column;justify-content: center;align-items: center;height: 100vh;">
     <div id="chat1" style="width: 1000px; height: 400px;"></div>
     <div id="chat2" style="width: 1000px; height: 400px;"></div>
    </div>
  </body>
  <script type="text/javascript">

  $.ajax
  ({
       dataType:"text",
       type: 'get',
       url: "${ pageContext.request.contextPath }/contract/getContractAdd",
       success: function (data){
      	 	console.log("-------------->",data)
      	 	console.log("-------------->", JSON.parse(data))

      	 	var obj = JSON.parse(data);

      	 	var chartDom = document.getElementById('chat1');
            var myChart1 = echarts.init(chartDom);
            var option;

            option = {
                xAxis: {
                  type: 'category',
                  data: obj.map(a=> a.day)
                },
                yAxis: {
                  type: 'value'
                },
                series: [
                  {
                    data: obj.map(a=> a.count),
                    type: 'bar'
                  }
                ]
              };
            myChart1.setOption({title: {text: '合同统计',left: 'center'}})
            option && myChart1.setOption(option);

       },
       error: function(){
          alert("ajax错误");
       }
   });


   $.ajax
     ({
          dataType:"text",
          type: 'get',
          url: "${ pageContext.request.contextPath }/log/getDownload",
          success: function (data){
         	 	console.log("-------------->",data)
         	 	console.log("-------------->", JSON.parse(data))

         	 	    var obj = JSON.parse(data);

         	 	    var chartDom = document.getElementById('chat2');
                    var myChart2 = echarts.init(chartDom);
                    var option;
                    option = {
                      xAxis: {
                        type: 'category',
                        data: obj.map(a=> a.day)
                      },
                      yAxis: {
                        type: 'value'
                      },
                      series: [
                        {
                          data: obj.map(a=> a.count),
                          type: 'line'
                        }
                      ]
                    };
                    myChart2.setOption({title: {text: '合同下载量',left: 'center'}})
                    option && myChart2.setOption(option);
          },
          error: function(){
             alert("ajax错误");
          }
      });

  </script>
</html>
