<template>
  <div style="text-align:center; vertical-align: middle; margin-top: 30px">
    <el-input
      type="textarea"
      :rows="4"
      placeholder="请输入内容"
      v-model="textarea"
      style="width: 800px;">
    </el-input>
    <div style="margin-top: 20px">
      <el-button type="primary" @click="useTemplate">选择模板</el-button>
      <el-button type="success" @click="setMessage" v-if="hasPerm('guide:update')">设置引导语</el-button>
    </div>
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form class="small-space" :model="MsgTemplate" label-position="left" label-width="120px"
               style='width: 300px; margin-left:50px;'>
        <el-form-item label="选择模板" required v-if="dialogStatus=='select'">
          <el-select v-model="MsgTemplate.ID" placeholder="请选择模板" style="width: 670px" @change="loadtextarea">
            <el-option
              v-for="item in template"
              :key="item.ID"
              :label="item.TITLE"
              :value="item.ID">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='select' && hasPerm('guide:update')" type="primary" @click="setIt">设置</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "LeadingWords",
    data() {
      return {
        textarea: '',
        template: [],//模板列表
        dialogStatus: 'select',
        dialogFormVisible: false,
        textMap: {
          select: '选择模板',
          send: '发送消息'
        },
        MsgTemplate: {
          ID: '',
          TITLE: '',
          CONTENT: '',
          IS_NOT_GUIDE: '',
        },
      }
    },
    created() {
      this.GetAllTemplet();
    },
    methods: {
      useTemplate(){
        //显示新增对话框
        this.dialogStatus = "select";
        this.dialogFormVisible = true
      },
      setIt() {
        this.$message.success("模板导入成功!");
        this.dialogFormVisible = false
      },
      setMessage(){
        this.api({
          url: "/information/saveWords",
          method: "post",
          data: {
            content: this.textarea
          }
        }).then(() => {
          this.$message.success("设置引导语成功!");
          this.dialogFormVisible = false
        })
      },
      loadtextarea(selVal) {
        // console.log(selVal)
        let templates = this.template
        for (let a=0;a<templates.length;a++){
          if (templates[a].ID==selVal) {
            // console.log(templates[a].CONTENT)
            this.textarea = templates[a].CONTENT;
          }
        }
      },
      GetAllTemplet() {
        this.api({
          url: "/templet/listtemplet",
          method: "post"
        }).then(data => {
          this.template = data.list;
          // console.log(this.template)
        })
      },
    }
  }
</script>

<style scoped>

</style>
