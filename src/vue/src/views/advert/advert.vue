<template>
  <div>
    <el-row>
      <h2 class="text-left" style="color: #409eff;">广告栏图片管理：</h2>
      <div class="filter-container">
        <el-form>
          <el-form-item>
            <el-button type="primary" icon="plus" v-if="hasPerm('template:add')" @click="showUpload">添加图片</el-button>
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
        <el-table-column align="center" label="广告标题" prop="ADVERT_TITLE" style="width: 60px;"></el-table-column>
        <el-table-column align="center" label="对应小程序页面" prop="OUT_URL" style="width: 60px;"></el-table-column>
        <el-table-column align="center" label="广告图片" prop="IMG_URL" style="width: 200px;">
          <template slot-scope="scope">
            <img :src="scope.row.IMG_URL" alt="" style="width: 200px;height:50px">
          </template>
        </el-table-column>
        <el-table-column align="center" label="管理" width="420px" v-if="hasPerm('template:update')">
          <template slot-scope="scope">
            <el-button type="primary" icon="edit" @click="showUpdate(scope.$index)" v-if="hasPerm('template:update')">
              修改
            </el-button>
            <el-button type="danger" icon="delete" @click="removeAdvert(scope.$index)"
                       v-if="hasPerm('template:delete')">删除
            </el-button>
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
        <el-form ref="form2" :model="form2" label-position="top" label-width="80px">
          <el-form-item label="广告标题:">
            <el-input v-model="Advert.ADVERT_TITLE" name="ADVERT_TITLE" style="width:360px;"></el-input>
          </el-form-item>
          <el-form-item label="对应小程序页面:">
            <el-input v-model="Advert.OUT_URL" name="OUT_URL" style="width:360px;"></el-input>
          </el-form-item>
          <el-form-item label-width="80px" label="上传图片:">
            <el-upload class="upload-demo" action="" :limit=1 :auto-upload=false :on-change="onchange2" :on-exceed="onexceed"
                       :on-remove="handleRemove2" :file-list="fileList2" :multiple=true list-type="picture">
              <el-button size="small" type="primary">选择图片</el-button>
            </el-upload>
          </el-form-item>
          <el-form-item style="padding-top:20px;">
            <el-button type="primary" @click="onSubmit2" v-if="hasPerm('adver:add') && dialogStatus == 'upload'">添加</el-button>
            <el-button type="primary" @click="UpdateAdvert" v-if="hasPerm('adver:add') && dialogStatus == 'update'">修改</el-button>
            <el-button class="test" @click="dialogFormVisible = false">取消</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </el-row>
  </div>
</template>

<script>
    export default {
        name: "advert",

        data() {
            return {
                fileList2: [],
                form2: {
                    name: ''
                },
                params2: {},
                totalCount: 0, //分页组件--数据总条数
                list: [],//模板表格的数据
                listLoading: false,//数据加载等待动画
                listQuery: {
                    pageNum: 1,//页码
                    pageRow: 10,//每页条数
                },
                dialogStatus: 'upload',
                dialogFormVisible: false,
                textMap: {
                    upload: '上传图片',
                    update: '修改',
                },
                Advert: {
                    ID: '',
                    ADVERT_TITLE: '',
                    IMG_URL: '',
                    CREATE_TIME: '',
                    SORT_TIME: '',
                    IS_DEL: '',
                    OUT_URL: '',
                },
                listID: [],
                listIndex: [],
            };
        },
        created() {
            this.getList();
        },
        methods: {
            onchange2(file, filesList) {
                this.param2 = new FormData();
                if (filesList.length >= 5) {
                    this.$message.warning("上传图片已达到5张,若要继续上传请删除某张图片!!");
                } else {
                    this.fileList2 = filesList;
                }
            },

            handleRemove2(file, filesList) {
                this.param2.delete('file')
            },

            onSubmit2() {//表单提交的事件
                if (this.fileList2.length > 0) {
                    var advert = this.Advert;
                    this.param2.append('advert_title', advert.ADVERT_TITLE);
                    this.param2.append('out_url', advert.OUT_URL);
                    let config = {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    };
                    for (var i = 0; i < this.fileList2.length; i++) {
                        this.param2.append('file', this.fileList2[i].raw, this.fileList2[i].name);
                    }
                    //上传文件
                    let _vue = this;
                    _vue.$reqs.post("api/upload/imgUpload", this.param2, config).then(function (data) {
                        if (data.data == "上传成功") {
                            _vue.$message({
                                message: '上传成功',
                                type: 'success',
                                onClose: function () {
                                    location.reload();
                                }
                            });
                        } else {
                            _vue.$message.error(data.data);
                        }
                    })
                } else {
                    this.$message.error("未选择上传图片,若要继续上传请选择某张图片!!");
                }
            },
            getList() {
                this.listLoading = true;
                this.api({
                    url: "/advert/listadvert",
                    method: "post",
                    params: this.listQuery,
                }).then(data => {
                    this.listLoading = false;
                    // console.log(data)
                    this.list = data.list;
                    this.totalCount = data.totalCount;
                    for (let a = 0; a < data.list.length; a++) {
                        this.listID.push(data.list[a].ID)
                    };
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
                    url: "/advert/updateIndex",
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
            showUpload() {
                //显示新增对话框
                this.dialogStatus = "upload"
                this.dialogFormVisible = true
            },
            showUpdate($index) {
                let advert = this.list[$index];
                this.fileList2 = [{name: advert.ADVERT_TITLE, url: advert.IMG_URL}];
                this.Advert.ID = advert.ID;
                this.Advert.ADVERT_TITLE = advert.ADVERT_TITLE;
                this.Advert.IMG_URL = advert.IMG_URL;
                this.Advert.CREATE_TIME = advert.CREATE_TIME;
                this.Advert.SORT_TIME = advert.SORT_TIME;
                this.Advert.IS_DEL = advert.IS_DEL;
                this.Advert.OUT_URL = advert.OUT_URL;
                this.dialogStatus = "update"
                this.dialogFormVisible = true
            },
            UpdateAdvert() {
                let advert = this.Advert;
                this.param2 = new FormData();
                this.param2.append('id', advert.ID);
                this.param2.append('advert_title', advert.ADVERT_TITLE);
                this.param2.append('out_url', advert.OUT_URL);
                let config;
                if (this.fileList2[0].status == 'ready') {
                    config = {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    };
                    for (var i = 0; i < this.fileList2.length; i++) {
                        this.param2.append('file', this.fileList2[i].raw,this.fileList2[i].name);
                    }
                } else {
                    this.param2.append('img_url', advert.IMG_URL);
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
            removeAdvert($index) {
                let _vue = this;
                this.$confirm('确定删除此广告?', '提示', {
                    confirmButtonText: '确定',
                    showCancelButton: false,
                    type: 'warning'
                }).then(() => {
                    let user = _vue.list[$index];
                    _vue.api({
                        url: "/advert/deleteadvert",
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
            onexceed(files, fileList){
                this.$message.error("只能选择一张图片!若要修改图片请将图片删除")
            },
            //上移
            moveUp(index,row){
                let that = this;
                that.listID = [];
                that.listIndex = [];
                that.listID.push(row.ID);
                that.listIndex.push(row.SORT);
                that.listID.push(that.list[index-1].ID);
                that.listIndex.push(that.list[index-1].SORT);
                that.updateChangeIndex();
            },
            //下移
            moveDown(index,row){
                this.listID = [];
                this.listIndex = [];
                let that = this;
                this.listID.push(row.ID);
                this.listIndex.push(row.SORT);
                this.listID.push(that.list[index+1].ID);
                this.listIndex.push(that.list[index+1].SORT);
                this.updateChangeIndex();
            },
            updateChangeIndex() {
                //更新标签的排序号
                this.listLoading = true;
                this.api({
                    url: "/advert/updateChangeIndex",
                    method: "post",
                    data: {
                        listID: this.listID,
                        listIndex: this.listIndex,
                    },
                }).then(data => {
                    if (data == "success") {
                        this.getList();
                        this.listLoading = false;
                        this.canclick = true;
                    }
                })
            }
        }
    }
</script>

<style scoped type="text/css">
  .upload-demo {
    width: 360px;
  }
</style>
