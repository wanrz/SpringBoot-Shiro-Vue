webpackJsonp([3],{Vt2S:function(i,e,n){(i.exports=n("BkJT")(!0)).push([i.i,'\n.imgleft {\r\n  height: 300px;\r\n  width: 300px;\r\n  float: left;\r\n  margin: 20px 0 0 0;\n}\n.imgleft /deep/ .el-upload--picture-card {\r\n  width: 300px;\r\n  height: 300px;\n}\n.imgleft /deep/ .el-upload {\r\n  width: 300px;\r\n  height: 300px;\r\n  line-height: 300px;\n}\n.imgleft /deep/ .avatar {\r\n  width: 300px;\r\n  height: 300px;\r\n  display: block;\n}\n.el-upload-list--picture-card .el-upload-list__item {\r\n  overflow: hidden;\r\n  background-color: #fff;\r\n  border: 1px solid #c0ccda;\r\n  border-radius: 6px;\r\n  -webkit-box-sizing: border-box;\r\n  box-sizing: border-box;\r\n  width: 300px;\r\n  height: 300px;\r\n  margin: 0 8px 8px 0;\r\n  display: inline-block;\n}\n.hide .el-upload--picture-card {\r\n  display: none;\n}\n.imgright-x {\r\n  border: 1px solid gainsboro;\r\n  height: 300px;\r\n  width: 800px;\r\n  float: left;\r\n  margin: 20px;\r\n  overflow-x: hidden;\r\n  word-break: break-all;\n}\n.clearfix:after {\r\n  /*伪元素是行内元素 正常浏览器清除浮动方法*/\r\n  content: "";\r\n  display: block;\r\n  height: 0;\r\n  clear: both;\r\n  visibility: hidden;\n}\n.clearfix {\r\n  *zoom: 1; /*ie6清除浮动的方式 *号只有IE6-IE7执行，其他浏览器不执行*/\n}\r\n',"",{version:3,sources:["E:/myown/SpringBoot-Shiro-Vue/vue/src/views/face/facecomepile.vue"],names:[],mappings:";AACA;EACE,cAAc;EACd,aAAa;EACb,YAAY;EACZ,mBAAmB;CACpB;AACD;EACE,aAAa;EACb,cAAc;CACf;AACD;EACE,aAAa;EACb,cAAc;EACd,mBAAmB;CACpB;AACD;EACE,aAAa;EACb,cAAc;EACd,eAAe;CAChB;AACD;EACE,iBAAiB;EACjB,uBAAuB;EACvB,0BAA0B;EAC1B,mBAAmB;EACnB,+BAA+B;EAC/B,uBAAuB;EACvB,aAAa;EACb,cAAc;EACd,oBAAoB;EACpB,sBAAsB;CACvB;AACD;EACE,cAAc;CACf;AACD;EACE,4BAA4B;EAC5B,cAAc;EACd,aAAa;EACb,YAAY;EACZ,aAAa;EACb,mBAAmB;EACnB,sBAAsB;CACvB;AACD;EACE,wBAAwB;EACxB,YAAY;EACZ,eAAe;EACf,UAAU;EACV,YAAY;EACZ,mBAAmB;CACpB;AACD;GACE,QAAS,CAAC,qCAAqC;CAChD",file:"facecomepile.vue",sourcesContent:['\n.imgleft {\r\n  height: 300px;\r\n  width: 300px;\r\n  float: left;\r\n  margin: 20px 0 0 0;\n}\n.imgleft /deep/ .el-upload--picture-card {\r\n  width: 300px;\r\n  height: 300px;\n}\n.imgleft /deep/ .el-upload {\r\n  width: 300px;\r\n  height: 300px;\r\n  line-height: 300px;\n}\n.imgleft /deep/ .avatar {\r\n  width: 300px;\r\n  height: 300px;\r\n  display: block;\n}\n.el-upload-list--picture-card .el-upload-list__item {\r\n  overflow: hidden;\r\n  background-color: #fff;\r\n  border: 1px solid #c0ccda;\r\n  border-radius: 6px;\r\n  -webkit-box-sizing: border-box;\r\n  box-sizing: border-box;\r\n  width: 300px;\r\n  height: 300px;\r\n  margin: 0 8px 8px 0;\r\n  display: inline-block;\n}\n.hide .el-upload--picture-card {\r\n  display: none;\n}\n.imgright-x {\r\n  border: 1px solid gainsboro;\r\n  height: 300px;\r\n  width: 800px;\r\n  float: left;\r\n  margin: 20px;\r\n  overflow-x: hidden;\r\n  word-break: break-all;\n}\n.clearfix:after {\r\n  /*伪元素是行内元素 正常浏览器清除浮动方法*/\r\n  content: "";\r\n  display: block;\r\n  height: 0;\r\n  clear: both;\r\n  visibility: hidden;\n}\n.clearfix {\r\n  *zoom: 1; /*ie6清除浮动的方式 *号只有IE6-IE7执行，其他浏览器不执行*/\n}\r\n'],sourceRoot:""}])},dtGy:function(i,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var t=config.picpath,l={components:{},data:function(){return{imgA:"",imgB:"",result:"",hideUploadA:!1,limitCountA:1,fileListA:[],dialogImageUrlA:"",dialogVisibleA:!1,hideUploadB:!1,limitCountB:1,fileListB:[],dialogImageUrlB:"",dialogVisibleB:!1,uploadUrl:t+"/file/upload"}},created:function(){this.fileList=[]},methods:{facecomepile:function(){var i=this;this.api({url:"/icbc/computeSimilarity",method:"post",data:{imgA:this.imgA,imgB:this.imgB}}).then(function(e){i.result=e,i.$message({message:"操作成功",type:"success"})})},handleRemoveA:function(i,e){this.imgA="",this.result="",console.log(i,e),this.hideUploadA=e.length>=this.limitCountA},handlePictureCardPreviewA:function(i){this.dialogImageUrlA=i.url,this.dialogVisibleA=!0},handleAvatarSuccessA:function(i,e){this.imgA=i.imgBase64,this.dialogVisibleA=!1},handleChangeA:function(i,e){this.hideUploadA=e.length>=this.limitCountA},handleRemoveB:function(i,e){this.imgB="",this.result="",console.log(i,e),this.hideUploadB=e.length>=this.limitCountB},handlePictureCardPreviewB:function(i){this.dialogImageUrlB=i.url,this.dialogVisibleB=!0},handleAvatarSuccessB:function(i,e){this.imgB=i.imgBase64,this.dialogVisibleB=!1},handleChangeB:function(i,e){this.hideUploadB=e.length>=this.limitCountB}}},r={render:function(){var i=this,e=i.$createElement,n=i._self._c||e;return n("div",{staticClass:"app-container"},[n("div",{staticClass:"clearfix"},[n("div",{staticClass:"imgleft"},[n("el-upload",{ref:"myphoto",class:{hide:i.hideUploadA},attrs:{action:i.uploadUrl,"list-type":"picture-card","file-list":i.fileListA,"on-preview":i.handlePictureCardPreviewA,"on-success":i.handleAvatarSuccessA,"on-remove":i.handleRemoveA,"on-change":i.handleChangeA,limit:1}},[n("i",{staticClass:"el-icon-plus"})]),i._v(" "),n("el-dialog",{attrs:{visible:i.dialogVisibleA},on:{"update:visible":function(e){i.dialogVisibleA=e}}},[n("img",{attrs:{width:"100%",src:i.dialogImageUrlA,alt:""}})])],1),i._v(" "),n("div",{staticClass:"imgleft"},[n("el-upload",{ref:"myphoto",class:{hide:i.hideUploadB},attrs:{action:i.uploadUrl,"list-type":"picture-card","file-list":i.fileListB,"on-preview":i.handlePictureCardPreviewB,"on-success":i.handleAvatarSuccessB,"on-remove":i.handleRemoveB,"on-change":i.handleChangeB,limit:1}},[n("i",{staticClass:"el-icon-plus"})]),i._v(" "),n("el-dialog",{attrs:{visible:i.dialogVisibleB},on:{"update:visible":function(e){i.dialogVisibleB=e}}},[n("img",{attrs:{width:"100%",src:i.dialogImageUrlB,alt:""}})])],1),i._v(" "),n("div",{staticClass:"imgright-x"},[i._v(i._s(i.result))])]),i._v(" "),n("div",{staticClass:"filter-container"},[n("el-form",[n("el-form-item",[n("el-button",{attrs:{type:"primary",icon:"plus"},on:{click:i.facecomepile}},[i._v("人脸比对")])],1)],1)],1)])},staticRenderFns:[]};var a=n("/Xao")(l,r,!1,function(i){n("z223")},null,null);e.default=a.exports},z223:function(i,e,n){var t=n("Vt2S");"string"==typeof t&&(t=[[i.i,t,""]]),t.locals&&(i.exports=t.locals);n("8bSs")("8d141932",t,!0)}});