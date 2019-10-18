<template>
  <div class="block">
    <h2 class="text-left" style="color: #409eff;">{{Title[checked]}}</h2>
    <div class="top">
      <el-select @change="checkoption" v-model="checked" placeholder="请选择">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <span class="demonstration">周</span>
      <el-date-picker @change="theweek" v-model="datatime" type="week" format="yyyy年第WW周"
                      placeholder="选择周"></el-date-picker>
      <span class="demonstration">月</span>
      <el-date-picker @change="themonths" v-model="months" type="month" format="yyyy年MM月" value-format="yyyy年MM月"
                      placeholder="选择月"></el-date-picker>
    </div>
    <div>
      <!--  商圈周边统计  -->
      <div v-if="checked == 'shopping'" id="shoppingCart" class="resonateCart"
           :style="{width: '1200px', height: '600px'}"></div>
      <!--  地铁周边统计  -->
      <div v-if="checked == 'subway'" id="subwayCart" class="resonateCart"
           :style="{width: '1200px', height: '600px'}"></div>
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
                Title: {
                    shopping: '商圈周边统计',
                    subway: '地铁周边统计'
                },
                checked: 'shopping',
                options: [{
                    value: 'shopping',
                    label: '商圈周边统计'
                }, {
                    value: 'subway',
                    label: '地铁周边统计'
                }]
            }
        },
        created() {
            this.defaulttime();
        },
        methods: {
            defaulttime() {
                let date = new Date();
                this.months = date;
                let year = this.months.getFullYear();
                let month = this.months.getMonth();
                let moths = month+1;
                if (moths < 10) {
                    this.months = year+'年0'+moths+'月';
                } else {
                    this.months = year+'年'+moths+'月';
                }
                this.themonths();
            },
            checkoption() {
                this.weeks = '';
                this.months = '';
                this.defaulttime();
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

                if (this.checked == 'shopping') {
                    this.getWeeksShopping();
                } else if (this.checked == 'subway') {
                    this.getWeeksSubway();
                }
            },
            themonths() {
                if (this.checked == 'shopping') {
                    this.getMonthsShopping();
                } else if (this.checked == 'subway') {
                    this.getMonthsSubway();
                }
            },
            getWeeksShopping() {
                this.Status = 'weeks';
                this.listLoading = true;
                this.api({
                    url: "/statistics/listweekshopping",
                    method: "get",
                }).then(data => {
                    this.listLoading = false;
                    this.listtitle = [];
                    this.listresonate = [];
                    this.week = this.weeks;
                    this.time = this.weeks;
                    for (var a = 0; a < data.list.length; a++) {
                        if (data.list[a].weeks == this.weeks) {
                            this.listresonate.push(data.list[a].count);
                            this.listtitle.push(data.list[a].title);
                        }
                    }
                    if (this.listresonate.length == 0 && this.checked == 'shopping') {
                        this.$message.error('当前周无浏览量数据')
                    }
                    this.drawLine();
                })
            },
            getMonthsShopping() {
                this.Status = 'months';
                this.listLoading = true;
                this.api({
                    url: "/statistics/listmonthshopping",
                    method: "get",
                }).then(data => {
                    this.listLoading = false;
                    this.listtitle = [];
                    this.listresonate = [];
                    this.month = this.months;
                    this.time = this.months;
                    for (var a = 0; a < data.list.length; a++) {
                        if (data.list[a].weeks == this.months) {
                            this.listresonate.push(data.list[a].count);
                            this.listtitle.push(data.list[a].title);
                        }
                    }
                    if (this.listresonate.length == 0 && this.checked == 'shopping') {
                        this.$message.error('当前月无浏览量数据')
                    }
                    this.drawLine();
                })
            },
            getWeeksSubway() {
                this.Status = 'weeks';
                this.listLoading = true;
                this.api({
                    url: "/statistics/listweeksubway",
                    method: "get",
                }).then(data => {
                    this.listLoading = false;
                    this.listtitle = [];
                    this.listresonate = [];
                    this.week = this.weeks;
                    this.time = this.weeks;
                    for (var a = 0; a < data.list.length; a++) {
                        if (data.list[a].weeks == this.weeks) {
                            this.listresonate.push(data.list[a].count);
                            this.listtitle.push(data.list[a].title);
                        }
                    }
                    if (this.listresonate.length == 0 && this.checked == 'subway') {
                        this.$message.error('当前周无浏览量数据')
                    }
                    this.drawLine();
                })
            },
            getMonthsSubway() {
                this.Status = 'months';
                this.listLoading = true;
                this.api({
                    url: "/statistics/listmonthsubway",
                    method: "get",
                }).then(data => {
                    this.listLoading = false;
                    this.listtitle = [];
                    this.listresonate = [];
                    this.month = this.months;
                    this.time = this.months;
                    for (var a = 0; a < data.list.length; a++) {
                        if (data.list[a].weeks == this.months) {
                            this.listresonate.push(data.list[a].count);
                            this.listtitle.push(data.list[a].title);
                        }
                    }
                    if (this.listresonate.length == 0 && this.checked == 'subway') {
                        this.$message.error('当前月无浏览量数据')
                    }
                    this.drawLine();
                })
            },
            drawLine() {
                if (this.checked == 'shopping') {
                    var myChart = this.$echarts.init(document.getElementById('shoppingCart'))
                } else if (this.checked == 'subway') {
                    var myChart = this.$echarts.init(document.getElementById('subwayCart'))
                }
                myChart.setOption({
                    color: ['#37a2da', '#32c5e9', '#67e0e3', '#9fe6b8', '#ffdb5c', '#749f83', '#ca8622', '#bda29a', '#6e7074', '#546570', '#c4ccd3'],
                    title: {
                        text: this.time + this.Title[this.checked] + '帖子浏览量排名',
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
    margin-left: 32%;
    margin-top: 20px;
  }
</style>
