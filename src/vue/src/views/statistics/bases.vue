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
      <span v-if="checked == 'money'" class="demonstration">价位范围</span>
      <el-input v-if="checked == 'money'" v-model="Prices" placeholder="请输入价位范围" :style="{width: '100px'}"></el-input>
    </div>
    <div>
      <!--  点赞统计  -->
      <div v-if="checked == 'good'" id="goodCart" class="resonateCart"
           :style="{width: '1200px', height: '400px'}"></div>
      <!--  评论统计  -->
      <div v-if="checked == 'comment'" id="commentCart" class="resonateCart"
           :style="{width: '1200px', height: '400px'}"></div>
      <!--  收藏统计  -->
      <div v-if="checked == 'collection'" id="collectionCart" class="resonateCart"
           :style="{width: '1200px', height: '400px'}"></div>
      <!--  价位统计  -->
      <div v-if="checked == 'money'" id="maxmoneyCart" class="resonateCart"
           :style="{width: '1200px', height: '400px'}"></div>
      <div v-if="checked == 'money'" id="minmoneyCart" class="resonateCart"
           :style="{width: '1200px', height: '400px'}"></div>
    </div>
  </div>
</template>

<script>
    export default {
        name: "bases",
        data() {
            return {
                listLoading: false,//数据加载等待动画
                listtitle: [],
                listresonate: [],
                listmaxprice: [],
                listminprice: [],
                listmaxprices: [],
                listminprices: [],
                week: '',
                weeks: '',
                month: '',
                months: '',
                time: '',
                datatime: '',
                Prices: '',
                Status: 'weeks',
                Title: {
                    good: '点赞统计',
                    comment: '评论统计',
                    collection: '收藏统计',
                    money: '价位统计',
                },
                checked: 'good',
                options: [{
                    value: 'good',
                    label: '点赞统计'
                }, {
                    value: 'comment',
                    label: '评论统计'
                }, {
                    value: 'collection',
                    label: '收藏统计'
                }, {
                    value: 'money',
                    label: '价位统计'
                }]
            }
        },
        created() {

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

                this.listtitle = [];
                this.listresonate = [];
                this.listmaxprice = [];
                this.listminprice = [];
                this.listmaxprices = [];
                this.listminprices = [];

                if (this.checked == 'good') {
                    this.getWeeksResonate();
                } else if (this.checked == 'comment') {
                    this.getWeeksComment();
                } else if (this.checked == 'collection') {
                    this.getWeeksCollection();
                } else if (this.checked == 'money') {
                    if (this.Prices == null || this.Prices.length == 0 || this.Prices == '') {
                        // console.log(this.Prices)
                        this.$message.error('请输入价格范围')
                    } else {
                        this.getWeeksMaxMoney();
                        this.getWeeksMinMoney();
                    }
                }
            },
            themonths() {
                this.listtitle = [];
                this.listresonate = [];
                this.listmaxprice = [];
                this.listminprice = [];
                this.listmaxprices = [];
                this.listminprices = [];

                if (this.checked == 'good') {
                    this.getMonthsResonate();
                } else if (this.checked == 'comment') {
                    this.getMonthsComment();
                } else if (this.checked == 'collection') {
                    this.getMonthsCollection();
                } else if (this.checked == 'money') {
                    if (this.Prices == null || this.Prices.length == 0 || this.Prices == '') {
                        // console.log(this.Prices)
                        this.$message.error('请输入价格范围')
                    } else {
                        this.getMonthMaxMoney();
                        this.getMonthMinMoney();
                    }
                }
            },
            getWeeksResonate() {
                this.Status = 'weeks';
                this.listLoading = true;
                this.api({
                    url: "/statistics/listweeksresonate",
                    method: "get",
                }).then(data => {
                    this.listLoading = false;
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
            getWeeksComment() {
                this.Status = 'weeks';
                this.listLoading = true;
                this.api({
                    url: "/statistics/listweekscomment",
                    method: "get",
                }).then(data => {
                    this.listLoading = false;
                    this.week = this.weeks;
                    this.time = this.weeks;
                    for (var a = 0; a < data.list.length; a++) {
                        if (data.list[a].weeks == this.weeks) {
                            this.listresonate.push(data.list[a].count);
                            this.listtitle.push(data.list[a].title);
                        }
                    }
                    if (this.listresonate.length == 0 && this.checked == 'comment') {
                        this.$message.error('当前周无评论数据')
                    }
                    this.drawLine();
                })
            },
            getMonthsComment() {
                this.Status = 'months';
                this.listLoading = true;
                this.api({
                    url: "/statistics/listmonthscomment",
                    method: "get"
                }).then(data => {
                    this.listLoading = false;
                    this.month = this.months;
                    this.time = this.months;
                    for (var a = 0; a < data.list.length; a++) {
                        // // console.log(data.list)
                        if (data.list[a].weeks == this.months) {
                            this.listresonate.push(data.list[a].count);
                            this.listtitle.push(data.list[a].title);
                        }
                    }
                    if (this.listresonate.length == 0 && this.checked == 'comment') {
                        this.$message.error('当前月无评论数据')
                    }
                    this.drawLine();
                })
            },
            getWeeksCollection() {
                this.Status = 'weeks';
                this.listLoading = true;
                this.api({
                    url: "/statistics/listweekscollection",
                    method: "get",
                }).then(data => {
                    this.listLoading = false;
                    this.week = this.weeks;
                    this.time = this.weeks;
                    for (var a = 0; a < data.list.length; a++) {
                        if (data.list[a].weeks == this.weeks) {
                            this.listresonate.push(data.list[a].count);
                            this.listtitle.push(data.list[a].title);
                        }
                    }
                    if (this.listresonate.length == 0 && this.checked == 'collection') {
                        this.$message.error('当前周无收藏数据')
                    }
                    this.drawLine();
                })
            },
            getMonthsCollection() {
                this.Status = 'months';
                this.listLoading = true;
                this.api({
                    url: "/statistics/listmonthscollection",
                    method: "get",
                }).then(data => {
                    this.listLoading = false;
                    this.month = this.months;
                    this.time = this.months;
                    for (var a = 0; a < data.list.length; a++) {
                        if (data.list[a].weeks == this.months) {
                            this.listresonate.push(data.list[a].count);
                            this.listtitle.push(data.list[a].title);
                        }
                    }
                    if (this.listresonate.length == 0 && this.checked == 'collection') {
                        this.$message.error('当前月无收藏数据')
                    }
                    this.drawLine();
                })
            },
            getWeeksMaxMoney() {
                this.Status = 'weeks';
                this.listLoading = true;
                this.api({
                    url: "/statistics/listWeekMAXPriceCount",
                    method: "post",
                    data: {
                        Prices: this.Prices,
                    }
                }).then(data => {
                    this.listLoading = false;
                    this.week = this.weeks;
                    this.time = this.weeks;
                    for (var a = 0; a < data.maxlist.length; a++) {
                        if (data.maxlist[a].weeks == this.weeks) {
                            this.listmaxprice.push(data.maxlist[a].count);
                            this.listmaxprices.push(data.maxlist[a].money);
                        }
                    }
                    if (this.listmaxprice.length == 0 && this.checked == 'money') {
                        this.$message.error('当前周无最高价格数据')
                    }
                    this.drawMaxMoney();
                });
            },
            getWeeksMinMoney() {
                this.Status = 'weeks';
                this.listLoading = true;
                this.api({
                    url: "/statistics/listMonthMINPriceCount",
                    method: "post",
                    data: {
                        Prices: this.Prices,
                    }
                }).then(data => {
                    console.log(data);
                    this.listLoading = false;
                    this.week = this.weeks;
                    this.time = this.weeks;
                    for (var a = 0; a < data.minlist.length; a++) {
                        if (data.minlist[a].weeks == this.weeks) {
                            this.listminprice.push(data.minlist[a].count);
                            this.listminprices.push(data.minlist[a].money);
                        }
                    }
                    if (this.listminprice.length == 0 && this.checked == 'money') {
                        this.$message.error('当前周无最低价格数据')
                    }
                    this.drawMinMoney();
                })
            },
            getMonthMaxMoney() {
                this.Status = 'weeks';
                this.listLoading = true;
                this.api({
                    url: "/statistics/listMonthMAXPriceCount",
                    method: "post",
                    data: {
                        Prices: this.Prices,
                    }
                }).then(data => {
                    this.listLoading = false;
                    this.month = this.months;
                    this.time = this.months;
                    for (var a = 0; a < data.maxlist.length; a++) {
                        if (data.maxlist[a].weeks == this.months) {
                            this.listmaxprice.push(data.maxlist[a].count);
                            this.listmaxprices.push(data.maxlist[a].money);
                        }
                    }
                    if (this.listmaxprice.length == 0 && this.checked == 'money') {
                        this.$message.error('当前月无最高价格数据')
                    }
                    this.drawMaxMoney();
                });
            },
            getMonthMinMoney() {
                this.Status = 'weeks';
                this.listLoading = true;
                this.api({
                    url: "/statistics/listMonthMINPriceCount",
                    method: "post",
                    data: {
                        Prices: this.Prices,
                    }
                }).then(data => {
                    console.log(data);
                    this.listLoading = false;
                    this.month = this.months;
                    this.time = this.months;
                    for (var a = 0; a < data.minlist.length; a++) {
                        if (data.minlist[a].weeks == this.months) {
                            this.listminprice.push(data.minlist[a].count);
                            this.listminprices.push(data.minlist[a].money);
                        }
                    }
                    if (this.listminprice.length == 0 && this.checked == 'money') {
                        this.$message.error('当前月无最低价格数据')
                    }
                    this.drawMinMoney();
                })
            },
            drawLine() {
                if (this.checked == 'good') {
                    var myChart = this.$echarts.init(document.getElementById('goodCart'))
                } else if (this.checked == 'comment') {
                    var myChart = this.$echarts.init(document.getElementById('commentCart'))
                } else if (this.checked == 'collection') {
                    var myChart = this.$echarts.init(document.getElementById('collectionCart'))
                }
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
            },
            drawMaxMoney() {
                if (this.checked == 'money') {
                    var myChart = this.$echarts.init(document.getElementById('maxmoneyCart'))
                }
                myChart.setOption({
                    color: ['#37a2da', '#32c5e9', '#67e0e3', '#9fe6b8', '#ffdb5c', '#749f83', '#ca8622', '#bda29a', '#6e7074', '#546570', '#c4ccd3'],
                    title: {
                        text: this.time + this.Title[this.checked] + '最高价格排名',
                        subtext: '数据来自益达的数据库'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        data: ['最高价格数量']
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis: {
                        type: 'category',
                        boundaryGap: false,
                        data: this.listmaxprices
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: this.loadline(this.listmaxprice)
                }, true);
            },
            drawMinMoney() {
                if (this.checked == 'money') {
                    var myChart = this.$echarts.init(document.getElementById('minmoneyCart'))
                }
                myChart.setOption({
                    color: ['#37a2da', '#32c5e9', '#67e0e3', '#9fe6b8', '#ffdb5c', '#749f83', '#ca8622', '#bda29a', '#6e7074', '#546570', '#c4ccd3'],
                    title: {
                        text: this.time + this.Title[this.checked] + '最低价格排名',
                        subtext: '数据来自益达的数据库'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        data: ['最低价格数量']
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis: {
                        type: 'category',
                        boundaryGap: false,
                        data: this.listminprices
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: this.loadline(this.listminprice)
                }, true);
            },
            loadline(data) {
                var serie = [];
                var item = {
                    name: '最高价格数量',
                    type: 'line',
                    data: data
                };
                serie.push(item);
                return serie;
            },
        }
    }
</script>

<style scoped>
  .resonateCart {
    margin: 40px auto;
    box-shadow: 0 0 10px #6c8286;
  }

  .top {
    margin-left: 30%;
    margin-top: 20px;
  }
</style>
