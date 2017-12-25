<!DOCTYPE html>
<html>
    <head>
        <title>新建  - ${r'${title}'}</title>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=8,9,10" />
        <link rel="stylesheet" type="text/css" href="${r'${requestContext.contextPath}'}/resources/themes/${r'${skinName}'}/base.min.css">
        <link rel="stylesheet" type="text/css" href="${r'${requestContext.contextPath}'}/resources/plugins/jquery-easyui-${r'${easyuiVersion}'}/themes/${r'${easyuiSkinName}'}/easyui.min.css">
        <link rel="stylesheet" type="text/css" href="${r'${requestContext.contextPath}'}/resources/plugins/jquery-easyui-${r'${easyuiVersion}'}/themes/icon.min.css">
        <script type="text/javascript">
            //呈现顶端遮罩
            if (top.showMask) top.showMask();
        </script>
    </head>
    <body class="easyui-layout" style="visibility:hidden">
        <div data-options="region:'center',border:false" style="padding:0px;">
           <form class="form" style="margin:0; padding:0; visibility:hidden" method="post" onsubmit="return false;">
            	<table cellspacing="5" cellpadding="0">
	           		<#list context.fields as field>
	           		<tr>
	           			<td class="field" colspan="2">
	           				<label class="key" style="width:100px"><#if field.required=true><span>*</span></#if>${field.meaning}：</label>
	           				<input  type="text" id="${field.fieldName}" name="${field.fieldName}" style="width: 100px" class="input easyui-validatebox" data-options="validType:'text[${field.minLen},${field.maxLen}]'<#if field.required=true>,required:true</#if>" />
	           			</td>
	           		</tr>
					</#list>
            	</table>
            </form>
        </div>
        <div data-options="region:'south',border:false" style="height:35px;text-align:right; padding:5px 5px 0 0;background-color:#efefef;">
            <a id="btnInsert" href="javascript:void(0)" class="easyui-linkbutton" data-options="disabled:true">保存</a>
            <a id="btnAppend" href="javascript:void(0)" class="easyui-linkbutton" data-options="disabled:true">保存并新建</a>
        </div>
		
        <script type="text/javascript" src="${r'${requestContext.contextPath}'}/resources/scripts/jquery-${r'${jqueryVersion}'}.min.js"></script>
        <script type="text/javascript" src="${r'${requestContext.contextPath}'}/resources/plugins/jquery-easyui-${r'${easyuiVersion}'}/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="${r'${requestContext.contextPath}'}/resources/plugins/jquery-easyui-${r'${easyuiVersion}'}/locale/easyui-lang-zh_CN.js"></script>
        <script type="text/javascript" src="${r'${requestContext.contextPath}'}/resources/scripts/jquery-domain.js"></script>
        <script type="text/javascript" src="${r'${requestContext.contextPath}'}/resources/scripts/jquery-boomhope.js"></script>
        
        <script type="text/javascript">
            $(function(){
                //解析页面
                $.parser.parse();
                //移除顶端遮罩
                if (top.hideMask){top.hideMask();}
                var t = $('form.form');
                t.css({ visibility: 'visible' });
                $('body').css({ visibility: 'visible' });
                $('#btnInsert')[0].onclick = function() { insert('save'); }
                $('#btnAppend')[0].onclick = function() { insert('append'); }
                $('#btnInsert,#btnAppend').linkbutton('enable');
                
                function insert(action) {
                    var t = $('form.form');
                    var pdata = t.domain('collect');
                    if(t.form("validate") && pdata) {
                        $('#btnInsert,#btnAppend').linkbutton('disable');
                        $.boomhope.ajax({
                        	url: '${r'${requestContext.contextPath}'}/xxx/${context.simpleName?lower_case}/doadd',
                        	data:pdata,
                        	success : function(data){
                        		$('#btnInsert,#btnAppend').linkbutton('enable');
    							if (action == 'save') {
                                    $(parent).domain('closeDialog');
                                }
                                else if (action == 'append') {
                                	if (data=='1')
        								$.messager.alert("提示", "添加成功", "ok", null, 2000);
                                    $('form.form')[0].reset();
                                }
    						},
    						onException : function(data){
    							$('#btnInsert,#btnAppend').linkbutton('enable');
    						},
    						fail : function(data){
                        		$('#btnInsert,#btnAppend').linkbutton('enable');
    						},
    						error : function(data){
    							$('#btnInsert,#btnAppend').linkbutton('enable');
    						}
                        });
                    }
                }
            });
        </script>
    </body>
</html>
