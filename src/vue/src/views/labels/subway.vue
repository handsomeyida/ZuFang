<template>
  <div class="app-container">
    <h2 class="text-left" style="color: #409eff;margin-left: 1px">地铁周边管理：</h2>
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-button type="primary" icon="plus" v-if="hasPerm('basics:add')" @click="showCreate">添加</el-button>
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
      <el-table-column align="center" label="地铁线路" prop="subcontent" width="400"></el-table-column>
      <el-table-column align="center" label="站点" width="800">
        <template slot-scope="scope">
          <div v-for="line in scope.row.Line" style="text-align: center;float: left;">
            <el-tag style="margin-right: 3px;" type="primary"
                    closable :disable-transitions="false" @close="handleClose(line)">{{line.Lcontent}}</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" label="管理" width="419" v-if="hasPerm('basics:update') ||hasPerm('basics:delete') ">
        <template slot-scope="scope">
          <!--          <el-button type="success" icon="edit" @click="showUpdate(scope.$index)" v-if="hasPerm('basics:update')">修改商圈</el-button>-->
          <el-button type="danger" icon="delete" @click="removeSubway(scope.$index)" v-if="hasPerm('basics:delete')">删除线路</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form class="small-space" :model="Subway" label-position="left" label-width="80px"
               style='width: 300px; margin-left:50px;'>
        <el-form-item label="区" required>
          <el-select v-model="Subway.parentId" placeholder="请选择(输入)地铁线路" :filterable=true :allow-create=true :clearable=true>
            <el-option
              v-for="item in list"
              :key="item.subid"
              :label="item.subcontent"
              :value="item.subid">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="站点" v-if="dialogStatus=='create'">
          <el-input type="content" v-model="Subway.content" placeholder="不输入不添加站点"></el-input>
        </el-form-item>
        <el-form-item label="站点" required v-if="dialogStatus=='update'">
          <div>
            <el-checkbox-group v-model="Subway.id" size="medium" style="width: 350px">
              <el-checkbox-button v-for="line in listcontent" :label="line.Lid" :key="line.Lid">{{line.Lcontent}}</el-checkbox-button>
            </el-checkbox-group>
          </div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="success" v-if="dialogStatus=='create'" @click="createSubway">添加线路</el-button>
        <el-button type="success" v-if="dialogStatus=='update'" @click="updateSubway">编辑线路</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
    export default {
        name: "subway",
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
            update: '编辑线路',
            create: '添加线路'
          },
          Subway: {
            id: [],
            content: [],
            parentId: '',
            parentContent: '',
          },
          listcontent: [],
        };
      },
      created() {
        this.getList();
      },
      methods: {
        getList() {
          this.listLoading = true;
          this.api({
            url: "/basics/listsubwaylabels",
            method: "get",
            // params: this.listQuery,
          }).then(data => {
            this.listLoading = false;
            this.list = data.list;
            // this.totalCount = data.totalCount;
            // console.log(data)
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
        handleClose(line) {
          console.log(line)
          this.$confirm('确定删除此站点?', '提示', {
            confirmButtonText: '确定',
            showCancelButton: false,
            type: 'warning'
          }).then(() => {
            let _vue = this;
            _vue.api({
              url: "/basics/deleteonelabels",
              method: "post",
              data: line
            }).then(() => {
              this.getList();
              this.dialogFormVisible = false;
              this.$message.success("删除成功!")
            })
          })
        },
        showCreate() {
          //显示新增对话框
          this.Subway.id = '';
          this.Subway.parentId = '';
          this.Subway.content = '';
          this.Subway.parentContent = '';
          this.dialogStatus = "create"
          this.dialogFormVisible = true
        },
        showUpdate($index) {
          let shop = this.list[$index];
          // this.Shop.id = shop.ID;
          // this.Shop.content = shop.SShop;
          this.Subway.parentId = shop.Qid;
          this.Subway.parentContent = shop.Qcontent;
          this.dialogStatus = "update"
          this.dialogFormVisible = true
        },
        createSubway() {
          console.log(this.Subway)
          this.api({
            url: "/basics/insertsubwaylabels",
            method: "post",
            data: this.Subway
          }).then(() => {
            this.getList();
            this.dialogFormVisible = false;
            this.$message.success("添加成功!")
          })
        },
        updateSubway() {
          console.log(this.Subway)
          // let _vue = this;
          // this.api({
          //   url: "/basics/updateSubwaylabels",
          //   method: "post",
          //   data: this.Home
          // }).then(() => {
          //   let msg = "修改站点成功";
          //   this.dialogFormVisible = false;
          //   this.$message({
          //     message: msg,
          //     type: 'success',
          //     duration: 1 * 1000,
          //     onClose: () => {
          //       _vue.getList();
          //     }
          //   })
          // })
        },
        removeSubway($index) {
          let _vue = this;
          let subway = _vue.list[$index];
          // console.log(subway)
          let subid = subway.subid;
          let sonid = [];
          for (var a=0; a<subway.Line.length; a++) {
            sonid.push(subway.Line[a].Lid);
          }
          this.$confirm('确定删除此线路?', '提示', {
            confirmButtonText: '确定',
            showCancelButton: false,
            type: 'warning'
          }).then(() => {
            _vue.api({
              url: "/basics/deletealllabels",
              method: "post",
              data: {
                subid,
                sonid
              }
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
