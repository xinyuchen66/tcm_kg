import pymysql
coon = pymysql.connect(
    host = '10.15.82.50',user = 'root',passwd = '123',
    port = 3306,db = 'lsc_qa',charset = 'utf8'
)
cur = coon.cursor()

def findqa(id):
    cur.execute("select question, answer from import_qa where id = "+id)
    res = cur.fetchall() 
    q = res[0][0]
    a = res[0][1]
    return q, a

def put(qa_id,qa_q,qa_a,qa_class,qa_tag):
    query = "INSERT INTO qa_new (qa_id,qa_q,qa_a,qa_class,qa_tag,q_source,a_source,q_author,a_author,support,unsupport,updateTime) " \
        "VALUES(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
    arg = (qa_id,qa_q,qa_a,qa_class,qa_tag,"网络资源","网络资源","网络资源","网络资源",0,0,"2019-10-24 12:00:00")
    cur.execute(query, arg)

with open('res.txt', encoding='utf-8') as f:
    id = 4000000
    for line in f.readlines():
        try:
            oldid, clazz, tag = line[:-1].split('\t')
            q, a = findqa(oldid)
            put(id,q,a,clazz,tag)
            id += 1
        except:
            pass

coon.commit()
cur.close()
coon.close()