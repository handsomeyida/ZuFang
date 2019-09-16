<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <div style="float: right">
            <div class="block">
              <el-date-picker
                value-format="yyyy-MM-dd HH:mm:ss"
                @change="getList"
                v-model="time"
                type="datetimerange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期">
              </el-date-picker>
            </div>
          </div>
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
      <el-table-column align="center" label="接收者" style="width: 60px;">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.TARGET_ID=='system_info'" type="success">群发</el-tag>
          <el-tag v-else>{{scope.row.NICKNAME}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="消息内容" prop="CONTENT" style="width: 400px;"></el-table-column>
      <el-table-column align="center" label="发送时间" prop="CREATE_TIME" style="width: 60px;"></el-table-column>
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
        name: "sysMessage",
        data() {
            return {
                totalCount: 0, //分页组件--数据总条数
                list: [],//消息表格的数据
                listLoading: false,//数据加载等待动画
                listQuery: {
                    pageNum: 1,//页码
                    pageRow: 10,//每页条数
                    starttime: '',
                    endtime: ''
                },
                time: '',
            }
        },
        created() {
            this.getList();
        },
        methods: {
            getList() {
                this.listQuery.starttime = this.time[0],
                this.listQuery.endtime = this.time[1],
                console.log(this.listQuery.starttime,this.listQuery.endtime)
                this.listLoading = true;
                this.api({
                    url: '/information/listsysinfomation',
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
        }
    }
</script>

<style scoped>

</style>
