<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-button type="primary" icon="plus" v-if="hasPerm('template:add')" @click="showCreate">添加模板</el-button>
          <el-button type="success" icon="plus" @click="showSend">发送消息</el-button>
          <el-button type="warning" icon="plus" @click="showWord">设置引导语</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit
              highlight-current-row>
      <el-table-column align="center" label="序号" width="80">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="模板标题" prop="TITLE" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="模板文本" prop="CONTENT" style="width: 200px;"></el-table-column>
      <el-table-column align="center" label="管理" width="220" v-if="hasPerm('template:update')">
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" @click="showUpdate(scope.$index)" v-if="hasPerm('template:update')">修改
          </el-button>
          <el-button type="danger" icon="delete" @click="removeTemplate(scope.$index)"
                     v-if="hasPerm('template:delete')">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="listQuery.pageNum"
      :page-size="listQuery.pageRow"
      :total="totalCount"
      :page-sizes="[10, 20, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form class="small-space" :model="templateList" label-position="left" label-width="80px"
               style='width: 300px; margin-left:50px;' v-if="dialogStatus=='create' || dialogStatus=='update'">
        <div style="text-align:center; vertical-align: middle; margin-top: 30px">
          <el-input type="textarea" :rows="4" placeholder="请输入内容" v-model="templateList.CONTENT" style="width: 800px;">
          </el-input>
        </div>
      </el-form>
      <div style="text-align:center; vertical-align: middle; margin-top: 30px" v-if="dialogStatus=='create' || dialogStatus=='update'">
        <div style="margin-top: 20px">
          <el-select
            v-model="templateList.TITLE"
            :multiple=false
            :clearable=true
            filterable
            allow-create
            default-first-option
            placeholder="请选择(输入)模板类型"
            @change="loadtextarea">
            <el-option
              v-for="item in list"
              :key="item.ID"
              :label="item.TITLE"
              :value="item.TITLE">
            </el-option>
          </el-select>
          <el-button type="success" v-if="dialogStatus=='create'" @click="createTemplate">存为模板</el-button>
          <el-button type="success" v-if="dialogStatus=='update'" @click="updateTemplate">修改模板</el-button>
        </div>
      </div>
      <div style="text-align:center; vertical-align: middle; margin-top: 30px" v-if="dialogStatus=='allsending' || dialogStatus=='leadingWord'">
        <el-input
          type="textarea"
          :rows="4"
          placeholder="请输入内容"
          v-model="textarea"
          style="width: 800px;">
        </el-input>
        <div style="margin-top: 20px">
          <el-button type="primary" @click="useTemplate">选择模板</el-button>
          <el-button type="success" @click="loadMessage" v-if="dialogStatus=='allsending'">发送消息</el-button>
          <el-button type="success" @click="setMessage" v-if="hasPerm('guide:update') && dialogStatus=='leadingWord'">设置引导语</el-button>
        </div>
      </div>
    </el-dialog>

    <el-dialog :title="sendtextMap[senddialogStatus]" :visible.sync="senddialogFormVisible">
      <el-form class="small-space" :model="MsgTemplate" label-position="left" label-width="120px"
               style='width: 300px; margin-left:50px;'>
        <el-form-item label="选择模板" required v-if="senddialogStatus=='select'">
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
        <el-form-item label="选择发送人员" required v-if="senddialogStatus=='send'">
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
        <el-button v-if="senddialogStatus=='send'" type="success" @click="AllsendMessage">群发</el-button>
        <el-button @click="senddialogFormVisible = false">取 消</el-button>
        <el-button v-if="senddialogStatus=='send'" type="primary" @click="sendMessage">发送</el-button>
        <el-button type="primary" v-else @click="senddialogFormVisible = false">选择</el-button>
<!--        <el-button v-if="senddialogStatus=='select' && hasPerm('guide:update')" type="primary" @click="setIt">设置</el-button>-->
      </div>
    </el-dialog>
  </div>
</template>

<script>
    import {mapGetters} from 'vuex'

    export default {
        name: "MsgTemplate",
        data() {
            return {
                totalCount: 0, //分页组件--数据总条数
                list: [],//模板表格的数据
                listLoading: false,//数据加载等待动画
                listQuery: {
                    pageNum: 1,//页码
                    pageRow: 10,//每页条数
                },
                dialogStatus: 'create',
                dialogFormVisible: false,
                textMap: {
                    update: '修改模板',
                    create: '添加模板',
                    leadingWord: '设置引导语',
                    allsending: '发送消息'
                },
                templateList: {
                    ID: '',
                    TITLE: '',
                    CONTENT: '',
                },
                textarea: '',
                userId: '',
                user: [],//角色列表
                template: [],//模板列表
                alltemplate: [],//全部模板列表
                senddialogStatus: 'select',
                senddialogFormVisible: false,
                sendtextMap: {
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
                endtime: '',
                success: '',
                selVal: '',
            }
        },
        created() {
            this.getList();
            this.GetAllUser();
            // this.GetTemplet();
            this.GetAllTemplet();
        },
        methods: {
            getList() {
                this.listLoading = true;
                this.api({
                    url: "/templet/listtemplet",
                    method: "post",
                    params: this.listQuery,
                }).then(data => {
                    this.listLoading = false;
                    for (let a = 0; a < data.list.length; a++) {
                        if (data.list[a].ID != 1) {
                            this.list.push(data.list[a]);
                            this.totalCount = data.totalCount;
                        }
                    }
                })
            },
            handleSizeChange(val) {
                //改变每页数量
                this.listQuery.pageRow = val
                this.handleFilter();
            },
            handleCurrentChange(val) {
                //改变页码
                this.listQuery.pageNum = val
                this.getList();
            },
            handleFilter() {
                //查询事件
                this.listQuery.pageNum = 1
                this.getList()
            },
            getIndex($index) {
                //表格序号
                return (this.listQuery.pageNum - 1) * this.listQuery.pageRow + $index + 1
            },
            showCreate() {
                //显示新增对话框
                this.templateList.ID = "";
                this.templateList.TITLE = "";
                this.templateList.CONTENT = "";
                this.dialogStatus = "create"
                this.dialogFormVisible = true
            },
            showUpdate($index) {
                let template = this.list[$index];
                this.templateList.ID = template.ID;
                this.templateList.TITLE = template.TITLE;
                this.templateList.CONTENT = template.CONTENT;
                this.dialogStatus = "update"
                this.dialogFormVisible = true
            },
            createTemplate() {
                // console.log(this.templateList)
                this.api({
                    url: "/templet/addTemplate",
                    method: "post",
                    data: this.templateList
                }).then(() => {
                    this.getList();
                    this.dialogFormVisible = false
                    this.$message.success("添加模板成功!")
                })
            },
            updateTemplate() {
                // console.log(this.templateList)
                let _vue = this;
                this.api({
                    url: "/templet/updateTemplate",
                    method: "post",
                    data: this.templateList
                }).then(() => {
                    let msg = "修改成功";
                    this.dialogFormVisible = false;
                    this.$message({
                        message: msg,
                        type: 'success',
                        duration: 1 * 1000,
                        onClose: () => {
                            _vue.getList();
                        }
                    })
                })
            },
            removeTemplate($index) {
                let _vue = this;
                // console.log(_vue.list[$index])
                this.$confirm('确定删除此模板?', '提示', {
                    confirmButtonText: '确定',
                    showCancelButton: false,
                    type: 'warning'
                }).then(() => {
                    let user = _vue.list[$index];
                    _vue.api({
                        url: "/templet/deleteTemplate",
                        method: "post",
                        data: user
                    }).then(() => {
                        _vue.$message.success("删除成功")
                        _vue.getList()
                    }).catch(() => {
                        _vue.$message.error("删除失败")
                    })
                })
            },
            showSend() {
                this.dialogStatus = "allsending";
                this.dialogFormVisible = true;
            },
            loadtextarea(selVal) {
                this.selVal = selVal;
                let templates = this.list
                for (let a = 0; a < templates.length; a++) {
                    if (templates[a].ID == selVal) {
                        this.templateList.CONTENT = templates[a].CONTENT;
                    }
                }
            },
            loadendtime() {
                this.userId = this.$store.state.user.userId;
                let _vue = this;
                _vue.api({
                    url: "/comment/loadendtime",
                    method: "post",
                    data: {
                        userId: this.userId
                    }
                }).then((data) => {
                    this.success = data.success;
                    if (data.success != "success") {
                        this.endtime = data.endtime;
                        this.open();
                    }
                })
            },
            open() {
                this.$confirm('你已经被禁言!!禁言到期时间为' + this.endtime, '警告', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'error',
                    center: true
                }).then(() => {
                    this.$router.push({path: '/'});
                }).catch(() => {
                    this.$router.push({path: '/'});
                });
            },
            useTemplate() {
                //显示新增对话框
                this.senddialogStatus = "select"
                this.senddialogFormVisible = true
            },
            loadMessage() {
                if (this.success != "success") {
                    this.loadendtime();
                } else {
                    this.senddialogStatus = "send"
                    this.senddialogFormVisible = true
                }
            },
            AllsendMessage() {
                let userIds = [];
                let users = this.user;
                for (let a = 0; a < users.length; a++) {
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
                    this.senddialogFormVisible = false;
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
                    this.senddialogFormVisible = false;
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
                for (let a = 0; a < templates.length; a++) {
                    if (templates[a].ID == selVal) {
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
            GetTemplet() {
                this.api({
                    url: "/templet/listtemplet",
                    method: "post"
                }).then(data => {
                    this.template = data.list;
                })
            },
            showWord() {
                this.dialogStatus = "leadingWord";
                this.dialogFormVisible = true;
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
            setIt() {
                this.$message.success("模板导入成功!");
                let templates = this.template
                for (let a=0;a<templates.length;a++){
                    if (templates[a].ID==this.selVal) {
                        this.textarea = templates[a].CONTENT;
                    }
                }
                this.senddialogFormVisible = false
            },
            GetAllTemplet() {
                this.api({
                    url: "/templet/listalltemplet",
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
