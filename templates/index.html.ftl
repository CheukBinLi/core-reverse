<!DOCTYPE html>
<html>
    <head>
        <title> 管理 - ${r'${title}'}</title>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=8,9,10" />
        <link rel="stylesheet" type="text/css" href="${r'${requestContext.contextPath}'}/resources/themes/${r'${skinName}'}/base.min.css">
        <link rel="stylesheet" type="text/css" href="${r'${requestContext.contextPath}'}/resources/plugins/jquery-easyui-${r'${easyuiVersion}'}/themes/${r'${easyuiSkinName}'}/easyui.min.css">
        <link rel="stylesheet" type="text/css" href="${r'${requestContext.contextPath}'}/resources/plugins/jquery-easyui-${r'${easyuiVersion}'}/themes/icon.min.css">
        <link rel="stylesheet" type="text/css" href="${r'${requestContext.contextPath}'}/resources/plugins/qtips2.0/jquery.qtip.min.css">
        <link rel="stylesheet" type="text/css" href="${r'${requestContext.contextPath}'}/resources/themes/icon.min.css">
        <script type="text/javascript">
            //呈现顶端遮罩
            if (top.showMask) top.showMask();
        </script>
    </head>
    <body class="easyui-layout" style="visibility:hidden;background: #006699;">
        <div data-options="region:'north',border:false,split:true,marginBottom:5" style="padding:0px; height:70px;width:auto;">
            <form class="query" style="margin:0; padding:0;" onsubmit="return false;">	
	        	<div style="padding-bottom: 2px;padding-left: 5px;padding-right: 5px;">
	                <div style="border-bottom: 1px #ddd solid;">
	                    <div>
	                        <img src="${r'${requestContext.contextPath}'}/resources/data/images/system_search.png" style="vertical-align: bottom;"/>
	                        <span style="font-size: 14px;font-weight: bold;">搜索</span>
	                    </div>
	                </div>
	            </div>
            	<table cellspacing="5" cellpadding="0">
	           		<tr>
	           			<#list context.fields as field>
	           			<td class="field">
	           				<label class="key" style="width:70px">${field.meaning}：</label>
	           				<input id="${field.fieldName}" name="${field.fieldName}" type="text" style="width: 120px" class="input easyui-validatebox" data-options="validType:'text[1,10]'" />
	           			</td>
						</#list>
	           			<td class="field" style="float:right">
	           				<a href="javascript:void(0)" id="btnSearch" class="easyui-linkbutton easyui-linkbutton-customized" data-options="iconCls:'icon-search', plain:false,disabled:false">查询</a>
                    		<a href="javascript:void(0)" id="btnReset" class="easyui-linkbutton easyui-linkbutton-customized" data-options="plain:false,disabled:false" style="margin-top:2px">重置</a>
	           			</td>
	           		</tr>
            	</table>
            </form>
        </div>
        <div data-options="region:'center',border:false" style="padding:0px;">
            <table class="datagrid"></table>
        </div>
        <!-- 表格工具条 -->
        <div id="toolbar" style="padding-top: 5px;padding-left: 5px;">
            <a href="javascript:void(0)" id="btnInsert" class="easyui-linkbutton" onClick="insertRecord()" data-options="iconCls:'icon-add',plain:false,disabled:false">新建</a>
        </div>
		<!-- 表格浮动工具条 -->
        <div id="actionDiv" style="text-align: center">
            <a href="javascript:void(0)" id="btnUpdate" class="easyui-linkbutton easyui-linkbutton-customized" onClick="updateRecord()" data-options="iconCls:'icon-edit',plain:false,disabled:false">编辑</a>
            <a href="javascript:void(0)" id="btnDelete" class="easyui-linkbutton easyui-linkbutton-customized" onClick="deleteRecord()" data-options="iconCls:'icon-remove',plain:false,disabled:false">删除</a>
        </div>
		
        <script type="text/javascript" src="${r'${requestContext.contextPath}'}/resources/scripts/jquery-${r'${jqueryVersion}'}.min.js"></script>
        <script type="text/javascript" src="${r'${requestContext.contextPath}'}/resources/plugins/jquery-easyui-${r'${easyuiVersion}'}/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="${r'${requestContext.contextPath}'}/resources/plugins/jquery-easyui-${r'${easyuiVersion}'}/locale/easyui-lang-zh_CN.js"></script>
        <script type="text/javascript" src="${r'${requestContext.contextPath}'}/resources/scripts/json2.js"></script>
        <script type="text/javascript" src="${r'${requestContext.contextPath}'}/resources/scripts/jquery-domain.js"></script>
        <script type="text/javascript" src="${r'${requestContext.contextPath}'}/resources/plugins/qtips2.0/jquery.qtip.min.js"></script>
        <script type="text/javascript" src="${r'${requestContext.contextPath}'}/resources/plugins/jquery.corner.js"></script>
		<script type="text/javascript" src="${r'${requestContext.contextPath}'}/resources/scripts/commons.js"></script>
		<script type="text/javascript" src="${r'${requestContext.contextPath}'}/resources/scripts/format.js"></script>
		<script type="text/javascript" src="${r'${requestContext.contextPath}'}/resources/scripts/jquery-boomhope.js"></script>
		
        <script type="text/javascript">
            function getDatagrid()
            {
            	var queryParams = $('form.query').domain('collect');
                if(queryParams == false) {
                    return false;
                }
                queryParams.total = -1;
                $("#btnSearch").linkbutton("disable");
                var t = $('table.datagrid');
                if(typeof($.data(t[0], 'datagrid')) == 'undefined') {
                	var toolbar = $.fn.domain.defaults.datagrid.toolbar;
                	t.domain('datagrid', {
                        title: '${context.mean}',
                        url: '${r'${requestContext.contextPath}'}/xxx/${context.simpleName?uncap_first}/datagrid',
                        queryParams: queryParams,
                        toolbar : "#toolbar",
                        frozenColumns : "",
                        singleSelect : true,
                        fitColumns : true,
                        openWidth : 520,
                        openHeight : 400,
                        columns: [[
                        		<#assign i=0/>
                        		<#list context.fields as field>
                        		<#if i gt 0>,</#if>{field: '${field.fieldName}', title: '${field.meaning}', width: 100, sortable: false, align: 'left', hidden: false}
								<#assign i=i+1/>
								</#list>
                            ]],
                        names: [
                        ],
                        onLoadSuccess: function(data, status, XHR) {
                            $("#btnSearch").linkbutton("enable");
                            $(document).unbind(".qtip").bind("mousedown.qtip", function(e) {
                            	if ($(e.target).closest("div.qtip-content").length == 0) {
                            		hideTips();  
                            	}
                    		});
                        },
                        onLoadError: function(XHR, status, errorThrow) {
                            $("#btnSearch").linkbutton("enable");
                        },
                        onSelect : function(index, value){
                            var show = changeButtonsStatus();
                            if (show){
                            	$(".datagrid-view2 .datagrid-row-selected").qtip({
                                      content : {text : $("#actionDiv").clone()},
                                      position : {my : "top center", at : "bottom center"},
                                      events : {show : function(e,api)
                                            {
                                                $(".qtip-tip").remove();
                                                $(".qtip").css("margin-top", "-7px");
                                            }},
                                        show : {event : false},
                                        hide : {event : false}
                                }).qtip("show");
                            }
                        },
                        onUnselect : function(index,value){
                            hideTips();  
                        },
                        onUnselectAll : function(){
                            hideTips();  
                        }
                    });
                }
                else {
    				t.datagrid("load", queryParams);
    			}
            }
            
            function resetQuery()
            {
            	$('form.query').form('clear');
            }
            
            function changeButtonsStatus(){
    			if (top.$(".messager-window").length > 0){
    				return false;
                }
    			return true;
    		}
            
            function hideTips(){
    			$(".datagrid-row").each(function(){
    				$(this).qtip("hide");
    			});  
    		}
            
            function insertRecord()
            {
            	$(parent).domain("openDialog", {
    				iconCls: "", 
    				title: "新建", 
    				src: '${r'${requestContext.contextPath}'}/xxx/${context.simpleName?uncap_first}/add',
    				width: 300,  
    				height:150, 
    				onClose: function() {
    					var t = $('table.datagrid');
    					t.datagrid("reload");
    				}
    			});
            }
            
            function updateRecord()
            {
            	var t = $('table.datagrid');
            	var node = t.datagrid('getSelected');
            	var pkParams = '';
            	<#assign i=0/>
                <#list context.pks as field>
                pkParams += '<#if i gt 0>&</#if>${field.fieldName}=' + node.${field.fieldName};
				<#assign i=i+1/>
				</#list>
            	$(parent).domain("openDialog", {
    				iconCls: "", 
    				title: "更新", 
    				src: '${r'${requestContext.contextPath}'}/xxx/${context.simpleName?uncap_first}/update?'+pkParams,
    				width: 300,  
    				height:150, 
    				onClose: function() {
    					var t = $('table.datagrid');
    					t.datagrid("reload");
    				}
    			});
            }
            
            function deleteRecord()
            {
            	var t = $('table.datagrid');
            	var node = t.datagrid('getSelected');
            	var pdata = {};
                <#list context.pks as field>
                pdata.${field.fieldName} = node.${field.fieldName};
				</#list>
            	var m = "即将删除选中的记录，确定吗？";
    			top.$.messager.confirm("提示", m, function(result) {
    				if (result){
    					$.boomhope.ajax({
    						url: "${r'${requestContext.contextPath}'}/xxx/${context.simpleName?uncap_first}/dodelete",
    						data:pdata,
    						success : function(data){
    							if (data)
    								$.messager.alert("提示", "删除成功 ", "ok", null, 2000);
    							t.datagrid("reload");
    						}
    					});
    				}
    			});
            }
            
            var mouseX, mouseY;
            $(function(){
                //解析页面
                $.parser.parse();
                //显示隐藏页面
                $('body').css({ visibility: 'visible' });
                //移除顶端遮罩
                if (top.hideMask) top.hideMask();
                $('#btnSearch')[0].onclick = function() { getDatagrid(); }
                $('#btnReset')[0].onclick = function() { resetQuery(); }
                //加载数据
                if($.fn.domain.defaults.datagrid.auto) getDatagrid();
                //
                $(document).mousemove(function(e) {
                    mouseX = e.pageX;
                    mouseY = e.pageY;
                }).mouseover();
            });
        </script>
    </body>
</html>
