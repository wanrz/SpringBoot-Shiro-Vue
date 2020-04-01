<template>
  <div class="app-container">
    <div class="clearfix">
      <div class="imgleft">
        <el-upload
          ref="myphoto"
          :action="uploadUrl"
          list-type="picture-card"
          :file-list="fileListA"
          :class="{hide:hideUploadA}"
          :on-preview="handlePictureCardPreviewA"
          :on-success="handleAvatarSuccessA"
          :on-remove="handleRemoveA"
          :on-change="handleChangeA"
          :limit="1"
        >
          <i class="el-icon-plus"></i>
        </el-upload>
        <el-dialog :visible.sync="dialogVisibleA">
          <img width="100%" :src="dialogImageUrlA" alt />
        </el-dialog>
      </div>
      <div class="imgleft">
        <el-upload
          ref="myphoto"
          :action="uploadUrl"
          list-type="picture-card"
          :file-list="fileListB"
          :class="{hide:hideUploadB}"
          :on-preview="handlePictureCardPreviewB"
          :on-success="handleAvatarSuccessB"
          :on-remove="handleRemoveB"
          :on-change="handleChangeB"
          :limit="1"
        >
          <i class="el-icon-plus"></i>
        </el-upload>
        <el-dialog :visible.sync="dialogVisibleB">
          <img width="100%" :src="dialogImageUrlB" alt />
        </el-dialog>
      </div>
      <div class="imgright-x">{{result}}</div>
    </div>
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-button type="primary" icon="plus" @click="facecomepile">人脸比对</el-button>
        </el-form-item>
      </el-form>
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
      imgA: "",
      imgB: "",
      result: "",
      hideUploadA: false, // 是否隐藏上传按钮
      limitCountA: 1, //上传图片数量限制
      fileListA: [], // 上传的文件列表
      dialogImageUrlA: "",
      dialogVisibleA: false,

      hideUploadB: false, // 是否隐藏上传按钮
      limitCountB: 1, //上传图片数量限制
      fileListB: [], // 上传的文件列表
      dialogImageUrlB: "",
      dialogVisibleB: false,

      uploadUrl: baseurl + "/file/upload"
    };
  },
  created() {
    this.fileList = [];
  },
  methods: {
    facecomepile() {
      //提取特征
      this.api({
        url: "/icbc/computeSimilarity",
        method: "post",
        data: { imgA: this.imgA, imgB: this.imgB }
      }).then(res => {
        this.result = res;
        this.$message({
          message: "操作成功",
          type: "success"
        });
      });
    },
    //上传组件相关事件方法
    handleRemoveA(file, fileListA) {
      //删除图片事件
      this.imgA = "";
      THIS.result = "";
      console.log(file, fileListA);
      this.hideUploadA = fileListA.length >= this.limitCountA;
    },
    handlePictureCardPreviewA(file) {
      //欲上传图片事件
      this.dialogImageUrlA = file.url;
      this.dialogVisibleA = true;
    },
    handleAvatarSuccessA(res, file) {
      //上传图片成功后事件
      // this.addList.imgPath = res.data.fileName;
      this.imgA = res.imgBase64;
      this.dialogVisibleA = false;
    },
    handleChangeA(file, fileListA) {
      //chang事件,上述3个事件均会触发
      debugger;
      this.hideUploadA = fileListA.length >= this.limitCountA;
    },

    //上传组件相关事件方法
    handleRemoveB(file, fileListB) {
      //删除图片事件
      this.imgB = "";
      THIS.result = "";
      console.log(file, fileListB);
      this.hideUploadB = fileListB.length >= this.limitCountB;
    },
    handlePictureCardPreviewB(file) {
      //欲上传图片事件
      this.dialogImageUrlB = file.url;
      this.dialogVisibleB = true;
    },
    handleAvatarSuccessB(res, file) {
      //上传图片成功后事件
      // this.addList.imgPath = res.data.fileName;
      this.imgB = res.imgBase64;
      this.dialogVisibleB = false;
    },
    handleChangeB(file, fileListB) {
      //chang事件,上述3个事件均会触发
      this.hideUploadB = fileListB.length >= this.limitCountB;
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

.imgright-x {
  border: 1px solid gainsboro;
  height: 300px;
  width: 800px;
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
