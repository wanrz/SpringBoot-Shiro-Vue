webpackJsonp([6],{"zX9/":function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var a={render:function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"app-container"},[i("div",{staticClass:"filter-container"},[i("el-form",[i("el-form-item",[t.hasPerm("article:add")?i("el-button",{attrs:{type:"primary",icon:"plus"},on:{click:t.showCreate}},[t._v("添加\n        ")]):t._e()],1)],1)],1),t._v(" "),i("el-table",{directives:[{name:"loading",rawName:"v-loading.body",value:t.listLoading,expression:"listLoading",modifiers:{body:!0}}],attrs:{data:t.list,"element-loading-text":"拼命加载中",border:"",fit:"","highlight-current-row":""}},[i("el-table-column",{attrs:{align:"center",label:"序号",width:"80"},scopedSlots:t._u([{key:"default",fn:function(e){return[i("span",{domProps:{textContent:t._s(t.getIndex(e.$index))}})]}}])}),t._v(" "),i("el-table-column",{staticStyle:{width:"60px"},attrs:{align:"center",prop:"content",label:"接口名称"}}),t._v(" "),i("el-table-column",{attrs:{align:"center",label:"创建时间",width:"170"},scopedSlots:t._u([{key:"default",fn:function(e){return[i("span",[t._v(t._s(e.row.createTime))])]}}])}),t._v(" "),t.hasPerm("article:update")?i("el-table-column",{attrs:{align:"center",label:"管理",width:"200"},scopedSlots:t._u([{key:"default",fn:function(e){return[i("el-button",{attrs:{type:"primary",icon:"edit"},on:{click:function(i){t.showUpdate(e.$index)}}},[t._v("修改")])]}}])}):t._e()],1),t._v(" "),i("el-pagination",{attrs:{"current-page":t.listQuery.pageNum,"page-size":t.listQuery.pageRow,total:t.totalCount,"page-sizes":[10,20,50,100],layout:"total, sizes, prev, pager, next, jumper"},on:{"size-change":t.handleSizeChange,"current-change":t.handleCurrentChange}}),t._v(" "),i("el-dialog",{attrs:{title:t.textMap[t.dialogStatus],visible:t.dialogFormVisible},on:{"update:visible":function(e){t.dialogFormVisible=e}}},[i("el-form",{staticClass:"small-space",staticStyle:{width:"300px","margin-left":"50px"},attrs:{model:t.tempArticle,"label-position":"left","label-width":"60px"}},[i("el-form-item",{attrs:{label:"接口名称"}},[i("el-input",{attrs:{type:"text"},model:{value:t.tempArticle.content,callback:function(e){t.$set(t.tempArticle,"content",e)},expression:"tempArticle.content"}})],1)],1),t._v(" "),i("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[i("el-button",{on:{click:function(e){t.dialogFormVisible=!1}}},[t._v("取 消")]),t._v(" "),"create"==t.dialogStatus?i("el-button",{attrs:{type:"success"},on:{click:t.createArticle}},[t._v("创 建")]):i("el-button",{attrs:{type:"primary"},on:{click:t.updateArticle}},[t._v("修 改")])],1)],1)],1)},staticRenderFns:[]},l=i("/Xao")({data:function(){return{totalCount:0,list:[],listLoading:!1,listQuery:{pageNum:1,pageRow:50,name:""},dialogStatus:"create",dialogFormVisible:!1,textMap:{update:"编辑",create:"创建文章"},tempArticle:{id:"",content:""}}},created:function(){this.getList()},methods:{getList:function(){var t=this;this.hasPerm("article:list")&&(this.listLoading=!0,this.api({url:"/article/listArticle",method:"get",params:this.listQuery}).then(function(e){t.listLoading=!1,t.list=e.list,t.totalCount=e.totalCount}))},handleSizeChange:function(t){this.listQuery.pageRow=t,this.handleFilter()},handleCurrentChange:function(t){this.listQuery.pageNum=t,this.getList()},getIndex:function(t){return(this.listQuery.pageNum-1)*this.listQuery.pageRow+t+1},showCreate:function(){this.tempArticle.content="",this.dialogStatus="create",this.dialogFormVisible=!0},showUpdate:function(t){this.tempArticle.id=this.list[t].id,this.tempArticle.content=this.list[t].content,this.dialogStatus="update",this.dialogFormVisible=!0},createArticle:function(){var t=this;this.api({url:"/article/addArticle",method:"post",data:this.tempArticle}).then(function(){t.getList(),t.dialogFormVisible=!1})},updateArticle:function(){var t=this;this.api({url:"/article/updateArticle",method:"post",data:this.tempArticle}).then(function(){t.getList(),t.dialogFormVisible=!1})}}},a,!1,null,null,null);e.default=l.exports}});