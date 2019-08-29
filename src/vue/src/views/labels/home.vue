<template>
  <div class="app-container">
    <h2 class="text-left" style="color: #409eff;margin-left: 1px">家用设备管理：</h2>
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-button type="primary" icon="plus" v-if="hasPerm('basics:add')" @click="showCreate">添加设备</el-button>
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
      <el-table-column align="center" label="设备" prop="CONTENT" width="800"></el-table-column>
      <el-table-column align="center" label="管理" width="819" v-if="hasPerm('basics:update') ||hasPerm('basics:delete') ">
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" @click="showUpdate(scope.$index)" v-if="hasPerm('basics:update')">修改</el-button>
          <el-button type="danger" icon="delete" @click="removeHome(scope.$index)" v-if="hasPerm('basics:delete')">删除</el-button>
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
      <el-form class="small-space" :model="Home" label-position="left" label-width="80px"
               style='width: 300px; margin-left:50px;'>
        <el-form-item label="标签" required>
          <el-input placeholder="家居设备" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="设备名称" required>
          <el-input type="content" v-model="Home.content" placeholder="请输入设备名称"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
          <el-button type="success" v-if="dialogStatus=='create'" @click="createHome">添加设备</el-button>
          <el-button type="success" v-if="dialogStatus=='update'" @click="updateHome">修改设备</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "home",
    data() {
      return {
        totalCount: 0, //分页组件--数据总条数
        list: [],//表格的数据
        listLoading: false,//数据加载等待动画
        listQuery: {
          pageNum: 1,//页码
          pageRow: 10,//每页条数
        },
        dialogStatus: 'create',
        dialogFormVisible: false,
        textMap: {
          update: '编辑设备',
          create: '添加设备'
        },
        Home: {
          id: '',
          parentId: 1,
          content: '',
        },
      }
    },
    created() {
      this.getList();
    },
    methods: {
      getList() {
        this.listLoading = true;
        this.api({
          url: "/basics/listhomelabels",
          method: "get",
          params: this.listQuery,
        }).then(data => {
          this.listLoading = false;
          this.list = data.list;
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
        return $index + 1
      },
      showCreate() {
        //显示新增对话框
        this.Home.id = '';
        this.Home.parentId = 1;
        this.Home.content = '';
        this.dialogStatus = "create"
        this.dialogFormVisible = true
      },
      showUpdate($index) {
        let home = this.list[$index];
        this.Home.id = home.ID;
        this.Home.parentId = home.PARENT_ID;
        this.Home.content = home.CONTENT;
        this.dialogStatus = "update"
        this.dialogFormVisible = true
      },
      createHome() {
        console.log(this.Home)
        this.api({
          url: "/basics/inserthomelabels",
          method: "post",
          data: this.Home
        }).then(() => {
          this.getList();
          this.dialogFormVisible = false;
          this.$message.success("添加设备成功!")
        })
      },
      updateHome() {
        // console.log(this.Home)
        let _vue = this;
        this.api({
          url: "/basics/updatehomelabels",
          method: "post",
          data: this.Home
        }).then(() => {
          let msg = "修改设备成功";
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
      removeHome($index) {
        let _vue = this;
        console.log(_vue.list[$index])
        this.$confirm('确定删除此设备?', '提示', {
          confirmButtonText: '确定',
          showCancelButton: false,
          type: 'warning'
        }).then(() => {
          let home = _vue.list[$index];
          _vue.api({
            url: "/basics/deletehomelabels",
            method: "post",
            data: home
          }).then(() => {
            _vue.$message.success("删除成功")
            _vue.getList()
          }).catch(() => {
            _vue.$message.error("删除失败")
          })
        })
      },
    }
  }
</script>

<style scoped>

</style>
