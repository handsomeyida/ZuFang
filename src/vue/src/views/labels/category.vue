<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <h2 class="text-left" style="color: #409eff;">帖子类型及图标管理：</h2>
        </el-form-item>
      </el-form>
    </div>
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit
              highlight-current-row v-if="hasPerm('basics:list')">
      <el-table-column align="center" label="序号" width="80">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="帖子类型" prop="TYPE_NAME" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="帖子图标" prop="TYPE_IMTG_URL" style="width: 200px;">
        <template slot-scope="scope">
          <img :src="scope.row.TYPE_IMTG_URL" alt="" style="width: 36px;height:36px">
        </template>
      </el-table-column>
      <el-table-column align="center" label="管理" width="220" v-if="hasPerm('basics:update')">
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" @click="showUpdate(scope.$index)" v-if="hasPerm('basics:update')">修改</el-button>
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
      <el-form class="small-space" :model="CategoryList" label-position="left" label-width="80px"
               style='width: 300px; margin-left:50px;' >
        <el-form-item label-width="90px" label="帖子类型:" required >
          <el-select v-model="CategoryList.TYPE_NAME" :filterable=true placeholder="请选择(输入)标签类型">
            <el-option
              v-for="item in list"
              :key="item.ID"
              :label="item.TYPE_NAME"
              :value="item.TYPE_NAME">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label-width="90px" required label="选择图标:">
          　<el-upload class="upload-demo"
             action=""
             :auto-upload=false
             :limit=1
             :on-change="onchange2"
             :on-exceed="onexceed"
             :on-remove="handleRemove2"
             :file-list="fileList"
             list-type="picture">
              <el-button size="small" type="primary">选择图标</el-button>
          　</el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="update" v-if="hasPerm('basics:update')">修 改</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "labels",
    data() {
      return {
        totalCount: 0, //分页组件--数据总条数
        list: [],//标签表格的数据
        listLoading: false,//数据加载等待动画
        listQuery: {
          pageNum: 1,//页码
          pageRow: 10,//每页条数
        },
        dialogStatus: 'create',
        dialogFormVisible: false,
        textMap: {
          update: '修改类型',
          create: '添加类型'
        },
        CategoryList: {
          ID: '',
          TYPE_IMTG_URL: '',
          TYPE_NAME: '',
          CREATE_TIME: '',
          IS_DEL: '',
        },
        types: [],
        fileList:[],
        params2:{},
      }
    },
    created() {
      this.getList();
    },
    methods: {
        //获取帖子类型
      getList() {
        this.listLoading = true;
        this.api({
          url: "/basics/listType",
          method: "post",
          params: this.listQuery,
        }).then(data => {
          this.listLoading = false;
          for (let a=0; a<data.list.length; a++) {
            if (data.list[a].IS_DEL != 1) {
              this.list.push(data.list[a]);
            }
          }
          this.totalCount = data.totalCount;
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
      showUpdate($index) {
        let type = this.list[$index];
        this.CategoryList.ID = type.ID;
        this.CategoryList.TYPE_NAME = type.TYPE_NAME;
        this.CategoryList.CREATE_TIME = type.CREATE_TIME;
        this.CategoryList.TYPE_IMTG_URL = type.TYPE_IMTG_URL;
        this.fileList = [{name: type.TYPE_NAME, url: type.TYPE_IMTG_URL}]
        this.dialogStatus = "update"
        this.dialogFormVisible = true
      },
      update() {
        let postType = this.CategoryList;
        this.param2.append('id', postType.ID);
        this.param2.append('type_name', postType.TYPE_NAME);
        let config = {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        };
        if (this.fileList.length>0) {
          for (var i = 0; i < this.fileList.length; i++) {
            this.param2.append('file', this.fileList[i].raw, this.fileList[i].name);
          }
        }
        let _vue = this;
        _vue.$reqs.post("api/upload/typeimgUpload", this.param2, config).then(function(data) {
          if (data.data == "上传成功") {
            _vue.$message({
              message: '成功',
              type: 'success',
              onClose: function () {
                location.reload();
              }
            });
          } else {
            _vue.$message.error(data.data);
          }
        })
      },
      onchange2(file,filesList){
        this.param2 = new FormData();
        this.fileList = filesList;
      },
      handleRemove2(file,filesList){
        this.filesList = [];
      },
      onexceed(files, fileList){
        this.$message.error("只能选择一张图片!若要修改图片请将图片删除")
      },
    }
  }
</script>

<style scoped>

</style>
