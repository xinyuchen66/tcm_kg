//echarts4.0 文档地址
//https://www.echartsjs.com/zh/option.html#series-graph.lineStyle.color
if (!window.console) {
    //兼容
    window.console = {
        error: function () {

        },
        log: function () {

        },
        table: function () {

        }
    }
}

window.ec = echarts;
initApp();
var keyCahce = {};
var keywordSearchFlag = 0;
nodeColor = {'Plant':'#00ff00',
    'Disease':'#ff0000',
    'Drug':'#0000ff'}

function getData(key) {
    return new Promise(function (resolve, reject) {
        axios.get('/tcm/qaos/topics/ask/getKGmain?entity=' + key).then(res => {
            if(key == window.app.input && keywordSearchFlag == 0) {
            keywordSearchFlag = 1;
        }
    else if(key == window.app.input && keywordSearchFlag == 1) {
            console.log('node has been clicked!');
            return;
        }
        if (res.data.message != 'success' || res.data.data.hasOwnProperty("avp") == false) {
            reject(res.data);
            app.noData = true;
        } else {
            app.noData = false;
            //移除关键词本身
            let temps = [];
            res.data.data.avp.forEach(item => {
                let label = item[1];
            if (label != key && !keyCahce[label]) {
                temps.push(item);
                // keyCahce[label] = "";
            }
        });
            res.data.data.avp = temps;
            resolve(res.data.data);

        }

    }).catch(err => {
            console.error(err);
        alert('api error！');
    })
    });
}

function initApp() {
    //初始化vue
    window.app = new Vue({
        el: '#app',
        data: {
            input: '',
            noData: false
        },
        created: function () {

            var word = getQueryVariable('word')
            if (word) {
                word = decodeURI(word);
                this.search(word)
            } else {
                word = '丁香';
            }

            this.input = word;

            this.$nextTick(function () {
                this.search(this.input);
            });

        },
        methods: {
            enter: function () {
                if(document.getElementById("choose_type").value == "疾病") {
                    enter_disease();
                    return;
                }
                // this.search();
                if (this.input != '') {
                    window.location.href = './profile?word=' + document.getElementById("inputWord").value;
                }
            },
            search: function () {
                if (this.input == "") {
                    return;
                }
                global_index = 1;
                keyCahce = {};
                getData(this.input).then(res => {

                    handlerData(res).then(data => {
                        data.nodes.unshift({
                            id: 0,
                            category: 0,
                            name: res.entity,
                            symbolSize: 10,
                            itemStyle: {
                                color: '#ffff00'
                            }
                        });
                initEc(data);
            })

            });

            }
        }
    })
}

function initEc(data) {

    var myChart = ec.init(document.getElementById('main'), 'macarons');
    window.myChart = myChart;
    var option = {
        tooltip: {
            show: false
        },
        series: [{
            itemStyle: {
                normal: {
                    label: {
                        position: 'top',
                        show: true,
                        textStyle: {
                            fontSize: 18,
                            color: '#000'
                        }
                    },
                    nodeStyle: {
                        brushType: 'both',
                        borderColor: 'rgba(255,215,0,0.4)',
                        borderWidth: 1
                    },
                    linkStyle: {
                        color: 'source',
                        curveness: 0,
                        type: "solid"
                    }
                },

            },
            force: {
                initLayout: 'force',//初始布局
                edgeLength: 20,
                repulsion: 20,
                gravity: 0.2,
                layoutAnimation: false
            },
            edgeLabel: {
                show: true,
                color: "#2d2d2d",
                formatter: function (param) {
                    return param.data.lineLabel
                }
            },
            //形状
            // symbol:'arrow',
            name: 'Les Miserables',
            type: 'graph',
            layout: 'force',//取值none,circular,force
            roam: true,//可以拖动
            draggable: true,
            useWorker: true,
            minRadius: 15,
            maxRadius: 25,
            // gravity: 0.1,
            // scaling: 3.1,
            zoom: 7,
            nodes: data.nodes,
            links: data.links
        }]
    };
    myChart.setOption(option);

    function openOrFold(param) {
        if(typeof param.data.name != 'string') {
            return getQAlist(param.data.name);
        };
        if(document.getElementById("choose_type").value == "疾病") return;
        var option = myChart.getOption();
        var nodes = option.series[0].nodes;
        var links = option.series[0].links;

        var data = param.data;

        //如果节点点击过了，就不能再次点击了
        if (!data.flag) {
            for (var i = 0; i < nodes.length; i++) {
                if (data.id == nodes[i].id) {
                    option.series[0].nodes[i].flag = true;
                }
            }
        } else {
            console.log('node has been clicked!');
            return;
        }

        // myChart.setOption(option);

        //从网络取下一个节点，然后追加到nodes中
        getData(data.name).then(res => {
            //追加节点到nodes
            handlerData(res, data.id).then(dd => {

            //遍历，如果nodes存在了，就不追加
            nodes = nodes.concat(dd.nodes);
        links = links.concat(dd.links);
        option.series[0].nodes = nodes;
        option.series[0].links = links;

        // console.log(option.series[0].nodes)
        myChart.clear();
        myChart.setOption(option);
    });
    });

    }

    //myChart.on('click', openOrFold);
    myChart.on('dblclick', openOrFold);
}

var global_index = 1;

function handlerData(res, targetId) {
    if (!targetId) {
        targetId = 0;
    }

    return new Promise(resolve => {
        let links = [];
    let nodes = [];

    res.avp.forEach((item, index) => {
        let id = global_index++;
    nodes.push({
        id: id,
        name: item[1],
        value: item[1],
        symbolSize: 5,
        itemStyle: {
            color: nodeColor[item[2]]
        }
    });
    links.push({
        source: id,
        target: targetId,
        name: item[3],
        lineLabel: item[0]
    });
});
    resolve({
        nodes: nodes,
        links: links
    });
});
}

function randomRgbColor() { //随机生成RGB颜色
    var r = Math.floor(Math.random() * 256); //随机生成256以内r值
    var g = Math.floor(Math.random() * 256); //随机生成256以内g值
    var b = Math.floor(Math.random() * 256); //随机生成256以内b值
    return `rgb(${r},${g},${b})`; //返回rgb(r,g,b)格式颜色
}


function enter_disease(){
    key = document.getElementById("inputWord").value;
    if(key == "") key = "慢性骨髓炎";
    axios.get('/tcm/qaos/topics/ask/getKGdisease?entity=' + key).then(res => {
        initEc2(res.data);
});
}

function initEc2(data) {
    var global_index = 1;
    nodes=[]
    links=[]

    nodes.push({
        id: 0,
        category: 0,
        name: data['entity'],
        symbolSize: 12,
        itemStyle: {
            color: '#ffff00'
        }
    });

//     data.single.forEach((item) => {
//         let id = global_index++;
//     nodes.push({
//         id: id,
//         name: item[0],
//         value: item[0],
//         symbolSize: 5,
//         itemStyle: {
//             color: randomRgbColor()
//         }
//     });
//     links.push({
//         source: id,
//         target: 0,
//         lineLabel: "方案"+id,
//     });
// });

    for(i = 0; i<data.single.length; i++) {
        let id = global_index++;
        nodes.push({
            id: id,
            name: data.single[i][0],
            value: data.single[i][0],
            symbolSize: 5,
            itemStyle: {
                color: randomRgbColor()
            }
        });
        links.push({
            source: id,
            target: 0,
            name: data.singleid[i],
            lineLabel: "方案"+id,
        });
    }

//     data.multiple.forEach((item) => {
//         var tot = "";
//     for(i=0; i<item.length-1; i++){
//         tot+=item[i]+'、';
//         if((i+1)%3 == 0) tot+='\n';
//     }
//     tot+=item[item.length-1];

//     let id = global_index++;
//     nodes.push({
//         id: id,
//         name: tot,
//         value: tot,
//         symbolSize: 10,
//         itemStyle: {
//             color: randomRgbColor()
//         }
//     });
//     links.push({
//         source: id,
//         target: 0,
//         lineLabel: "方案"+id,
//     });
// });

    for(i = 0; i<data.multiple.length; i++) {
        var item = data.multiple[i];
        var tot = "";
        for(j=0; j<item.length-1; j++){
            tot+=item[j]+'、';
            if((j+1)%3 == 0) tot+='\n';
        }
        tot+=item[item.length-1];
        let id = global_index++;
        nodes.push({
            id: id,
            name: tot,
            value: tot,
            symbolSize: 10,
            itemStyle: {
                color: randomRgbColor()
            }
        });
        links.push({
            source: id,
            target: 0,
            name: data.multipleid[i],
            lineLabel: "方案"+id,
        });
    }

    data.single_drug.forEach((item)=>{
        sourceId = item[0]+1;
    score = item[1];
    drugId = item[2].split('-')[0];
    drugName = item[2].split('-')[1];
    let id = global_index++;
    nodes.push({
        id: id,
        name: drugName,
        value: drugName,
        symbolSize: 6,
        itemStyle: {
            color: '#F00000'
        }
    });
    links.push({
        source: sourceId,
        target: id,
        value: drugId,
        name: drugName,
        lineLabel: "方剂相似度："+parseInt(score*100)+'%',
        lineStyle:{type:'dotted',color:'#001'}
    });
});

    data.multiple_drug.forEach((item)=>{
        sourceId = item[0]+1+data.single.length;
    score = item[1];
    drugId = item[2].split('-')[0];
    drugName = item[2].split('-')[1];
    let id = global_index++;
    nodes.push({
        id: id,
        name: drugName,
        value: drugName,
        symbolSize: 6,
        itemStyle: {
            color: '#F00000'
        }
    });
    links.push({
        source: sourceId,
        target: id,
        value: drugId,
        name: drugName,
        lineLabel: "方剂相似度："+parseInt(score*100)+'%',
        lineStyle:{type:'dotted',color:'#001'}
    });
});

    var myChart = ec.init(document.getElementById('main'), 'macarons');
    window.myChart = myChart;
    var option = {
        tooltip: {
            show: false
        },
        series: [{
            itemStyle: {
                normal: {
                    label: {
                        position: 'top',
                        show: true,
                        textStyle: {
                            fontSize: 18,
                            color: '#000'
                        }
                    },
                    nodeStyle: {
                        brushType: 'both',
                        borderColor: 'rgba(255,215,0,0.4)',
                        borderWidth: 1
                    },
                    linkStyle: {
                        color: 'source',
                        curveness: 0,
                        type: "solid"
                    }
                },

            },
            force: {
                initLayout: 'force',//初始布局
                edgeLength: 20,
                repulsion: 20,
                gravity: 0.2,
                layoutAnimation: false
            },
            edgeLabel: {
                show: true,
                formatter: function (param) {
                    return param.data.lineLabel
                }
            },
            //形状
            // symbol:'arrow',
            name: 'Les Miserables',
            type: 'graph',
            layout: 'force',//取值none,circular,force
            roam: true,//可以拖动
            draggable: true,
            useWorker: true,
            minRadius: 15,
            maxRadius: 25,
            // gravity: 0.1,
            // scaling: 3.1,
            zoom: 7,
            nodes:nodes,
            links:links
        }]
    };
    myChart.setOption(option);
    myChart.on('dblclick', getDrugLocation);
}

function getDrugLocation(link) {
    if(Number.isInteger(parseInt(link.data.value))) {
        //window.location.href = '../search/pre?prename=' + link.data.value + '&preid=' + link.data.value;
        let newhref = '../search/pre?prename=' + link.data.value + '&preid=' + link.data.value;
        window.open(newhref);
    }
}


function getQueryVariable(variable) {
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split("=");
        if (pair[0] == variable) {
            return pair[1];
        }
    }
    return (false);
}

function getQAlist(list) {
    $.ajax({
        url:'/tcm/qaos/topics/ask/getKGlist?id='+list,
        dataType: "json",
        success: function(data){
            document.getElementById("qaname").style.visibility="visible";
            $("#qaList").html('');
            let res = '';
            for(var key in data){
                res+='<li><a target="_blank" class="content-item ellipsis" href="topics/ask/qadetail?qaid='+key+'">'+data[key]+'</a></li>';
            }
            $("#qaList").html(res);
            document.querySelector("#qaTitle").scrollIntoView();
        }
    });
}