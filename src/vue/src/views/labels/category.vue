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
      <el-table-column align="center" label="管理" width="420px" v-if="hasPerm('basics:update')">
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" @click="showUpdate(scope.$index)" v-if="hasPerm('basics:update')">修改
          </el-button>
          <el-button type="danger" icon="edit" @click="removeType(scope.$index)">删除</el-button>
          <el-button
            size="mini"
            :disabled="scope.$index===0"
            @click="moveUp(scope.$index,scope.row)"><i class="el-icon-arrow-up"></i></el-button>
          <el-button
            size="mini"
            :disabled="scope.$index===(list.length-1)"
            @click="moveDown(scope.$index,scope.row)"><i class="el-icon-arrow-down"></i></el-button>
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
               style='width: 300px; margin-left:50px;'>
        <el-form-item label-width="90px" label="帖子类型:" required>
          <el-input v-model="CategoryList.TYPE_NAME" name="names" placeholder="请选择(输入)标签类型"></el-input>
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
                fileList: [],
                params2: {},
                listID: [],
                listIndex: [],
                canclick: true
            }
        },
        created() {
            this.getList();
        },
        methods: {
            //获取帖子类型
            getList() {
                this.listLoading = true;
                this.list = [];
                this.api({
                    url: "/basics/listType",
                    method: "post",
                    params: this.listQuery,
                }).then(data => {
                    this.listLoading = false;
                    for (let a = 0; a < data.list.length; a++) {
                        if (data.list[a].IS_DEL != 1) {
                            this.list.push(data.list[a]);
                            this.listID.push(data.list[a].ID)
                        }
                    }
                    this.totalCount = data.totalCount;
                    let i = 0;
                    for (i;i<this.list.length;i++) {
                        if (i == this.listQuery.pageRow) {
                            this.listIndex.push(i+1)
                            i = 0;
                        }
                        this.listIndex.push(i+1);
                    }
                    this.updateIndex();
                })
            },
            updateIndex() {
                //设置标签的排序号
                this.api({
                    url: "/basics/updateIndex",
                    method: "post",
                    data: {
                        listID: this.listID,
                        listIndex: this.listIndex,
                    },
                }).then(data => {})
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
                this.param2 = new FormData();
                this.param2.append('id', postType.ID);
                this.param2.append('type_name', postType.TYPE_NAME);
                let config;
                if (this.fileList[0].status == 'ready') {
                    config = {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    };
                    for (var i = 0; i < this.fileList.length; i++) {
                        this.param2.append('file', this.fileList[i].raw, this.fileList[i].name);
                    }
                } else {
                    this.param2.append('type_imtg_url', postType.TYPE_IMTG_URL);
                }
                let _vue = this;
                _vue.$reqs.post("api/upload/typeimgUpload", this.param2, config).then(function (data) {
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
            onchange2(file, filesList) {
                this.param2 = new FormData();
                this.fileList = filesList;
            },
            handleRemove2(file, filesList) {
                this.filesList = [];
            },
            onexceed(files, fileList) {
                this.$message.error("只能选择一张图片!若要修改图片请将图片删除")
            },
            removeType($index) {
                let _vue = this;
                this.$confirm('确定删除此标签?', '提示', {
                    confirmButtonText: '确定',
                    showCancelButton: false,
                    type: 'warning'
                }).then(() => {
                    let type = _vue.list[$index];
                    _vue.api({
                        url: "/basics/deleteType",
                        method: "post",
                        data: type
                    }).then(() => {
                        _vue.$message.success("删除成功")
                        _vue.getList()
                    }).catch(() => {
                        _vue.$message.error("删除失败")
                    })
                })
            },
            //上移
            moveUp(index, row){
                let that = this;
                this.canclick = false
                this.listID = [];
                this.listIndex = [];
                this.listID.push(row.ID)
                this.listIndex.push(row.SORT);
                this.listID.push(that.list[index-1].ID);
                this.listIndex.push(that.list[index-1].SORT);
                this.updateChangeIndex();
            },
            //下移
            moveDown(index,row){
                this.canclick = false;
                this.listID = [];
                this.listIndex = [];
                let that = this;
                this.listID.push(row.ID)
                this.listIndex.push(row.SORT);
                this.listID.push(that.list[index+1].ID);
                this.listIndex.push(that.list[index+1].SORT);
                this.updateChangeIndex();
            },
            updateChangeIndex() {
                //更新标签的排序号
                this.listLoading = true;
                this.api({
                    url: "/basics/updateChangeIndex",
                    method: "post",
                    data: {
                        listID: this.listID,
                        listIndex: this.listIndex,
                    },
                }).then(data => {
                    if (data == "success") {
                        this.listLoading = false;
                        this.getList();
                    }
                })
            }
        }
    }
</script>

<style scoped>

</style>
