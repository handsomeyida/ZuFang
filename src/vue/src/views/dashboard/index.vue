<template>
  <div class="dashboard-container">
    <div class="tx">
      <a href="https://user.qzone.qq.com/867256260/infocenter" target="_blank"><img class="tximg"
                                                                                    src="http://pic85.nipic.com/file/20151218/17926741_084024242000_2.jpg">
      </a>
    </div>
  </div>
</template>

<script>
    export default {
        name: 'dashboard',
        data() {
            return {
                userId: '',
                role: '',
                badId: '',
                badname: '',
                nickname: '',
                message: [],
                username: [],
                AllMesg: [],
                notifyPromise: Promise.resolve(),
            }
        },
        created() {
            this.loadUser();
            this.getMesg();
        },
        methods: {
            loadUser() {
                this.userId = this.$store.state.user.userId;
                this.badId = this.$store.state.user.badId;
                this.badname = this.$store.state.user.badname;
                this.role = this.$store.state.user.role;
                if (this.userId == this.badId && this.role != this.badname) {
                    let _vue = this;
                    _vue.api({
                        url: '/backstageuser/updateuser',
                        method: 'post',
                        data: {
                            userId: this.userId
                        },
                    }).then((data) => {
                        this.$store.dispatch('LogOut').then(() => {
                            location.reload();
                        })
                    })
                }
            },
            //获取用户收到的消息并弹框显示
            getMesg() {
                this.userId = this.$store.state.user.userId;
                this.nickname = this.$store.state.user.nickname;

                this.api({
                    url: '/information/loadUserMsg',
                    method: "post",
                    data: {
                        userId: this.userId
                    }
                }).then((data) => {
                    // console.log(data);
                    this.AllMesg = data.list;
                    for (let a = 0; a < this.AllMesg.length; a++) {
                        if (this.AllMesg[a].IS_DEL == 0) {
                            this.message.push(this.AllMesg[a].CONTENT);
                            this.username.push(this.AllMesg[a].START_ID);
                        }
                    }
                    // console.log(this.message);
                    if (this.message.length > 3) {
                        let msgcount = '你有' + this.message.length + '条未读消息';
                        this.showWords(msgcount);
                    } else {
                        for (let a = 0; a < this.message.length; a++) {
                            this.showWords(this.username[a] + ":" + this.message[a]);
                        }
                    }
                });
            },
            //显示公告
            showWords(data) {
                this.notifyPromise = this.notifyPromise.then(this.$nextTick).then(() => {
                    this.$notify({
                        type: 'warning',
                        title: '未读消息',
                        message: data,
                        duration: 5000,
                    });
                });
            },

        }
    }
</script>

<style>
  .tx {
    text-align: center
  }

  .tximg {
    vertical-align: middle
  }

  img {
    width: 1730px;
    height: 880px;
    margin: 0 auto;
  }
</style>
