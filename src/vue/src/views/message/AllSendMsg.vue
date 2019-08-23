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
      <el-button type="success" @click="loadMessage">发送消息</el-button>
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
      <el-form class="small-space" :model="MsgUser" label-position="left" label-width="120px"
               style='width: 300px; margin-left:50px;'>
        <el-form-item label="选择发送人员" required v-if="dialogStatus=='send'">
          <el-select v-model="MsgUser.userId" placeholder="请选择人员" style="width: 670px">
            <el-option
              v-for="item in user"
              v-if="item.delete_status != 2"
              :key="item.id"
              :label="item.nickname"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-if="dialogStatus=='send'" type="success" @click="AllsendMessage">群发</el-button>
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='send'" type="primary" @click="sendMessage">发送</el-button>
        <el-button type="primary" v-else @click="dialogFormVisible = false">选择</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "AllSendMsg",
    data() {
      return {
        textarea: '',
        userId: '',
        user: [],//角色列表
        template: [],//模板列表
        dialogStatus: 'select',
        dialogFormVisible: false,
        textMap: {
          select: '选择模板',
          send: '发送消息'
        },
        MsgUser: {
          userId: '',
          username: '',
          nickname: '',
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
      this.GetAllUser();
      this.GetAllTemplet();
    },
    methods: {
      useTemplate() {
        //显示新增对话框
        this.dialogStatus = "select"
        this.dialogFormVisible = true
      },
      loadMessage() {
        this.dialogStatus = "send"
        this.dialogFormVisible = true
      },
      AllsendMessage() {
        let userIds=[];
        let users=this.user;
        for (let a=0;a<users.length;a++) {
          userIds.push(users[a].id);
        }
        this.userId = this.$store.state.user.userId;
        this.MsgTemplate.CONTENT = this.textarea;
        this.api({
          url: "/information/insertAllInformation",
          method: "post",
          data: {
            userIds: userIds,
            userId: this.userId,
            targetId: "system_info",
            content: this.MsgTemplate.CONTENT
          }
        }).then(() => {
          this.dialogFormVisible = false;
          this.$message({
            message: '发送消息成功',
            type: 'success',
            onClose: function () {
              location.reload();
            }
          });
        })
      },
      sendMessage() {
        this.userId = this.$store.state.user.userId;
        this.MsgTemplate.CONTENT = this.textarea;
        // console.log(this.MsgUser.userId);
        // console.log(this.userId);
        // console.log(this.MsgTemplate.CONTENT);
        this.api({
          url: "/information/insertInformation",
          method: "post",
          data: {
            userIds: this.MsgUser.userId,
            userId: this.userId,
            targetId: this.MsgUser.userId,
            content: this.MsgTemplate.CONTENT
          }
        }).then(() => {
          this.dialogFormVisible = false;
          this.$message({
            message: '发送消息成功',
            type: 'success',
            onClose: function () {
              location.reload();
            }
          });
        })
      },
      loadtextarea(selVal) {
        let templates = this.template
        for (let a=0;a<templates.length;a++){
          if (templates[a].ID==selVal) {
            // console.log(templates[a].CONTENT)
            this.textarea = templates[a].CONTENT;
          }
        }
      },
      GetAllUser() {
        this.api({
          url: "/backstageuser/loadlist",
          method: "post"
        }).then(data => {
          this.user = data.list;
        })
      },
      GetAllTemplet() {
        this.api({
          url: "/templet/listtemplet",
          method: "post"
        }).then(data => {
          this.template = data.list;
        })
      },
    }
  }
</script>

<style scoped>

</style>
