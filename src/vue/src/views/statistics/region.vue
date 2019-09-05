<template>
  <div class="block">
    <h2 class="text-left" style="color: #409eff;">地区统计</h2>
    <div class="top">
      <span class="demonstration">周</span>
      <el-date-picker @change="theweek" v-model="datatime" type="week" format="yyyy年第WW周"
                      placeholder="选择周"></el-date-picker>
      <span class="demonstration">月</span>
      <el-date-picker @change="themonths" v-model="months" type="month" format="yyyy年MM月" value-format="yyyy年MM月"
                      placeholder="选择月"></el-date-picker>
    </div>
    <div>
      <!--  地区统计  -->
      <div id="regionCart" class="resonateCart" :style="{width: '1200px', height: '600px'}"></div>
    </div>
  </div>
</template>

<script>
    export default {
        name: "region",
        data() {
            return {
                listLoading: false,//数据加载等待动画
                listtitle: [],
                listresonate: [],

                week: '',
                weeks: '',
                month: '',
                months: '',
                time: '',
                datatime: '',
                Status: 'weeks',
            }
        },
        create() {

        },
        methods: {
            checkoption() {
                this.weeks = '';
                this.months = '';
            },
            theweek(val) {
                //周时间选择器有bug，使用自己写的方法来获取是今年的哪一周
                let temptTime = val;
                let weekday = temptTime.getDay() || 7;
                temptTime.setDate(temptTime.getDate() - weekday + 1 + 5);
                let firstDay = new Date(temptTime.getFullYear(), 0, 1);
                let dayOfWeek = firstDay.getDay();
                let spendDay = 1;
                if (dayOfWeek != 0) {
                    spendDay = 7 - dayOfWeek + 1
                }
                firstDay = new Date(temptTime.getFullYear(), 0, 1 + spendDay)
                let d = Math.ceil((temptTime.valueOf() - firstDay.valueOf()) / 86400000)
                let result = Math.ceil(d / 7);
                let zhou = result + 1;
                this.weeks = val.getFullYear() + "年第" + zhou + "周";
            },
            themonths() {

            },
            getWeeksResonate() {
                this.Status = 'weeks';
                this.listLoading = true;
                this.api({
                    url: "/statistics/listweeksresonate",
                    method: "get",
                }).then(data => {
                    this.listLoading = false;
                    this.listtitle = [];
                    this.listresonate = [];
                    this.week = this.weeks;
                    this.time = this.weeks;
                    for (var a = 0; a < data.list.length; a++) {
                        if (data.list[a].weeks == this.weeks) {
                            this.listresonate.push(data.list[a].dev_resonate);
                            this.listtitle.push(data.list[a].title);
                        }
                    }
                    if (this.listresonate.length == 0 && this.checked == 'good') {
                        this.$message.error('当前周无点赞数据')
                    }
                    this.drawLine();
                })
            },
            getMonthsResonate() {
                this.Status = 'months';
                this.listLoading = true;
                this.api({
                    url: "/statistics/listmonthsresonate",
                    method: "get",
                }).then(data => {
                    this.listLoading = false;
                    this.listtitle = [];
                    this.listresonate = [];
                    this.month = this.months;
                    this.time = this.months;
                    for (var a = 0; a < data.list.length; a++) {
                        if (data.list[a].weeks == this.months) {
                            this.listresonate.push(data.list[a].dev_resonate);
                            this.listtitle.push(data.list[a].title);
                        }
                    }
                    if (this.listresonate.length == 0 && this.checked == 'good') {
                        this.$message.error('当前月无点赞数据')
                    }
                    this.drawLine();
                })
            },
            drawLine() {
                var myChart = this.$echarts.init(document.getElementById('regionCart'))
                myChart.setOption({
                    color: ['#37a2da', '#32c5e9', '#67e0e3', '#9fe6b8', '#ffdb5c', '#749f83', '#ca8622', '#bda29a', '#6e7074', '#546570', '#c4ccd3'],
                    title: {
                        text: this.time + this.Title[this.checked] + '排名',
                        subtext: '数据来自益达的数据库'
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    legend: {
                        data: this.listtitle
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis: {
                        type: 'value',
                        boundaryGap: [0, 0.01]
                    },
                    yAxis: {
                        type: 'category',
                        data: [this.time]
                    },
                    series: this.loadata(this.listresonate)
                }, true);
            },
            loadata(data) {
                var serie = [];
                for (var i = 0; i < this.listtitle.length; i++) {
                    var item = {
                        name: this.listtitle[i],
                        type: 'bar',
                        label: {
                            normal: {
                                show: true,
                                position: 'insideLeft',
                                fontSize: 26,
                                formatter: this.listtitle[i],
                            }
                        },
                        data: [data[i]]
                    };
                    serie.push(item);
                }
                return serie;
            }
        },
    }
</script>

<style scoped>
  .resonateCart {
    margin: 40px auto;
    box-shadow: 0 0 10px #6c8286;
  }

  .top {
    margin-left: 37%;
    margin-top: 20px;
  }
</style>
