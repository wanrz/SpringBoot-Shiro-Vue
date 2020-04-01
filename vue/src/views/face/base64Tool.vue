<template>
  <div class="app-container">
    <div class="clearfix">
      <div class="imgleft">
        <el-upload
          ref="myphoto"
          :action="uploadUrl"
          list-type="picture-card"
          :file-list="fileList"
          :class="{hide:hideUpload}"
          :on-preview="handlePictureCardPreview"
          :on-success="handleAvatarSuccess"
          :on-remove="handleRemove"
          :on-change="handleChange"
          :limit="1"
        >
          <i class="el-icon-plus"></i>
        </el-upload>
        <el-dialog :visible.sync="dialogVisible">
          <img width="100%" :src="dialogImageUrl" alt />
        </el-dialog>
      </div>
      <div class="imgright">{{imgBase64}}</div>
    </div>
  </div>
</template>
<script>
// const baseurl = process.env.BASE_URL;
const baseurl = "http://10.128.134.25:9090";
export default {
  components: {},
  data() {
    return {
      imgBase64: "",
      hideUpload: false, // 是否隐藏上传按钮
      limitCount: 1, //上传图片数量限制
      fileList: [], // 上传的文件列表
      uploadUrl: baseurl + "/file/upload",
      dialogImageUrl: "",
      dialogVisible: false
    };
  },
  created() {
    this.fileList = [];
  },
  methods: {
    //上传组件相关事件方法
    handleRemove(file, fileList) {
      //删除图片事件
      console.log(file, fileList);
      this.imgBase64 = "";
      this.hideUpload = fileList.length >= this.limitCount;
    },
    handlePictureCardPreview(file) {
      //欲上传图片事件
      debugger;
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleAvatarSuccess(res, file) {
      //上传图片成功后事件
      // this.addList.imgPath = res.data.fileName;
      this.imgBase64 = res.imgBase64;
      this.dialogVisible = false;
      this.$message({
        message: "操作成功",
        type: "success"
      });
    },
    handleChange(file, fileList) {
      //chang事件,上述3个事件均会触发
      this.hideUpload = fileList.length >= this.limitCount;
    }
  }
};
</script>

<style>
.imgleft {
  height: 300px;
  width: 300px;
  float: left;
  margin: 20px 0 0 0;
}

.imgleft /deep/ .el-upload--picture-card {
  width: 300px;
  height: 300px;
}
.imgleft /deep/ .el-upload {
  width: 300px;
  height: 300px;
  line-height: 300px;
}

.imgleft /deep/ .avatar {
  width: 300px;
  height: 300px;
  display: block;
}
.el-upload-list--picture-card .el-upload-list__item {
  overflow: hidden;
  background-color: #fff;
  border: 1px solid #c0ccda;
  border-radius: 6px;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  width: 300px;
  height: 300px;
  margin: 0 8px 8px 0;
  display: inline-block;
}

.hide .el-upload--picture-card {
  display: none;
}

.imgright {
  border: 1px solid gainsboro;
  height: 300px;
  width: 1300px;
  float: left;
  margin: 20px;
  overflow-x: hidden;
  word-break: break-all;
}
.clearfix:after {
  /*伪元素是行内元素 正常浏览器清除浮动方法*/
  content: "";
  display: block;
  height: 0;
  clear: both;
  visibility: hidden;
}
.clearfix {
  *zoom: 1; /*ie6清除浮动的方式 *号只有IE6-IE7执行，其他浏览器不执行*/
}
</style>
