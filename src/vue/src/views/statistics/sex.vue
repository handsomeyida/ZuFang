<template>
  <div>
    <div>
      <h2 class="text-left" style="color: #409eff;">性别统计:</h2>
    </div>
    <div>
      <!--  性别统计  -->
      <div id="sexCart" class="sexCart" :style="{width: '1200px', height: '600px'}"></div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "statistics",
    data() {
      return {
        listLoading: false,//数据加载等待动画
        boy: 0,
        girl: 0,
        listresonate: [],
      }
    },
    created() {
      this.getList();
    },
    methods: {
      getList() {
        this.listLoading = true;
        this.api({
          url: "/statistics/listwxuser",
          method: "get",
        }).then(data => {
          this.listLoading = false;
          // console.log(data.list[0].wx_gender)
          for (var a=0; a<data.list.length; a++) {
            if (data.list[a].wx_gender == 1) {
              this.boy++;
            } else {
              this.girl++;
            }
          }
          this.drawLine();
        })
      },
      drawLine() {
        // 基于准备好的dom，初始化echarts实例
        var myChart = this.$echarts.init(document.getElementById('sexCart'))
        // 绘制图表
        myChart.setOption({
          color: ['#37a2da', '#ffdb5c'],
          title: {
            text: '租房用户性别统计',
            subtext: '微信用户',
            x: 'center'
          },
          tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c}人 ({d}%)"
          },
          legend: {
            orient: 'vertical',
            left: 'left',
            data: ['男', '女']
          },
          series: [
            {
              name: '性别统计',
              type: 'pie',
              radius: '55%',
              center: ['50%', '60%'],
              data: [
                {value: this.boy, name: '男'},
                {value: this.girl, name: '女'}
              ],
              itemStyle: {
                emphasis: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              }
            }
          ]
        });
      }
    }
  }
</script>

<style scoped>

  .sexCart{
    margin: 20px auto;
    box-shadow: 0 0 10px #6c8286;
  }

</style>
