<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
    <hend>
        <title>轮播图</title>
        <%--引入样式--%>
        <link rel="stylesheet" href="statics/boot/css/bootstrap.css" type="text/css">
        <link rel="stylesheet" href="statics/jqgrid/css/trirand/ui.jqgrid-bootstrap.css" type="text/css">
        <script src="statics/boot/js/jquery-2.2.1.min.js" type="text/javascript"></script>
        <script src="statics/boot/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="statics/jqgrid/js/trirand/i18n/grid.locale-cn.js" type="text/javascript"></script>
        <script src="statics/jqgrid/js/trirand/jquery.jqGrid.min.js" type="text/javascript"></script>
        <script src="statics/jqgrid/js/ajaxfileupload.js" type="text/javascript"></script>

        <script type="text/javascript">
            $(function () {
                $('#table1').jqGrid({
                    url: "${pageContext.request.contextPath}/banner/selectAllBanner",
                    datatype: 'json',
                    styleUI: 'Bootstrap',
                    colNames: ['编号', '名字', '图片', '描述', '状态', '日期'],
                    colModel: [
                        {name: 'id', align: 'center',
                            hidden:true,
                           },
                        {name: 'name',
                            editable: true,//指定单元格编辑
                            align: 'center'},
                        {name : 'cover',editable:true,edittype:"file",formatter:function (value,option,rows) {
                                return "<img style='width:50%;height:12%;' src='${pageContext.request.contextPath}/banner/img/"+rows.cover+"'/>";
                            }},
                        {name: 'description', align: 'center',editable:true},
                        {name : 'status',editable:true,edittype:"select",editoptions:{value:"正常:正常;冻结:冻结"}},
                        {name: 'createDate', align: 'center'}
                    ],
                    caption:"轮播图的详细信息 ",
                    mtype:'post',//设置请求方式 POST
                    pager:'#page',
                    rowList:[5,20,30,40,50],
                    height:500,
                    rowNum : 5,//每页展示条数
                    autowidth:true,//自适应表格宽度
                    viewrecords : true,//展示总记录条数
                    editurl : "${pageContext.request.contextPath}/banner/edit"
                }).navGrid("#page", {edit : true,add : true,del : true,search:false},{
                    //控制修改
                    closeAfterEdit:close,
                    beforeShowForm:function (frm) {//在表单显示前出触发
                        //修改图片设为只读
                        frm.find("#cover").attr("disabled",true);
                    }
                },{
                    //控制添加
                    //关闭添加对话框
                    closeAfterAdd:close,
                    afterSubmit:function (response) {
                        var status = response.responseJSON.status;
                        var id = response.responseJSON.message;
                        if(status){
                            $.ajaxFileUpload({
                                url: "${pageContext.request.contextPath}/banner/upload",
                                fileElementId:"cover",
                                data:{id:id},
                                type:"post",
                                success:function () {
                                    $("#table1").trigger("reloadGrid");
                                }
                            });
                        }
                        return "12312";
                    }
                })
                })
        </script>


    </hend>
    <body>
    <div>
     <h2>轮播图</h2>
    <table id="table1"></table>
    <div id="page" style="height: 40px"></div>
    </div>
    </body>
</html>