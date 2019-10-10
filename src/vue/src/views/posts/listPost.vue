<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <h2 class="text-left" style="color: #409eff;">帖子管理：</h2>
        </el-form-item>
      </el-form>
    </div>
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <div style="float: left">
            <el-date-picker
              v-model="listQuery.baseCreateTime"
              style="width: 150px;"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              type="date"
              placeholder="发帖时间">
            </el-date-picker>
            <el-input v-model="listQuery.typeName" placeholder="发帖类型" style="width: 150px;"></el-input>
            <el-input v-model="listQuery.labels" placeholder="发帖标签" style="width: 150px;"></el-input>
            <el-input v-model="listQuery.wxNickName" placeholder="发帖人" style="width: 150px;"></el-input>
            <el-button type="primary" plain @click="getList">搜索</el-button>
          </div>
        </el-form-item>
      </el-form>
    </div>
    <el-table :data="list"
              v-loading.body="listLoading" element-loading-text="拼命加载中" border fit
              highlight-current-row>
      <el-table-column align="center" label="序号" width="80">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column >
      <el-table-column align="center" label="发帖人" prop="wxNickName"></el-table-column>
      <el-table-column align="center" label="发帖类型" prop="typeName"></el-table-column>
      <el-table-column align="center" label="帖子内容" prop="content" width="500"></el-table-column>
      <el-table-column align="center" label="最小价格" prop="minPrice"></el-table-column>
      <el-table-column align="center" label="最大价格" prop="maxPrice"></el-table-column>
      <el-table-column align="center" label="手机号码" prop="phone"></el-table-column>
      <el-table-column align="center" label="地址" prop="address"></el-table-column>
      <el-table-column align="center" label="标签" prop="labels">
        <template slot-scope="scope">
          <el-tag v-for="(item) in list[scope.$index].labels.split(',').slice(0, list[scope.$index].labels.split(',').length-1)" :key="index" type="success">
            {{item}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="真实浏览量" prop="totalBrowse"></el-table-column>
      <el-table-column align="center" label="真实点赞量" prop="totalResonate"></el-table-column>
      <el-table-column align="center" label="偏移浏览量" prop="devBrowse"></el-table-column>
      <el-table-column align="center" label="偏移点赞量" prop="devResonate"></el-table-column>
      <el-table-column align="center" label="活跃时间" prop="activeTime"></el-table-column>
      <el-table-column align="center" label="创建时间" prop="baseCreateTime"></el-table-column>
      <el-table-column align="center" label="是否下架" prop="isLowerShelf">
        <template slot-scope="scope">
          <p v-if="list[scope.$index].isLowerShelf == 0">
            {{"否"}}
          </p>
          <p v-if="list[scope.$index].isLowerShelf == 1">
            {{"是"}}
          </p>
        </template>
      </el-table-column>
      <el-table-column align="center" label="是否删除" prop="isDelBase" style="width: 100px;">
        <template slot-scope="scope">
          <p v-if="list[scope.$index].isDelBase == 0">
            {{"否"}}
          </p>
          <p v-if="list[scope.$index].isDelBase == 1">
            {{"是"}}
          </p>
        </template>
      </el-table-column>
      <el-table-column align="center" label="总收藏量" prop="totalCollection"></el-table-column>
      <el-table-column align="center" label="总关注量" prop="totalComments"></el-table-column>
      <el-table-column align="center" label="是否有中介费" prop="fee">
        <template slot-scope="scope">
          <p v-if="list[scope.$index].fee == 0">
            {{"否"}}
          </p>
          <p v-if="list[scope.$index].fee == 1">
            {{"是"}}
          </p>
        </template>
      </el-table-column>
      <el-table-column align="center" label="是否为地铁周边">
        <template slot-scope="scope">
          <p v-if="list[scope.$index].isSub == 0">
            {{"否"}}
          </p>
          <p v-if="list[scope.$index].isSub == 1">
            {{"是"}}
          </p>
        </template>
      </el-table-column>
      <el-table-column align="center" label="是否为商圈附近" prop="isMall">
        <template slot-scope="scope">
          <p v-if="list[scope.$index].isMall == 0">
            {{"否"}}
          </p>
          <p v-if="list[scope.$index].isMall == 1">
            {{"是"}}
          </p>
        </template>
      </el-table-column>
      <el-table-column align="center" fixed="right" label="操作" width="150">
        <template slot-scope="scope">
          <el-button type="text" size="medium" @click="showPostUpdate(scope.$index)">修改</el-button>
          <el-button type="text" size="medium" @click="showNavTop(scope.$index)">置顶</el-button>
          <el-button type="text" size="medium" v-if="list[scope.$index].isLowerShelf==1" @click="updateIsLowerShelfU(scope.$index)">上架</el-button>
          <el-button type="text" size="medium" v-if="list[scope.$index].isLowerShelf==0" @click="updateIsLowerShelfD(scope.$index)">下架</el-button>
          <el-button type="text" size="medium" @click="showComment(scope.$index)">查看评论</el-button>
          <el-button type="text" size="medium" v-if="list[scope.$index].isDelBase==0" @click="updatePostIsDelD(scope.$index)">删除</el-button>
          <el-button type="text" size="medium" v-if="list[scope.$index].isDelBase==1" @click="updatePostIsDelR(scope.$index)">恢复</el-button>
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

    <el-dialog v-if="dialogStatus=='create'" :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <div style="width: 100%;height: 50px;text-align: center" required v-if="dialogStatus=='create'">
        <div v-for="item in listNav">
          <el-checkbox-group v-model="chooseCheckbox">
            <div style="height: 50px;width: 120px;float: left">
              <el-checkbox-button :label="item.navigationId">{{item.title}}</el-checkbox-button>
            </div>
          </el-checkbox-group>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="success" @click="updateTopIsDel(chooseCheckbox)">确定</el-button>
      </div>
    </el-dialog>

    <el-dialog fullscreen v-if="dialogStatus=='update'" :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form class="small-space" :model="tempPost" label-position="left" label-width="80px"
               status-icon :rules="rules" ref="tempPost" v-if="dialogStatus=='update'">
        <el-form-item label="发帖人" style="width: 300px">
          <el-input type="text" v-model="tempPost.wxNickName" disabled="disabled">
          </el-input>
        </el-form-item>
        <el-form-item label="发帖类型">
          <el-select v-model="tempPost.id" placeholder="请选择">
            <el-option
              v-for="item in listPostType"
              :key="item.id"
              :label="item.typeName"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="帖子内容" style="width: 300px" prop="content">
          <el-input type="text" placeholder="请输入" v-model="tempPost.content">
          </el-input>
        </el-form-item>
        <el-form-item label="最小价格" style="width: 300px" prop="minPrice">
          <el-input placeholder="请输入" v-model.number="tempPost.minPrice">
          </el-input>
        </el-form-item>
        <el-form-item label="最大价格" style="width: 300px" prop="maxPrice">
          <el-input placeholder="请输入" v-model.number="tempPost.maxPrice">
          </el-input>
        </el-form-item>
        <el-form-item label="手机号码" style="width: 300px" prop="phone">
          <el-input placeholder="请输入" v-model.number="tempPost.phone">
          </el-input>
        </el-form-item>
        <el-form-item label="地址" style="width: 300px" prop="address">
          <el-input type="text" placeholder="请输入" v-model="tempPost.address">
          </el-input>
        </el-form-item>

        <el-form-item label="标签" style="text-align: center">
          <div v-for="postLabel in listPostLabel">
          <el-checkbox-group v-model="choosePostLabel">
            <div style="height: 50px;width: 120px;float: left">
              <el-checkbox-button :label="postLabel.labelId">{{postLabel.labelContent}}</el-checkbox-button>
            </div>
          </el-checkbox-group>
          </div>
        </el-form-item>

        <el-form-item label="真实浏览量" style="width: 300px" prop="totalBrowse">
          <el-input placeholder="请输入" v-model.number="tempPost.totalBrowse">
          </el-input>
        </el-form-item>
        <el-form-item label="真实点赞量" style="width: 300px" prop="totalResonate">
          <el-input placeholder="请输入" v-model.number="tempPost.totalResonate">
          </el-input>
        </el-form-item>
        <el-form-item label="偏移浏览量" style="width: 300px" prop="devBrowse">
          <el-input placeholder="请输入" v-model.number="tempPost.devBrowse">
          </el-input>
        </el-form-item>
        <el-form-item label="偏移点赞量" style="width: 300px" prop="devResonate">
          <el-input placeholder="请输入" v-model.number="tempPost.devResonate">
          </el-input>
        </el-form-item>
        <el-form-item label="活跃时间" style="width: 300px">
          <el-date-picker
            v-model="tempPost.activeTime"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
            type="datetime"
            placeholder="选择日期时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="创建时间" style="width: 300px">
          <el-date-picker
            v-model="tempPost.baseCreateTime"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
            type="datetime"
            placeholder="选择日期时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="总收藏量" style="width: 300px" prop="totalCollection">
          <el-input placeholder="请输入" v-model.number="tempPost.totalCollection">
          </el-input>
        </el-form-item>
        <el-form-item label="总关注量" style="width: 300px" prop="totalComments">
          <el-input placeholder="请输入" v-model.number="tempPost.totalComments">
          </el-input>
        </el-form-item>
        <el-form-item label="是否有中介费">
          <el-switch
            v-model="tempPost.fee"
            active-text="是"
            inactive-text="否">
          </el-switch>
        </el-form-item>
        <el-form-item label="是否为地铁周边">
          <el-switch
            v-model="tempPost.isSub"
            active-text="是"
            inactive-text="否">
          </el-switch>
        </el-form-item>
        <el-form-item label="是否为商圈附近">
          <el-switch
            v-model="tempPost.isMall"
            active-text="是"
            inactive-text="否">
          </el-switch>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" v-if="dialogStatus=='update'" @click="updatePost(choosePostLabel,'tempPost')">修 改</el-button>
      </div>
    </el-dialog>

    <el-dialog v-if="dialogStatus=='show'" :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
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

    <el-dialog v-if="dialogStatus=='showlimit'" :title="textMap[dialogStatus]" :visible.sync="dialogForm">
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
      var checkPhone = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('不能为空'));
        }
        setTimeout(() => {
          if (new RegExp(/(^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$)/).test(value) == false) {
            callback(new Error('请输入正确手机号码'));
          } else {
            callback();
          }
        }, 1000);
      };
      return {
        listLoading: false,//数据加载等待动画
        totalCount: 0, //分页组件--数据总条数
        list: [],//表格的数据
        listPostType: [],//所有帖子类型
        listNav: [],//导航表里的所有导航栏名称
        onCheckNav: [],
        listNavigationTop: [],//listNavigationTop的Dao方法查询出来的结果
        chooseCheckbox: [],//帖子所置顶的所有导航栏(模块)的名称
        listPostLabel: [],//标签表里的所有标签名称
        choosePostLabel: [],//已选择的标签Id
        listQuery: {
          page: 1,//页码
          limit: 5,//每页条数
          baseCreateTime: '',//查询发帖时间
          typeName: '',//查询发帖类型
          labels: '',//查询便签内容
          wxNickName: '',//查询发帖人
        },
        postAction:{
          postId:'',
          listCheckNavName: [],
          listCheckNavName1:[]//没被选中的导航栏名称
        },
        postLabel:{
          postId:'',
          listCheckLabelName: [],
          listCheckLabelName1: []//没被选中的标签名称
        },
        tempPost: {
          wxNickName: '',           id: '',
          content: '',              minPrice: '',
          maxPrice: '',             phone: '',
          address: '',              labels: '',
          totalBrowse: '',          totalResonate: '',
          devBrowse: '',            devResonate: '',
          activeTime: '',           baseCreateTime: '',
          isLowerShelf: '',         isDelBase: '',
          totalCollection: '',      totalComments: '',
          fee: '',                  isSub: '',
          isMall: '',               postId:'',

        },
        dialogStatus: 'create',
        dialogFormVisible: false,
        dialogForm: false,
        textMap: {
          update: '编辑',
          create: '选择模块',
          show: '帖子评论信息',
          showlimit: '限制'
        },
        rules: {
          content: [
            { required: true, message: '请输入帖子内容', trigger: 'blur' },
            { min: 3, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur' }
          ],
          minPrice: [
            { validator: checkMan, trigger: 'blur' }
          ],
          maxPrice: [
            { validator: checkMan, trigger: 'blur' }
          ],
          phone: [
            { validator: checkPhone, trigger: 'blur' }
          ],
          address: [
            { required: true, message: '地址不能为空', trigger: 'blur' }
          ],
          totalBrowse: [
            { validator: checkMan, trigger: 'blur' }
          ],
          totalResonate: [
            { validator: checkMan, trigger: 'blur' }
          ],
          devBrowse: [
            { validator: checkMan, trigger: 'blur' }
          ],
          devResonate: [
            { validator: checkMan, trigger: 'blur' }
          ],
          totalCollection: [
            { validator: checkMan, trigger: 'blur' }
          ],
          totalComments: [
            { validator: checkMan, trigger: 'blur' }
          ]
        },
        end: '',
        collectionList: {
          ID: '',
          CONTENT: '',
          IS_DEL: '',
          CREATE_TIME: '',
          SORT_TIME: '',
          START_ID: '',
        },
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
      this.getPostType();
    },
    methods: {
      getList() {
        //查询列表
        this.listLoading = true;
        this.api({
          url: "/FPost/listPostF",
          method: "post",
          params: this.listQuery,
        }).then(reqs => {
          this.listLoading = false;
          this.list = reqs.data;
          this.totalCount = reqs.count;
        });
      },
      getPostType(){
        this.api({
          url: "/FPost/listPostTypeF",
          method: "post"
        }).then(data => {
          this.listPostType = data.data;
        })
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
      updatePostIsDelD($index) {
        this.$confirm('确定删除该帖子?', '提示', {
          confirmButtonText: '确定',
          showCancelButton: false,
          type: 'warning'
        }).then(() => {
          var postId = this.list[$index].postId;
          this.postAction.postId = postId;
          this.api({
            url: "/FPost/updatePostIsDelF",
            method: "post",
            params: this.postAction,
          }).then(() => {
            this.getList()
          }).catch(() => {
            this.$message.error("删除失败")
          })
        })
      },
      updatePostIsDelR($index) {
        this.$confirm('确定恢复该帖子?', '提示', {
          confirmButtonText: '确定',
          showCancelButton: false,
          type: 'warning'
        }).then(() => {
          var postId = this.list[$index].postId;
          this.postAction.postId = postId;
          this.api({
            url: "/FPost/updatePostIsDelR",
            method: "post",
            params: this.postAction,
          }).then(() => {
            this.getList()
          }).catch(() => {
            this.$message.error("恢复失败")
          })
        })
      },
      updateIsLowerShelfU($index) {
        this.$confirm('确定上架该帖子?', '提示', {
          confirmButtonText: '确定',
          showCancelButton: false,
          type: 'warning'
        }).then(() => {
          var postId = this.list[$index].postId;
          this.postAction.postId = postId;
          this.api({
            url: "/FPost/updateIsLowerShelfU",
            method: "post",
            params: this.postAction,
          }).then(() => {
            this.getList()
          }).catch(() => {
            this.$message.error("上架失败")
          })
        })
      },
      updateIsLowerShelfD($index) {
        this.$confirm('确定下架该帖子?', '提示', {
          confirmButtonText: '确定',
          showCancelButton: false,
          type: 'warning'
        }).then(() => {
          var postId = this.list[$index].postId;
          this.postAction.postId = postId;
          this.api({
            url: "/FPost/updateIsLowerShelfD",
            method: "post",
            params: this.postAction,
          }).then(() => {
            this.getList()
          }).catch(() => {
            this.$message.error("下架失败")
          })
        })
      },
      showNavTop($index){
        this.listLoading = true;
        this.listNav = [];
        this.chooseCheckbox = [];//初始化,防止之前push的数据还残留
        this.api({
          url: "/FPost/listNavF",
          method: "post",
        }).then(reqs => {
          this.listLoading = false;
          //对listNav数组初始化，防止点击多次置顶，导航栏复选框重复添加到数组而没有清除数据
          for (var i = 0; i < reqs.data.length; i++) {
            this.listNav.push(reqs.data[i]);
          };
        })
        var postId = this.list[$index].postId;
        this.postAction.postId = postId;
        this.api({
          url: "/FPost/getListNavName",
          method: "post",
          data: this.postAction,
        }).then(reqs => {
          this.listLoading = false;
          for (var i = 0; i < reqs.data.length; i++) {
            this.chooseCheckbox.push(reqs.data[i].navigationTopId);
          };
        })
        this.dialogStatus = "create"
        this.dialogFormVisible = true
      },
      updateTopIsDel(chooseCheckbox) {
        this.postAction.listCheckNavName1 = [];
        this.onCheckNav = [];
        this.postAction.listCheckNavName = chooseCheckbox;
        for (let a = 0; a < this.listNav.length; a++) {
          this.postAction.listCheckNavName1.push(this.listNav[a].navigationId);
        };
        for (let i = 0; i < this.postAction.listCheckNavName1.length; i++) {
          //判断选中的值是否在所有导航Id的集合里面
          if (chooseCheckbox.indexOf(this.postAction.listCheckNavName1[i])==-1) {
            this.onCheckNav.push(this.postAction.listCheckNavName1[i]);
          }
        }
        this.postAction.listCheckNavName1 = this.onCheckNav;
        if(this.postAction.listCheckNavName1 == undefined||this.postAction.listCheckNavName1.length <= 0){
          //获取选中的navigationTopId，查询t_navigation_top表对应postId和navigationTopId的数据是否存在
          //获取选中的navigationTopId，查询t_navigation_top表对应postId和navigationTopId的数据是否存在
          for (let i = 0; i < chooseCheckbox.length; i++) {
            //将axios默认的异步请求转成同步
            this.funcAsync(chooseCheckbox[i]);
          }
          this.api({
            url: "/FPost/updateTopIsDel0",
            method: "post",
            data:this.postAction,
          }).then(() => {
            this.getList()
            this.dialogFormVisible = false
          }).catch(() => {
            this.$message.error("置顶失败")
          })
        }else if (this.chooseCheckbox == undefined||this.chooseCheckbox.length <= 0){
          this.api({
            url: "/FPost/updateTopIsDel1",
            method: "post",
            data:this.postAction,
          }).then(() => {
            this.getList()
            this.dialogFormVisible = false
          }).catch(() => {
            this.$message.error("置顶失败")
          })
        } else {
          //获取选中的navigationTopId，查询t_navigation_top表对应postId和navigationTopId的数据是否存在
          for (let i = 0; i < chooseCheckbox.length; i++) {
            //将axios默认的异步请求转成同步
            this.funcAsync(chooseCheckbox[i]);
          }
          this.api({
            url: "/FPost/updateTopIsDel0",
            method: "post",
            data:this.postAction,
          }).then(() => {
            this.getList()
            this.dialogFormVisible = false
          }).catch(() => {
            this.$message.error("置顶失败")
          })
          this.api({
            url: "/FPost/updateTopIsDel1",
            method: "post",
            data:this.postAction,
          }).then(() => {
            this.getList()
            this.dialogFormVisible = false
          }).catch(() => {
            this.$message.error("置顶失败")
          })
        }
      },
      showPostUpdate($index){
        this.listPostLabel = [];
        this.choosePostLabel = [];//初始化,防止之前push的数据还残留
        this.postLabel.listCheckLabelName = [];
        this.postLabel.listCheckLabelName1 = [];
        this.api({
          url: "/FPost/listPostLabelF",
          method: "post",
        }).then(reqs => {
          this.listLoading = false;
          //对数组初始化，防止点击多次置顶，导航栏复选框重复添加到数组而没有清除数据
          for (var i = 0; i < reqs.data.length; i++) {
            this.listPostLabel.push(reqs.data[i]);
          };
        })
        var postId = this.list[$index].postId;
        this.postLabel.postId = postId;
        this.api({
          url: "/FPost/getListLabelName",
          method: "post",
          data: this.postLabel,
        }).then(reqs => {
          this.listLoading = false;
          for (var i = 0; i < reqs.data.length; i++) {
            this.choosePostLabel.push(reqs.data[i].postLabelId);
          };
        })
        this.listLoading = true;
        let post = this.list[$index];
        this.tempPost.wxNickName = post.wxNickName;
        this.tempPost.id = post.id;
        this.tempPost.content = post.content;
        this.tempPost.minPrice = post.minPrice;
        this.tempPost.maxPrice = post.maxPrice;
        this.tempPost.phone = post.phone;
        this.tempPost.address = post.address;
        this.tempPost.totalBrowse = post.totalBrowse;
        this.tempPost.totalResonate = post.totalResonate;
        this.tempPost.devBrowse = post.devBrowse;
        this.tempPost.devResonate = post.devResonate;
        this.tempPost.activeTime = post.activeTime;
        this.tempPost.baseCreateTime = post.baseCreateTime;
        this.tempPost.isLowerShelf = post.isLowerShelf;
        this.tempPost.isDelBase = post.isDelBase;
        this.tempPost.totalCollection = post.totalCollection;
        this.tempPost.totalComments = post.totalComments;
        if (post.fee == 0) {
          this.tempPost.fee = false;
        }else {
          this.tempPost.fee = true;
        }
        if (post.isSub == 0) {
          this.tempPost.isSub = false;
        }else {
          this.tempPost.isSub = true;
        }
        if (post.isMall == 0) {
          this.tempPost.isMall = false;
        }else {
          this.tempPost.isMall = true;
        }
        this.dialogStatus = "update"
        this.dialogFormVisible = true
      },
      updatePost(choosePostLabel,data){
        this.$refs[data].validate((valid) => {//elementUI表单点击提交后执行的函数，校验是否满足自定义规则
          if (valid) {
            if (choosePostLabel.length>5) {
              this.$message.error("标签最多只能选择5个")
            }else {
              for (let a = 0; a < this.listPostLabel.length; a++) {
                this.postLabel.listCheckLabelName1.push(this.listPostLabel[a].labelId);
              };
              if(choosePostLabel == undefined||choosePostLabel.length <= 0){
                //先删除对应行的postId的t_post_base_label所有数据
                this.api({
                  url: "/FPost/deleteBaseLabelF",
                  method: "post",
                  data:this.postLabel,
                }).then(() => {
                }).catch(() => {
                  this.$message.error("系统异常")
                })
                for (let x = 0; x < choosePostLabel.length; x++) {
                  //重新添加数据
                  this.api({
                    url: "/FPost/insertBaseLabelF",
                    method: "post",
                    params:{
                      postId:this.postLabel.postId,
                      postLabelId:choosePostLabel[x]
                    },
                  }).then(() => {
                  }).catch(() => {
                    this.$message.error("系统异常")
                  })
                }
                if (this.tempPost.fee == false) {
                  this.tempPost.fee = 0;
                }else {
                  this.tempPost.fee = 1;
                }
                if (this.tempPost.isSub == false) {
                  this.tempPost.isSub = 0;
                }else {
                  this.tempPost.isSub = 1;
                }
                if (this.tempPost.isMall == false) {
                  this.tempPost.isMall = 0;
                }else {
                  this.tempPost.isMall = 1;
                }
                this.tempPost.postId = this.postLabel.postId;
                this.postLabel.listCheckLabelName = choosePostLabel;
                //修改帖子信息
                this.api({
                  url: "/FPost/updatePostF",
                  method: "post",
                  data:this.tempPost,
                }).then(() => {
                  this.$message.success("修改成功")
                  this.getList()
                  this.dialogFormVisible = false
                }).catch(() => {
                  this.$message.error("修改失败")
                })
              }else if (choosePostLabel == undefined||choosePostLabel.length <= 0){
                this.api({
                  url: "/FPost/updateBaseLabelIsDel1",
                  method: "post",
                  data:this.postLabel,
                }).then(() => {
                }).catch(() => {
                  this.$message.error("系统异常")
                })
                if (this.tempPost.fee == false) {
                  this.tempPost.fee = 0;
                }else {
                  this.tempPost.fee = 1;
                }
                if (this.tempPost.isSub == false) {
                  this.tempPost.isSub = 0;
                }else {
                  this.tempPost.isSub = 1;
                }
                if (this.tempPost.isMall == false) {
                  this.tempPost.isMall = 0;
                }else {
                  this.tempPost.isMall = 1;
                }
                this.tempPost.postId = this.postLabel.postId;
                this.postLabel.listCheckLabelName = choosePostLabel;
                //修改帖子信息
                this.api({
                  url: "/FPost/updatePostF",
                  method: "post",
                  data:this.tempPost,
                }).then(() => {
                  this.$message.success("修改成功")
                  this.getList()
                  this.dialogFormVisible = false
                }).catch(() => {
                  this.$message.error("修改失败")
                })
              } else {
                //先删除对应行的postId的t_post_base_label所有数据
                this.api({
                  url: "/FPost/deleteBaseLabelF",
                  method: "post",
                  data:this.postLabel,
                }).then(() => {
                }).catch(() => {
                  this.$message.error("系统异常")
                })
                for (let x = 0; x < choosePostLabel.length; x++) {
                  //重新添加数据
                  this.api({
                    url: "/FPost/insertBaseLabelF",
                    method: "post",
                    params:{
                      postId:this.postLabel.postId,
                      postLabelId:choosePostLabel[x]
                    },
                  }).then(() => {
                  }).catch(() => {
                    this.$message.error("系统异常")
                  })
                }
                if (this.tempPost.fee == false) {
                  this.tempPost.fee = 0;
                }else {
                  this.tempPost.fee = 1;
                }
                if (this.tempPost.isSub == false) {
                  this.tempPost.isSub = 0;
                }else {
                  this.tempPost.isSub = 1;
                }
                if (this.tempPost.isMall == false) {
                  this.tempPost.isMall = 0;
                }else {
                  this.tempPost.isMall = 1;
                }
                this.tempPost.postId = this.postLabel.postId;
                this.postLabel.listCheckLabelName = choosePostLabel;
                //修改帖子信息
                this.api({
                  url: "/FPost/updatePostF",
                  method: "post",
                  data:this.tempPost,
                }).then(() => {
                  this.$message.success("修改成功")
                  this.getList()
                  this.dialogFormVisible = false
                }).catch(() => {
                  this.$message.error("修改失败")
                })
              }
            }
          } else {
            this.$message.error("数据不规范，请检查")
            return false;
          }
        });
      },
      async funcAsync(data){
        await this.funcAwait(data);
        if (this.listNavigationTop == undefined||this.listNavigationTop.length <= 0) {
          this.api({
            url: "/FPost/insertTopF",
            method: "post",
            params:{
              postId:this.postAction.postId,
              navigationTopId:data
            },
          }).then(() => {
          }).catch(() => {
            this.$message.error("系统异常")
          })
        }
      },
      funcAwait(data){
        return new Promise((resolve,reject)=>{
          this.api({
            url: "/FPost/listNavigationTop",
            method: "post",
            params:{
              postId:this.postAction.postId,
              navigationTopId:data
            },
          }).then(reqs => {
            resolve(reqs)
            this.listNavigationTop = [];
            this.listNavigationTop.push(reqs.data.topId);
          }).catch(err => {
            reject(err)
          })
        })
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
        this.commentlistQuery.postId = comment.postId;
        this.dialogStatus = "show"
        this.dialogFormVisible = true
        this.getCommentList();
      },
      getCommentList() {
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
