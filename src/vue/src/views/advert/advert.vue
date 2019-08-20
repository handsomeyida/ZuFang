<template>
  <div>
    <el-row>
      <h2 class="text-left" style="color: #409eff;">广告栏图片上传：</h2>
      <el-col :span="8" :offset="8">
        <div id="upload2">
          <el-form ref="form2" :model="form2" label-width="80px">
            <el-form-item label="广告标题">
              <el-input v-model="form2.name" name="names" style="width:360px;"></el-input>
            </el-form-item>
            <el-form-item label-width="80px" label="上传图片">
            　<el-upload class="upload-demo" action="" :limit=5 :auto-upload=false :on-change="onchange2" :on-remove="handleRemove2" :file-list="fileList2" :multiple=true list-type="picture">
                <el-button size="small" type="primary">选择图片</el-button>
            　</el-upload>
            </el-form-item>
            <el-form-item style="padding-top:20px;" >
              <el-button type="primary" @click="onSubmit2">立即创建</el-button>
              <el-button>取消</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  export default {
    name: "advert",

    data() {
      return {
        fileList2:[],
        form2: {
          name: ''
        },
        params2:{},
      };
    },

    methods: {
      onchange2(file,filesList){
        this.param2 = new FormData();
        if (filesList.length >= 5) {
          this.$message.error("上传图片已达到5张,若要继续上传请删除某张图片!!");
        }
        else {
          this.fileList2 = filesList;
        }
      },

      handleRemove2(file,filesList){
        this.param2.delete('file')
      },

      onSubmit2(){//表单提交的事件
        if(this.fileList2.length>0){
          var names = this.form2.name;
          this.param2.append('AdvertTitle', names);

          let config = {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          };
          for (var i = 0; i < this.fileList2.length; i++) {
            this.param2.append('file', this.fileList2[i].raw, this.fileList2[i].name);
          }

          let _vue = this;
          _vue.$reqs.post("api/upload/imgUpload", this.param2, config).then(function(data) {
            if (data.data == "上传成功") {
              _vue.$message({
                message: '上传成功',
                type: 'success',
                onClose: function () {
                  location.reload();
                }
              });
            } else {
              _vue.$message.error(data.data);
            }
          })
        } else {
          this.$message.error("未选择上传图片,若要继续上传请选择某张图片!!");
        }
      },

    }
  }
</script>

<style scoped>
  .el-upload-list--picture .el-upload-list__item{
    width: 360px;
  }
</style>
