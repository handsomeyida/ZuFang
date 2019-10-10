<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <h2 class="text-left" style="color: #409eff;">用户管理：</h2>
        </el-form-item>
      </el-form>
    </div>
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <div style="float: left">
            <el-date-picker
              v-model="listQuery.wxuserCreateTime"
              style="width: 150px;"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              type="date"
              placeholder="用户创建时间">
            </el-date-picker>
            <el-input v-model="listQuery.wxGender" placeholder="性别" style="width: 150px;"></el-input>
            <el-input v-model="listQuery.wxCountry" placeholder="国家" style="width: 150px;"></el-input>
            <el-input v-model="listQuery.wxNickName" placeholder="用户昵称" style="width: 150px;"></el-input>
            <el-button type="primary" plain @click="getList">搜索</el-button>
          </div>
        </el-form-item>
      </el-form>
    </div>
    <el-table :data="list"
              border fit
              highlight-current-row>
      <el-table-column align="center" label="序号" width="80">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="用户头像" prop="wxAvatarUrl">
        <template slot-scope="scope">
          <img :src="list[scope.$index].wxAvatarUrl" style="height: 90px;width: 90px;"/>
        </template>
      </el-table-column>
      <el-table-column align="center" label="用户昵称" prop="wxNickName"></el-table-column>
      <el-table-column align="center" label="性别" prop="wxGender">
        <template slot-scope="scope">
          <p v-if="list[scope.$index].wxGender == 0">
            {{"女"}}
          </p>
          <p v-if="list[scope.$index].wxGender == 1">
            {{"男"}}
          </p>
        </template>
      </el-table-column>
      <el-table-column align="center" label="国家" prop="wxCountry"></el-table-column>
      <el-table-column align="center" label="省份" prop="wxProvince"></el-table-column>
      <el-table-column align="center" label="城市" prop="wxCity"></el-table-column>
      <el-table-column align="center" label="粉丝偏移量" prop="devFans"></el-table-column>
      <el-table-column align="center" label="创建时间" prop="wxuserCreateTime"></el-table-column>
      <el-table-column align="center" label="更新时间" prop="updateTime"></el-table-column>
      <el-table-column align="center" label="是否被限制" prop="isDelWxuser" style="width: 100px;">
        <template slot-scope="scope">
          <p v-if="list[scope.$index].isDelWxuser == 0">
            {{"否"}}
          </p>
          <p v-if="list[scope.$index].isDelWxuser == 1">
            {{"是"}}
          </p>
        </template>
      </el-table-column>
      <el-table-column align="center" fixed="right" label="操作" width="150">
        <template slot-scope="scope">
          <el-button type="text" size="medium" @click="showGetCollection(scope.$index)">查看收藏</el-button>
          <el-button type="text" size="medium" @click="showGetFollow(scope.$index)">查看关注</el-button>
          <el-button type="text" size="medium" @click="showWxUserUpdate(scope.$index)">修改用户</el-button>
          <el-button type="text" size="medium" v-if="list[scope.$index].isDelWxuser==0" @click="limitWxUser(scope.$index)">限制用户</el-button>
          <el-button type="text" size="medium" v-if="list[scope.$index].isDelWxuser==1" @click="limitWxUser(scope.$index)">取消限制</el-button>
          <el-button type="text" size="medium" @click="showSend(scope.$index)">发送系统消息</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="listQuery.page"
      :page-size="listQuery.limit"
      :total="totalCount"
      :page-sizes="[5, 10, 20, 50]"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>

    <el-dialog v-if="dialogStatus=='collection'" :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-table :data="collectionList" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit
                highlight-current-row>
        <el-table-column align="center" label="序号" width="80">
          <template slot-scope="scope">
            <span v-text="getCollectionIndex(scope.$index)"> </span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="收藏的帖子内容" prop="content"></el-table-column>
        <el-table-column align="center" label="最新收藏时间" prop="collectionSortTime"></el-table-column>
      </el-table>
      <el-pagination
        @size-change="collectionHandleSizeChange"
        @current-change="collectionHandleCurrentChange"
        :current-page="listCollectionQuery.page"
        :page-size="listCollectionQuery.limit"
        :total="listCollectionQuery.collectionCount"
        :page-sizes="[5, 10, 20, 50]"
        layout="total, sizes, prev, pager, next, jumper">
      </el-pagination>
    </el-dialog>

    <el-dialog v-if="dialogStatus=='follow'" :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-table :data="followList" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit
                highlight-current-row>
        <el-table-column align="center" label="序号" width="80">
          <template slot-scope="scope">
            <span v-text="getFollowIndex(scope.$index)"> </span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="关注的用户" prop="wxNickName"></el-table-column>
        <el-table-column align="center" label="关注时间" prop="followCreateTime"></el-table-column>
      </el-table>
      <el-pagination
        @size-change="followHandleSizeChange"
        @current-change="followHandleCurrentChange"
        :current-page="listFollowQuery.page"
        :page-size="listFollowQuery.limit"
        :total="listFollowQuery.followCount"
        :page-sizes="[5, 10, 20, 50]"
        layout="total, sizes, prev, pager, next, jumper">
      </el-pagination>
    </el-dialog>

    <el-dialog fullscreen v-if="dialogStatus=='update'" :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form class="small-space" :model="updateWxUser" label-position="left" label-width="80px"
               status-icon :rules="rules" ref="updateWxUser" v-if="dialogStatus=='update'">
        <el-form-item label="用户头像" style="width: 300px">
          <el-upload
            class="avatar-uploader"
            action="https://jsonplaceholder.typicode.com/posts/"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <img v-if="updateWxUser.wxAvatarUrl" :src="updateWxUser.wxAvatarUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="用户昵称" style="width: 300px">
          <el-input type="text" v-model="updateWxUser.wxNickName">
          </el-input>
        </el-form-item>
        <el-form-item label="用户性别">
          <el-select v-model="updateWxUser.wxGender" placeholder="请选择">
            <el-option
              v-for="item in wxUserGenderType"
              :key="item.wxGender"
              :label="item.gender"
              :value="item.wxGender">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="国家">
          <el-cascader
            :options="listArea"
            v-model="value"
            @change="handleChange"
          >
          </el-cascader>
        </el-form-item>

        <el-form-item label="粉丝偏移量" style="width: 300px" prop="devFans">
          <el-input placeholder="请输入" v-model.number="updateWxUser.devFans">
          </el-input>
        </el-form-item>
        <el-form-item label="创建时间" style="width: 300px">
          <el-date-picker
            v-model="updateWxUser.wxuserCreateTime"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
            type="datetime"
            placeholder="选择日期时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="更新时间" style="width: 300px">
          <el-date-picker
            v-model="updateWxUser.updateTime"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
            type="datetime"
            placeholder="选择日期时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="是否限制用户">
          <el-switch
            v-model="updateWxUser.isDelWxuser"
            active-text="是"
            inactive-text="否">
          </el-switch>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" v-if="dialogStatus=='update'" @click="updateWxUserBtn('updateWxUser')">修 改</el-button>
      </div>
    </el-dialog>

    <el-dialog v-if="dialogStatus=='send' || dialogStatus=='template'" :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <div style="text-align:center; vertical-align: middle; margin-top: 30px">
        <el-input
          type="textarea"
          :rows="4"
          placeholder="请输入内容"
          v-model="insertSysInformation.sysInformationContent"
          style="width: 800px;">
        </el-input>
        <div style="margin-top: 20px">
          <el-button type="primary" @click="showTemplate">选择模板</el-button>
          <el-button type="success" @click="sendUserMessage">发送消息</el-button>
        </div>
      </div>
    </el-dialog>

    <el-dialog v-if="dialogStatus=='template'" :title="textMap[dialogStatus]" :visible.sync="dialogTemplateFormVisible">
      <el-form class="small-space" :model="MsgTemplate" label-position="left" label-width="120px"
               style='width: 300px; margin-left:50px;'>
        <el-form-item label="选择模板" required>
          <el-select v-model="MsgTemplate.templateContent" placeholder="请选择模板" style="width: 670px">
            <el-option
              v-for="item in template"
              :key="item.templateId"
              :label="item.templateTitle"
              :value="item.templateContent">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogTemplateFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirm">选择</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 100px;
    height: 100px;
    line-height: 100px;
    text-align: center;
  }
  .avatar {
    width: 100px;
    height: 100px;
    display: block;
  }
</style>

<script>
  import {mapGetters} from 'vuex'

  export default {
    data() {
      //input结合正则的校验
      var checkMan = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('不能为空'));
        }
        setTimeout(() => {
          if (new RegExp(/(^([1-9]\d*|[0]{1,1})$)/).test(value) == false) {
            callback(new Error('请输入正确数字'));
          } else {
              callback();
          }
        }, 1000);
      };
      return {
        listLoading: false,//数据加载等待动画
        totalCount: 0, //分页组件--数据总条数
        list: [],//表格的数据
        collectionList: [],//收藏列表数据
        followList: [],//关注列表数据
        value:['','',''],//微信某用户初始的国家，省份，城市
        listArea: [],//存放城市数据
        listQuery: {
          page: 1,//页码
          limit: 5,//每页条数
          wxuserCreateTime: '',//查询用户创建时间
          wxGender: '',//查询用户性别
          wxCountry: '',//查询用户国家
          wxNickName: '',//查询用户昵称
        },
        listCollectionQuery: {
          page: 1,//页码
          limit: 5,//每页条数
          collectionCount: 0, //分页组件--数据总条数
          wxuserId:'',
        },
        listFollowQuery: {
          page: 1,//页码
          limit: 5,//每页条数
          followCount: 0, //分页组件--数据总条数
          wxuserId:'',
        },
        wxUserIdObject:{wxuserId:'',},
        updateWxUser: {
          wxuserId: '',
          wxAvatarUrl: '',          wxNickName: '',
          wxGender: '',             wxCountry: '',
          wxProvince: '',           wxCity: '',
          devFans: '',              wxuserCreateTime: '',
          updateTime: '',           isDelWxuser: '',
        },
        wxUserGenderType: [{
          wxGender:"0",
          gender:"未知"
        },{
          wxGender:"1",
          gender:"男"
        },{
          wxGender:"2",
          gender:"女"
        }],
        MsgTemplate: {
          templateId: '',
          templateTitle: '',
          templateContent: '',
          templateIsNotGuide: '',
        },
        insertSysInformation: {
          wxuserId:'',
          sysInformationContent: '',
        },

        template: [],//模板列表
        dialogStatus: 'create',
        dialogFormVisible: false,
        dialogTemplateFormVisible: false,
        dialogForm: false,
        textMap: {
          update: '编辑用户',
          collection: '查看收藏',
          follow: '查看关注',
          send: '发送系统消息',
          template: '选择模板',
        },
        rules: {
          wxNickName: [
            { required: true, message: '请输入用户昵称', trigger: 'blur' },
            { min: 3, max: 16, message: '长度在 3 到 16 个字符', trigger: 'blur' }
          ],
          devFans: [
            { validator: checkMan, trigger: 'blur' }
          ]
        },
      }
    },
    created() {
      this.getList();
    },
    methods: {
      getList() {
        //查询列表
        this.listLoading = true;
        this.api({
          url: "/WxUser/listWxUserF",
          method: "post",
          params: this.listQuery,
        }).then(reqs => {
          this.listLoading = false;
          this.list = reqs.data;
          this.totalCount = reqs.count;
        });
      },
      handleSizeChange(val) {
        //改变每页数量
        this.listQuery.limit = val
        this.handleFilter();
      },
      handleCurrentChange(val) {
        //改变页码
        this.listQuery.page = val
        this.getList();
      },
      handleFilter() {
        //查询事件
        this.listQuery.page = 1
        this.getList()
      },
      getIndex($index) {
        //表格序号
        return (this.listQuery.page - 1) * this.listQuery.limit + $index + 1
      },
      collectionHandleSizeChange(val) {
        //改变每页数量
        this.listCollectionQuery.limit = val
        this.collectionHandleFilter();
      },
      collectionHandleCurrentChange(val) {
        //改变页码
        this.listCollectionQuery.page = val
        this.getCollection();
      },
      collectionHandleFilter() {
        //查询事件
        this.listCollectionQuery.page = 1
        this.getCollection()
      },
      getCollectionIndex($index) {
        //表格序号
        return (this.listCollectionQuery.page - 1) * this.listCollectionQuery.limit + $index + 1
      },
      //专门给分页用的
      getCollection(){
        this.api({
          url: "/WxUser/listCollectionF",
          method: "post",
          params: this.listCollectionQuery,
        }).then(reqs => {
          this.listLoading = false;
          this.collectionList = reqs.data;
          this.listCollectionQuery.collectionCount = reqs.count;
        });
      },
      showGetCollection($index){
        //查询列表
        this.listLoading = true;
        this.listCollectionQuery.wxuserId = this.list[$index].wxuserId;
        this.api({
          url: "/WxUser/listCollectionF",
          method: "post",
          params: this.listCollectionQuery,
        }).then(reqs => {
          this.listLoading = false;
          this.collectionList = reqs.data;
          this.listCollectionQuery.collectionCount = reqs.count;
        });
        this.dialogStatus = "collection";
        this.dialogFormVisible = true;
      },

      followHandleSizeChange(val) {
        //改变每页数量
        this.listFollowQuery.limit = val
        this.followHandleFilter();
      },
      followHandleCurrentChange(val) {
        //改变页码
        this.listFollowQuery.page = val
        this.getFollow();
      },
      followHandleFilter() {
        //查询事件
        this.listFollowQuery.page = 1
        this.getFollow()
      },
      getFollowIndex($index) {
        //表格序号
        return (this.listFollowQuery.page - 1) * this.listFollowQuery.limit + $index + 1
      },
      //专门给分页用的
      getFollow(){
        this.api({
          url: "/WxUser/listFollowF",
          method: "post",
          params: this.listFollowQuery,
        }).then(reqs => {
          this.listLoading = false;
          this.followList = reqs.data;
          this.listFollowQuery.followCount = reqs.count;
        });
      },
      showGetFollow($index){
        //查询列表
        this.listLoading = true;
        this.listFollowQuery.wxuserId = this.list[$index].wxuserId;
        this.api({
          url: "/WxUser/listFollowF",
          method: "post",
          params: this.listFollowQuery,
        }).then(reqs => {
          this.listLoading = false;
          this.followList = reqs.data;
          this.listFollowQuery.followCount = reqs.count;
        });
        this.dialogStatus = "follow";
        this.dialogFormVisible = true;
      },

      showWxUserUpdate($index){
        this.listArea = this.areaName.Location.CountryRegion;
        let post = this.list[$index];
        this.updateWxUser.wxuserId = post.wxuserId;
        this.updateWxUser.wxAvatarUrl = post.wxAvatarUrl;
        this.updateWxUser.wxNickName = post.wxNickName;
        this.updateWxUser.wxGender = post.wxGender;
        this.value[0] = post.wxCountry;
        this.value[1] = post.wxProvince;
        this.value[2] = post.wxCity;
        this.updateWxUser.wxCountry = post.wxCountry;
        this.updateWxUser.wxProvince = post.wxProvince;
        this.updateWxUser.wxCity = post.wxCity;
        this.updateWxUser.devFans = post.devFans;
        this.updateWxUser.wxuserCreateTime = post.wxuserCreateTime;
        this.updateWxUser.updateTime = post.updateTime;
        if (post.isDelWxuser == 0) {
          this.updateWxUser.isDelWxuser = false;
        }else {
          this.updateWxUser.isDelWxuser = true;
        }
        this.listLoading = true;
        this.dialogStatus = "update"
        this.dialogFormVisible = true
      },
      handleAvatarSuccess(res, file) {
        this.updateWxUser.wxAvatarUrl = file.url;
      },
      beforeAvatarUpload(file) {
        const isLt2M = file.size / 1024 / 1024 < 2;
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return isLt2M;
      },
      handleChange(value) {
        this.updateWxUser.wxCountry = value[0];
        this.updateWxUser.wxProvince = value[1];
        this.updateWxUser.wxCity = value[2];
      },
      updateWxUserBtn(data){
        this.$refs[data].validate((valid) => {//elementUI表单点击提交后执行的函数，校验是否满足自定义规则
          if (valid) {
            if (this.updateWxUser.isDelWxuser == false) {
              this.updateWxUser.isDelWxuser = 0;
            }else {
              this.updateWxUser.isDelWxuser = 1;
            }
            //修改微信用户信息
            this.api({
              url: "/WxUser/updateWxUserF",
              method: "post",
              data:this.updateWxUser,
            }).then(() => {
              this.$message.success("修改成功")
              this.getList()
              this.dialogFormVisible = false
            }).catch(() => {
              this.$message.error("修改失败")
            })
          } else {
            this.$message.error("数据不规范，请检查")
            return false;
          }
        });
      },
      limitWxUser($index) {
        var wxuserId = this.list[$index].wxuserId;
        this.wxUserIdObject.wxuserId = wxuserId;
        var isDelWxuser = this.list[$index].isDelWxuser;
        if (isDelWxuser==1){
          this.$confirm('确定取消限制该用户?', '提示', {
            confirmButtonText: '确定',
            showCancelButton: false,
            type: 'warning'
          }).then(() => {
            this.api({
              url: "/WxUser/updateIsDelWxuser0F",
              method: "post",
              params: this.wxUserIdObject,
            }).then(() => {
              this.getList()
            }).catch(() => {
              this.$message.error("取消限制失败")
            })
          })
        } else {
          this.$confirm('限制后，该用户将无法不能使用发布、评论、私信、点赞功能，但可以浏览帖子和关注其他用户。确定限制该用户?', '提示', {
            confirmButtonText: '确定',
            showCancelButton: false,
            type: 'warning'
          }).then(() => {
            this.api({
              url: "/WxUser/updateIsDelWxuserF",
              method: "post",
              params: this.wxUserIdObject,
            }).then(() => {
              this.getList()
            }).catch(() => {
              this.$message.error("限制失败")
            })
          })
        }


      },

      showSend($index){
        this.insertSysInformation.wxuserId = this.list[$index].wxuserId;
        this.dialogStatus = "send";
        this.dialogFormVisible = true;
      },
      showTemplate() {
        //当打开模板时，查询所有消息模板信息
        this.api({
          url: "/WxUser/listTemplateF",
          method: "post"
        }).then(data => {
          this.template = data.data;
        })
        //显示消息模板对话框
        this.dialogStatus = "template";
        this.dialogTemplateFormVisible = true;
      },
      confirm(){
        this.insertSysInformation.sysInformationContent = this.MsgTemplate.templateContent;
        this.dialogFormVisible = true;
        this.dialogTemplateFormVisible = false
      },
      sendUserMessage(){
        this.$confirm('确定发送此消息给该用户吗?', '提示', {
          confirmButtonText: '确定',
          showCancelButton: false,
          type: 'warning'
        }).then(() => {
          this.api({
            url: "/WxUser/insertSysInformationF",
            method: "post",
            params: this.insertSysInformation,
          }).then(() => {
            this.$message.success("发送成功")
            this.getList()
            this.dialogFormVisible = false
          }).catch(() => {
            this.$message.error("发送失败")
          })
        })
      }
    }
  }
</script>
