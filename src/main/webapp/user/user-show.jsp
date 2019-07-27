<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<script>
    function out() {
        window.location.href="${pageContext.request.contextPath}/user/out";
    }
    $("#user-table").jqGrid({
        url: "${pageContext.request.contextPath}/user/selectAllUser",
        datatype:"json",
        height: 190,
        colNames : [ '编号', '名称', '密码', '手机', '法名','省','城市','签名','头像','性别','日期'],
        colModel : [
            {name : 'id',hidden:true},
            {name : 'username',editable:true},
            {name : 'password',editable:true},
            {name : 'phone',editable:true},
            {name : 'dharma',editable:true},
            {name : 'province'},
            {name : 'city'},
            {name : 'sign'},
            {name : 'photo',editable:true,edittype:"file",formatter:function (value,option,rows) {
                    return "<img style='width:50%;height:12%;' src='${pageContext.request.contextPath}/banner/img/"+rows.cover+"'/>";
                }},
            {name : 'sex'},
            {name : 'createDate'}
        ],
        styleUI:"Bootstrap",
        autowidth:true,
        height:"300px",
        rowNum : 3,
        rowList : [ 3, 5, 10 ],
        pager : '#user-pager',
        viewrecords : true,
        caption : "轮播图的详细信息",

    }).navGrid("#user-pager", {edit : true,add : true,del : true,search:false},{
        //控制修改
        closeAfterEdit:close,
        beforeShowForm:function (frm) {
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
                    url:"${pageContext.request.contextPath}/user/upload",
                    fileElementId:"cover",
                    data:{id:id},
                    type:"post",
                    success:function () {
                        $("#banner-table").trigger("reloadGrid");
                    }
                });
            }
            return "12312";
        }
    },{
        //控制删除
    });
</script>
<div class="page-header">
    <h2>用户的展示</h2>
</div>
<div>
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">所有用户</a></li>
        <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab" onclick="out()">导出所有用户信息</a></li>
    </ul>
</div>
<table id="user-table"></table>
<div id="user-pager" style="height: 40px"></div>