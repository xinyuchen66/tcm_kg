'''
进入spark/sbin sh start-all.sh
进入spark/bin sh spark-submit --master local test.py
'''
#文件路径
path = "file:////Users/sicongli/spark-2.4.5-bin-hadoop2.6/bin/tcm_conclusion/input.txt"
#统计个数
count = 10000

from operator import add
from pyspark import SparkContext
import jieba
import pymysql
import time

stop = [line.strip() for line in open ("./tcm_conclusion/stopword.txt").readlines()]
gongxiao = [line.strip() for line in open ("./tcm_conclusion/功效.txt").readlines()]
dongwu = [line.strip() for line in open ("./tcm_conclusion/动物.txt").readlines()]
huahewu = [line.strip() for line in open ("./tcm_conclusion/化合物.txt").readlines()]
fangji = [line.strip() for line in open ("./tcm_conclusion/方剂.txt").readlines()]
zhiwu = [line.strip() for line in open ("./tcm_conclusion/植物.txt").readlines()]
jibin = [line.strip() for line in open ("./tcm_conclusion/疾病.txt").readlines()]
zhenzhuang = [line.strip() for line in open ("./tcm_conclusion/症状.txt").readlines()]
kuangwu = [line.strip() for line in open ("./tcm_conclusion/矿物.txt").readlines()]
xuewei = [line.strip() for line in open ("./tcm_conclusion/穴位.txt").readlines()]

def word_count():
    sc = SparkContext(appName="wordcount")
    textFile= sc.textFile(path)
    result = textFile.flatMap(lambda x: [i for i in list(jieba.cut(x)) if i not in stop]) \
        .map(lambda x: (x, 1)) \
        .reduceByKey(add) \
        .sortBy(lambda x: x[1], False).take(count)
    with open("./tcm_conclusion", "w") as f:
    	for k, v in result:
    		f.write(str(k)+" "+str(v)+"\n")


def getQA():
	coon = pymysql.connect(
    host = '10.15.82.58',user = 'root',passwd = '123',
    port = 3306,db = 'qa',charset = 'utf8'
    )
	source = coon.cursor()
	source.execute("select qa_q,qa_a from qa_new")
	tem = source.fetchall()
	source.close()
	coon.close()
	with open("./input.txt", "w") as f:
		for single in tem:
			f.write(single[0]+" "+single[1]+"\n")

if __name__ == '__main__':
	while True:
		getQA()
	    word_count()
	    time.sleep(60*60*30)