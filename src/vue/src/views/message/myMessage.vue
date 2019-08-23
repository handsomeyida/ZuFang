<template>
  <div class="app-container">
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit
              highlight-current-row>
      <el-table-column align="center" label="序号" width="80">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="发送者" prop="START_ID" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="消息内容" prop="CONTENT" style="width: 200px;"></el-table-column>
      <el-table-column align="center" label="发送时间" prop="CREATE_TIME" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="管理" width="220">
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" @click="showUpdate(scope.$index)">查看消息
          </el-button>
          <el-button type="success" icon="delete" @click="removeMsg(scope.$index)">已读
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
  </div>
</template>

<script>
  export default {
    name: "myMessage",
    data() {
      return {
        totalCount: 0, //分页组件--数据总条数
        list: [],//消息表格的数据
        listLoading: false,//数据加载等待动画
        listQuery: {
          pageNum: 1,//页码
          pageRow: 10,//每页条数
          userId: '',
        },
        dialogStatus: 'create',
        dialogFormVisible: false,
      }
    },
    created() {
      this.getList();
    },
    methods: {
      getList() {
        this.listLoading = true;
        this.listQuery.userId = this.$store.state.user.userId;
        this.api({
          url: '/information/listUserMsg',
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
        console.log(val)
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
      removeMsg($index) {
        let _vue = this;
        // console.log(_vue.list[$index])
        this.$confirm('你看完了吗?', '提示', {
          confirmButtonText: '确定',
          showCancelButton: false,
          type: 'warning'
        }).then(() => {
          let msg = _vue.list[$index];
          _vue.api({
            url: "/information/deleteMsg",
            method: "post",
            data: msg
          }).then(() => {
            _vue.$message.success("已读");
            _vue.getList()
          }).catch(() => {
            _vue.$message.error("获取信息失败,请重试");
          })
        })
      },
    },
  }
</script>

<style scoped>

</style>
