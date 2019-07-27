<%@page pageEncoding="UTF-8" isELIgnored="false" %>

<script>
    $("#album-table").jqGrid({
        url : "${pageContext.request.contextPath}/album/selectAllAlbum",
        datatype : "json",
        height : 190,
        colNames : [ '编号', '名称', '封面', '作者', '状态','章节数','得分', '简介','创建时间'],
        colModel : [
            {name : 'id',hidden:true},
            {name : 'title',editable:true},
            {name : 'cover',editable:true,edittype:"file",formatter:function (value,option,rows) {
                    return "<img style='width:50%;height:12%;' src='${pageContext.request.contextPath}/album/img/"+rows.cover+"'/>";
                }},
            {name : 'author',editable:true},
            {name : 'broadcast',editable:true},
            {name: 'count',editable:true},
            {name: 'score',editable:true},
            {name : 'brief',editable:true},
            {name : 'createDate'}
        ],
        autowidth:true,
        styleUI:"Bootstrap",
        rowNum : 8,
        height:"500px",
        rowList : [ 8, 10, 20, 30 ],
        pager : '#album-pager',
        sortname : 'id',
        viewrecords : true,
        sortorder : "desc",
        multiselect : false,
        caption : "德玛西亚",
        subGrid : true,
        editurl : "${pageContext.request.contextPath}/album/edit",
        subGridRowExpanded : function(subgrid_id, albumId) {// 1.装子表的容器id    2.关系属性
            var subgrid_table_id = subgrid_id + "_t";   //子表的table  id
            var pager_id = "p_" + subgrid_table_id;     //子表的div  id
            $("#" + subgrid_id).html(
                "<table id='" + subgrid_table_id + "' class='scroll'></table>" +
                "<div id='" + pager_id + "' class='scroll'></div>");
            $("#" + subgrid_table_id).jqGrid(
                {
                    url : "${pageContext.request.contextPath}/chapter/selectChaptersBuAlbumId?albumId="+albumId,  //查询当前专辑下的所有章节？当前专辑的id
                    datatype : "json",
                    editurl:"${pageContext.request.contextPath}/chapter/edit?albumId="+albumId,
                    colNames : [ '编号', '标题', '大小', '时长','文件名',"创建日期","在线播放" ],
                    colModel : [
                        {name : "id", hidden:true},
                        {name : "title",editable:true,width:"70"},
                        {name : "size",width:"50"},
                        {name : "duration",width:"50"},
                        {name : "name",editable:true,edittype:"file",width:"70"},
                        {name : "createDate",width:"80"},
                        {name : "aa",formatter:function (value,options,row) {
                                return "<audio controls loop>\n" +
                                    "  <source src='${pageContext.request.contextPath}/album/music/"+row.name+"' type=\"audio/ogg\">\n" +
                                    "</audio>";
                            }}
                    ],
                    autowidth:true,
                    styleUI:"Bootstrap",
                    rowNum : 20,
                    pager : pager_id,
                    sortname : 'num',
                    sortorder : "asc",
                    height : '100%',
                }).jqGrid('navGrid',
                "#" + pager_id, {
                    edit : false,
                    add : true,
                    del : false
                },{
                    //控制子表的修改
                },{
                    //控制子表的添加
                    closeAfterAdd:true,
                    afterSubmit:function (response) {
                        var status = response.responseJSON.status;
                        var id = response.responseJSON.message;
                        if(status){
                            $.ajaxFileUpload({
                                url:"${pageContext.request.contextPath}/chapter/upload",
                                fileElementId:"name",
                                data:{id:id},
                                type:"post",
                                success:function () {
                                    $("#"+subgrid_table_id).trigger("reloadGrid");
                                }
                            });
                        }
                        return "123";
                    }
                });
        },

    }).navGrid("#album-pager", {edit : false,add : true,del : false,search:false},{
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
                    url:"${pageContext.request.contextPath}/album/upload",
                    fileElementId:"cover",
                    data:{id:id},
                    type:"post",
                    success:function () {
                        $("#album-table").trigger("reloadGrid");
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
    <h2>专辑的展示</h2>
</div>
<table id="album-table"></table>
<div id="album-pager" style="height: 40px"></div>