import pymysql
coon = pymysql.connect(
    host = '10.15.82.50',user = 'root',passwd = '123',
    port = 3306,db = 'tcm',charset = 'utf8'
    #port必须写int类型
    #charset必须写utf8，不能写utf-8
)

cur = coon.cursor() #建立游标
cur.execute("select topic_title,topic_body from qa_topic")  #查询数据
res = cur.fetchall() #获取结果

ff=open('../network_res.txt','a',encoding='utf-8')

disease = set()
plant = set()
drug = set()

with open('disease.txt', encoding='utf-8') as f:
    for line in f.readlines():
        disease.add(line[:-1])
with open('plant.txt', encoding='utf-8') as f:
    for line in f.readlines():
        plant.add(line[:-1])
with open('drug.txt', encoding='utf-8') as f:
    for line in f.readlines():
        drug.add(line[:-1])


def extract(sentence):
    cur_disease = []
    cur_plant = []
    cur_drug = []

    for i in disease:
        if i in sentence:
            cur_disease.append(i)
    for i in plant:
        if i in sentence:
            cur_plant.append(i)
    for i in drug:
        if i in sentence:
            cur_drug.append(i)
    cur_drug.remove('')
    cur_disease.remove('')
    cur_plant.remove('')

    for i in cur_disease:
        for j in cur_plant:
            ff.write(j+'\t治疗\t'+i+'\n')
    for i in cur_disease:
        for j in cur_drug:
            ff.write(j+'\t治疗\t'+i+'\n')
    for i in cur_plant:
        for j in cur_drug:
            ff.write(i+'\t组成\t'+j+'\n')
    for i in range(len(cur_plant)):
        for j in cur_plant[i+1:]:
            ff.write(cur_plant[i]+'\t伴随\t'+j+'\n')

for single in res:
    sen = single[0] + single[1]
    extract(sen)
    
ff.close()
cur.close()     #关闭游标
coon.close()    #关闭连接
