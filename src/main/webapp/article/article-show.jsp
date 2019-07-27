<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<script>
    $("#article-table").jqGrid({
        url : "${pageContext.request.contextPath}/article/selectAllArticle",
        datatype : "json",
        colNames : [ '编号', '标题', '作者', '内容','创建时间',"操作"],
        colModel : [
            {name : 'id',hidden:true},
            {name : 'title',editable:true},
            {name : 'author',editable:true},
            {name : 'content',editable:true},
            {name : 'createDate'},
            {name : 'aa',formatter:function (value,options,row) {
                    return "<a class='btn btn-warning' onclick=\"operModal('edit','"+row.id+"')\">修改</a>";
                }}
        ],
        styleUI:"Bootstrap",
        autowidth:true,
        height:"300px",
        rowNum : 3,
        rowList : [ 3, 5, 10 ],
        pager : '#article-pager',
        viewrecords : true,
        caption : "文章的详细信息",
        editurl : "${pageContext.request.contextPath}/article/edit"
    }).navGrid("#article-pager", {edit : false,add : false,del : true,search:false});


    //打开模态框
    function operModal(oper,id) {
        KindEditor.html("#editor_id","");

        var article = $("#article-table").jqGrid("getRowData",id);
        //给表单设置默认值
        $("#article-id").val(article.id);
        $("#article-title").val(article.title);
        $("#article-author").val(article.author);
        KindEditor.html("#editor_id",article.content);
        $("#article-oper").val(oper);

        $("#article-modal").modal("show");

    }

    //初始化kindeditor
    KindEditor.create('#editor_id',{
        //展示图片空间
        allowFileManager:true,
        //图片空间对应的地址
        fileManagerJson:"${pageContext.request.contextPath}/article/browser",
        //上传图片的地址
        uploadJson:"${pageContext.request.contextPath}/article/upload",
        //上传图片时接受的参数
        filePostName:"articleImage",
        //设置只能改变宽度，不能改变高度
        resizeType:1,
        //集成项目时必须添加,同步KindEditor的值到textarea文本框
        afterBlur:function () {
            this.sync();
        }
    });

    function dealSave() {
        //    文件添加
        $.ajax({
            url:"${pageContext.request.contextPath}/article/edit",
            type:"post",
            data:$("#article-form").serialize(),
            dataType:"json",
            success:function () {
                //    关闭模态框
                $("#article-modal").modal("hide");
                //    刷新jqgrid
                $("#article-table").trigger("reloadGrid");
            }
        })

    }


</script>








<div>
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">所有文章</a></li>
        <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab" onclick="operModal('add')">添加文章</a></li>
    </ul>
</div>
<table id="article-table"></table>
<div id="article-pager" style="height: 40px"></div>


<%--添加文章的模态框--%>
<div class="modal fade" tabindex="-1" role="dialog" id="article-modal">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="width: 702px;">
            <%--头--%>
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">我的文章</h4>
            </div>
            <%--内容--%>
            <div class="modal-body">


                <form class="form-horizontal" id="article-form">
                    <input type="hidden" id="article-id" name="id">
                    <input type="hidden" id="article-oper" name="oper">

                    <%--输入文章标题--%>
                    <div class="form-group">
                        <label for="article-title" class="col-sm-3 control-label">文章标题：</label>
                        <div class="col-sm-9">
                            <input type="text" name="title" class="form-control" id="article-title" placeholder="文章标题">
                        </div>
                    </div>
                    <%--输入文章作者--%>
                    <div class="form-group">
                        <label for="article-author" class="col-sm-3 control-label">文章作者：</label>
                        <div class="col-sm-9">
                            <input type="text" name="author" class="form-control" id="article-author" placeholder="文章作者">
                        </div>
                    </div>
                    <div class="form-group">
                        <%--输入文章内容--%>
                        <%--kindeditor--%>
                        <textarea id="editor_id" name="content" style="width:700px;height:300px;"></textarea>
                    </div>
                </form>


            </div>
            <%--脚--%>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="dealSave()">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->






