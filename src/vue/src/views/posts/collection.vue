<template>
  <div class="app-container">
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit
              highlight-current-row>
      <el-table-column align="center" label="序号" width="80">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="帖子内容" prop="CONTENT" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="收藏时间" prop="CREATE_TIME" style="width: 200px;"></el-table-column>
      <el-table-column align="center" label="管理" width="440px" v-if="hasPerm('template:update')">
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" @click="showComment(scope.$index)">查看评论</el-button>
          <el-button type="danger" icon="delete" @click="removeCollection(scope.$index)">取消收藏</el-button>
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
      <div class="filter-container">
        <el-form>
          <el-form-item>
            <div style="float: right">
              <el-input v-model="commentlistQuery.CONTENT" placeholder="请输入评论内容" style="width: 150px;"></el-input>
              <el-input v-model="commentlistQuery.WXNAME" placeholder="请输入发帖人姓名" style="width: 150px;"></el-input>
              <el-button type="primary" plain @click="getCommentList">搜索</el-button>
            </div>
          </el-form-item>
        </el-form>
      </div>
      <el-table :data="commentlist" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit
                highlight-current-row>
        <el-table-column align="center" label="序号" width="80">
          <template slot-scope="scope">
            <span v-text="CommentgetIndex(scope.$index)"> </span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="评论人" prop="WXNAME" style="width: 60px;"></el-table-column>
        <el-table-column align="center" label="评论内容" prop="CONTENT" style="width: 200px;"></el-table-column>
        <el-table-column align="center" label="评论时间" prop="CREATE_TIME" style="width: 200px;"></el-table-column>
        <el-table-column align="center" label="管理" width="380px" v-if="hasPerm('template:update')">
          <template slot-scope="scope">
            <el-button type="danger" icon="delete" @click="removeComment(scope.$index)">删除评论</el-button>
            <el-button type="warning" icon="update" @click="showUpdate(scope.$index)">限制此人</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        @size-change="CommenthandleSizeChange"
        @current-change="CommenthandleCurrentChange"
        :current-page="commentlistQuery.pageNum"
        :page-size="commentlistQuery.pageRow"
        :total="commenttotalCount"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper">
      </el-pagination>
    </el-dialog>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogForm">
      <div class="filter-container">
        <el-form>
          <el-form-item>
            <el-input v-model="end" placeholder="请输入限制天数"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="success" @click="updateUser">限制</el-button>
          <el-button @click="dialogForm = false">取 消</el-button>
        </div>
      </div>
    </el-dialog>
  </div>

</template>

<script>
    export default {
        name: "collection",
        data() {
            return {
                totalCount: 0, //分页组件--数据总条数
                list: [],//收藏表格的数据
                listLoading: false,//数据加载等待动画
                listQuery: {
                    userId: '',
                    pageNum: 1,//页码
                    pageRow: 10,//每页条数
                },
                dialogStatus: 'show',
                dialogFormVisible: false,
                dialogForm: false,
                textMap: {
                    show: '帖子评论信息',
                    showlimit: '限制'
                },
                collectionList: {
                    ID: '',
                    CONTENT: '',
                    IS_DEL: '',
                    CREATE_TIME: '',
                    SORT_TIME: '',
                    START_ID: '',
                },
                end: '',
                commenttotalCount: 0, //分页组件--数据总条数
                commentlist: [],//评论表格的数据
                commentlistQuery: {
                    WXNAME: '',
                    postId: '',
                    CONTENT: '',
                    pageNum: 1,//页码
                    pageRow: 100,//每页条数
                },
            }
        },
        created() {
            this.getList();
        },
        methods: {
            getList() {
                this.listQuery.userId = this.$store.state.user.userId;
                this.listLoading = true;
                this.api({
                    url: "/comment/listcollectionbase",
                    method: "post",
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
                return (this.listQuery.pageNum - 1) * this.listQuery.pageRow + $index + 1
            },
            CommenthandleSizeChange(val) {
                //改变每页数量
                this.commentlistQuery.pageRow = val
                this.CommenthandleFilter();
            },
            CommenthandleCurrentChange(val) {
                //改变页码
                this.commentlistQuery.pageNum = val
                this.getCommentList();
            },
            CommenthandleFilter() {
                //查询事件
                this.commentlistQuery.pageNum = 1
                this.getCommentList()
            },
            CommentgetIndex($index) {
                //表格序号
                return (this.commentlistQuery.pageNum - 1) * this.commentlistQuery.pageRow + $index + 1
            },
            showComment($index) {
                //显示新增对话框
                let comment = this.list[$index];
                this.commentlistQuery.postId = comment.POST_ID;
                this.dialogStatus = "show"
                this.dialogFormVisible = true
                this.getCommentList();
            },
            getCommentList() {
                this.listLoading = true;
                this.api({
                    url: "/comment/listcomment",
                    method: "post",
                    params: this.commentlistQuery,
                }).then(data => {
                    this.listLoading = false;
                    this.commentlist = data.list;
                    this.commenttotalCount = data.totalCount;
                })
            },
            removeCollection($index) {
                let _vue = this;
                this.$confirm('确定取消收藏?', '提示', {
                    confirmButtonText: '确定',
                    showCancelButton: false,
                    type: 'warning'
                }).then(() => {
                    let base = _vue.list[$index];
                    // console.log(base)
                    _vue.api({
                        url: "/comment/deletecollectionbase",
                        method: "post",
                        data: base
                    }).then(() => {
                        _vue.$message.success("取消收藏成功")
                        _vue.getList()
                    }).catch(() => {
                        _vue.$message.error("取消收藏失败")
                    })
                })
            },
            removeComment($index) {
                let _vue = this;
                this.$confirm('确定删除评论?', '提示', {
                    confirmButtonText: '确定',
                    showCancelButton: false,
                    type: 'warning'
                }).then(() => {
                    let comment = _vue.commentlist[$index];
                    _vue.api({
                        url: "/comment/deletecomment",
                        method: "post",
                        data: comment
                    }).then(() => {
                        _vue.$message.success("删除成功")
                        _vue.getCommentList()
                    }).catch(() => {
                        _vue.$message.error("删除失败")
                    })
                })
            },
            showUpdate($index) {
                this.dialogForm = true;
                this.dialogStatus = 'showlimit';
                let _vue = this;
                let limit = _vue.commentlist[$index];
                // console.log(limit)
                this.collectionList.START_ID = limit.START_ID;
            },
            updateUser() {
                if (this.end == null || this.end.length == 0) {
                    this.$message.error('请输入要限制的天数')
                } else {
                    let _vue = this;
                    this.$confirm('确定限制此人?', '提示', {
                        confirmButtonText: '确定',
                        showCancelButton: false,
                        type: 'warning'
                    }).then(() => {
                        let limit = _vue.collectionList;
                        _vue.api({
                            url: "/comment/insertlimit",
                            method: "post",
                            data: {
                                userId: limit.START_ID,
                                end: this.end
                            }
                        }).then(() => {
                            _vue.$message.success("限制成功")
                            this.dialogForm = false;
                            this.end = '';
                        }).catch(() => {
                            _vue.$message.error("限制失败")
                        })
                    })
                }
            },
        }
    }
</script>

<style scoped>

</style>
