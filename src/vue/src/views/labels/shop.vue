<template>
  <div class="app-container">
    <h2 class="text-left" style="color: #409eff;margin-left: 1px">热门商圈管理：</h2>
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
      <el-table-column align="center" label="区" prop="Qcontent" width="400"></el-table-column>
      <el-table-column align="center" label="商圈" width="800">
        <template slot-scope="scope">
            <div v-for="SShop in scope.row.SShop" style="text-align: center;float: left;">
              <el-tag style="margin-right: 3px;" type="primary"
                      closable :disable-transitions="false" @close="handleClose(SShop)">{{SShop.Scontent}}</el-tag>
            </div>
        </template>
      </el-table-column>
      <el-table-column align="center" label="管理" width="419" v-if="hasPerm('basics:update') ||hasPerm('basics:delete') ">
        <template slot-scope="scope">
<!--          <el-button type="success" icon="edit" @click="showUpdate(scope.$index)" v-if="hasPerm('basics:update')">修改商圈</el-button>-->
          <el-button type="danger" icon="delete" @click="removeShop(scope.$index)" v-if="hasPerm('basics:delete')">删除区域</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form class="small-space" :model="Shop" label-position="left" label-width="80px"
               style='width: 300px; margin-left:50px;'>
        <el-form-item label="区" required>
          <el-select v-model="Shop.parentId" placeholder="请选择(输入)区名称" :filterable=true :allow-create=true :clearable=true>
            <el-option
              v-for="item in list"
              :key="item.Qid"
              :label="item.Qcontent"
              :value="item.Qid">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="热门商圈" v-if="dialogStatus=='create'">
          <el-input type="content" v-model="Shop.content" placeholder="不输入不添加商圈"></el-input>
        </el-form-item>
        <el-form-item label="热门商圈" required v-if="dialogStatus=='update'">
          <div>
            <el-checkbox-group v-model="Shop.id" size="medium" style="width: 350px">
              <el-checkbox-button v-for="sshop in listcontent" :label="sshop.Sid" :key="sshop.Sid">{{sshop.Scontent}}</el-checkbox-button>
            </el-checkbox-group>
          </div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="success" v-if="dialogStatus=='create'" @click="createShop">添加商圈</el-button>
        <el-button type="success" v-if="dialogStatus=='update'" @click="updateShop">编辑商圈</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
    export default {
        name: "shop",
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
            update: '编辑商圈',
            create: '添加区'
          },
          Shop: {
            id: [],
            content: [],
            parentId: '',
            parentContent: '',
          },
          listcontent: [],
        }
      },
      created() {
        this.getList();
      },
      methods: {
        getList() {
          this.listLoading = true;
          this.api({
            url: "/basics/listshoplabels",
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
        handleClose(shop) {
          this.$confirm('确定删除此商圈?', '提示', {
            confirmButtonText: '确定',
            showCancelButton: false,
            type: 'warning'
          }).then(() => {
            let _vue = this;
            _vue.api({
              url: "/basics/deleteonelabels",
              method: "post",
              data: shop
            }).then(() => {
              this.getList();
              this.dialogFormVisible = false;
              this.$message.success("删除成功!")
            })
          })
        },
        showCreate() {
          //显示新增对话框
          this.Shop.id = '';
          this.Shop.parentId = '';
          this.Shop.content = '';
          this.Shop.parentContent = '';
          this.dialogStatus = "create"
          this.dialogFormVisible = true
        },
        showUpdate($index) {
          let shop = this.list[$index];
          // this.Shop.id = shop.ID;
          // this.Shop.content = shop.SShop;
          for (let a=0; a<shop.SShop.length;a++){
            this.Shop.id.push(shop.SShop[a].Sid);
          }
          this.Shop.parentId = shop.Qid;
          this.Shop.parentContent = shop.Qcontent;
          this.listcontent = shop.SShop;
          this.dialogStatus = "update"
          this.dialogFormVisible = true
          console.log(this.listcontent)
        },
        createShop() {
          // console.log(this.Shop)
          this.api({
            url: "/basics/insertshoplabels",
            method: "post",
            data: this.Shop
          }).then(() => {
            this.getList();
            this.dialogFormVisible = false;
            this.$message.success("添加成功!")
          })
        },
        updateShop() {
          console.log(this.Shop)
          // let _vue = this;
          // this.api({
          //   url: "/basics/updateShoplabels",
          //   method: "post",
          //   data: this.Home
          // }).then(() => {
          //   let msg = "修改商圈成功";
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
        removeShop($index) {
          // console.log(_vue.list[$index])
          let _vue = this;
          let shop = _vue.list[$index];
          let Qid = shop.Qid;
          let sonid = [];
          for (var a=0; a<shop.SShop.length; a++) {
            sonid.push(shop.SShop[a].Sid);
          }
          this.$confirm('确定删除此区域?', '提示', {
            confirmButtonText: '确定',
            showCancelButton: false,
            type: 'warning'
          }).then(() => {
            _vue.api({
              url: "/basics/deletealllabels",
              method: "post",
              data: {
                Qid,
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
