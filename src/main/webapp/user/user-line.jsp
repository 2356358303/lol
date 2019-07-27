<%@page pageEncoding="UTF-8" %>

<script src="echarts/echarts.min.js"></script>
<script src="statics/boot/js/jquery-3.3.1.min.js"></script>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="main" style="width: 600px;height:400px;"></div>

<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));    // 必须通过js语法获取当前div进行初始化

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '上半年用户注册趋势',
        },
        tooltip: {},  // 数据的提示
        legend: {
            data:['用户']
        },
        xAxis: {
            data: ["一月","二月","三月","四月","五月","六月"]
        },
        yAxis: {},

    };


    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    $.ajax({
        url:"${pageContext.request.contextPath}/user/count",
        type:"get",
        dataType:"json",
        success:function (response) {
            myChart.setOption({
                series: [{
                    name:'用户',
                    type:'line',
                    data:response.data
                }]

            })
        }
    })


    <%--$.ajax({--%>
    <%--url:"${pageContext.request.contextPath}/json/json-bar.json",--%>
    <%--type:"get",--%>
    <%--datetype:"json",--%>
    <%--success:function (response) {--%>
    <%--myChart.setOption({--%>
    <%--series: [{--%>
    <%--name: '销量',--%>
    <%--type: 'bar',//bar：柱状图  line:折线图     pie:饼图--%>
    <%--data: response.data--%>
    <%--}],--%>
    <%--xAxis: {--%>
    <%--data: response.name--%>
    <%--}--%>
    <%--})--%>
    <%--}--%>
    <%--})--%>





</script>
