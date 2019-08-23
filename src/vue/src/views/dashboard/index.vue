<template>
  <div class="dashboard-container">
    <div class="tx">
      <a href="https://user.qzone.qq.com/867256260/infocenter" target="_blank"><img class="tximg" src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1565870854203&di=2715c7960d3705a89539551832c221ca&imgtype=0&src=http%3A%2F%2Fbpic.588ku.com%2Felement_origin_min_pic%2F17%2F03%2F03%2F8721263b7aff2e254e92ef07e53304a6.jpg%2521%2Ffwfh%2F804x485%2Fquality%2F90%2Funsharp%2Ftrue%2Fcompress%2Ftrue">
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
        message: [],
        username: [],
        AllMesg: [],
        notifyPromise:Promise.resolve(),
      }
    },
    created() {
      this.getMesg();
    },
    methods: {
      getMesg() {
        this.userId = this.$store.state.user.userId;
        this.api({
          url: '/information/loadUserMsg',
          method: "post",
          data: {
            userId: this.userId
          }
        }).then((data)=> {
          // console.log(data);
          this.AllMesg = data.list;
          for (let a=0; a<this.AllMesg.length; a++){
            if (this.AllMesg[a].IS_DEL == 0) {
              this.message.push(this.AllMesg[a].CONTENT);
              this.username.push(this.AllMesg[a].START_ID);
            }
          }
          // console.log(this.message);
          if (this.message.length > 3) {
            let msgcount = '你有'+this.message.length+'条未读消息';
            this.showWords(msgcount);
          } else {
            for (let a=0; a<this.message.length; a++){
              this.showWords(this.username[a]+":"+this.message[a]);
            }
          }
        });
      },
      showWords(data) {
        this.notifyPromise = this.notifyPromise.then(this.$nextTick).then(()=> {
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
  .tx{
    text-align:center
  }
  .tximg{
    vertical-align:middle
  }
  img{
    width: 1730px;
    height: 880px;
    margin: 0 auto;
  }
</style>
