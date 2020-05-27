# encoding=utf-8
from flask import Flask, request
import logging
from sklearn.feature_extraction.text import TfidfTransformer
from sklearn.feature_extraction.text import CountVectorizer
import jieba
from jieba import posseg
import re


def remove_punctuation(s):
    """
    文本标准化：仅保留中文字符、英文字符和数字字符
    :param s: 输入文本（中文文本要求进行分词处理）
    :return: s_：标准化的文本
    """
    regex = re.compile(u'[^\u4E00-\u9FA5]')
    s_ = regex.sub('', s)
    return s_

def get_stop_word():
    stop = []
    with open('stopWord.txt',encoding='utf-8') as f:
        for line in f.readlines():
            stop.append(line[:-1])
    return stop

def getCor(new_sentence):

    stop = get_stop_word()
    res = []

    new = remove_punctuation(new_sentence)
    stringword = ''
    for word in jieba.cut(new):
        if word not in stop:
            stringword = stringword + ' ' + word
    res.append(stringword)

    with open('cor.txt', encoding='utf-8') as f:
        for single in f.readlines():

            num, sen = single.split('\t')
            sen = remove_punctuation(sen)
            stringword = ''
            for word in jieba.cut(sen):
                if word not in stop:
                    stringword = stringword+' '+word
            res.append(stringword)
    return res


def get_tfidf_socre(new):
    corpus = getCor(new)
    vectorizer = CountVectorizer()
    transformer = TfidfTransformer()

    # 第一个fit_transform是计算tf-idf 第二个fit_transform是将文本转为词频矩阵
    tfidf = transformer.fit_transform(vectorizer.fit_transform(corpus))

    # 获取词袋模型中的所有词语
    word = vectorizer.get_feature_names()
    weight = tfidf.toarray()

    dict_one={}
    for i in range(1,5):
        if (weight[0][weight[0].argsort()[-i]]) > 0:
            dict_one[word[weight[0].argsort()[-i]]] = weight[0][weight[0].argsort()[-i]]

    return dict_one

def get_pos_score(word,ori):
    w = posseg.cut(word)
    pos = ''
    score = 1
    for i in w:
        pos = i.flag
        if pos[0] == 'n':
            score = 2
        if pos[0] == 'v':
            score = 1.5
        if pos[0] == 'a':
            score = 1.2
    return score*ori

def get_common_word():
    common = []
    with open('symptom.txt',encoding='utf-8') as f:
        for line in f.readlines():
            common.append(line[:-1])
    with open('plant.txt',encoding='utf-8') as f:
        for line in f.readlines():
            common.append(line[:-1])
    with open('disease.txt',encoding='utf-8') as f:
        for line in f.readlines():
            common.append(line[:-1])
    common.remove('李')
    return common

def predict(text):
    tot = get_tfidf_socre(text)
    common = get_common_word()
    tot2 = text

    tem_dic = {}
    for word in tot:
        tem_dic[word] = get_pos_score(word, tot[word])
    for i in common:
        if i in tot2:
            tem_dic[i] = 5
    return tem_dic

def create_app():
    app = Flask(__name__)
    @app.route('/', methods=['GET', 'POST'])
    def callback():
        s = request.args.get("s") or "EOF"
        app.logger.warning('the sentence is %s', s)
        ans = predict(s)
        app.logger.warning('the answer is %s', ans)
        return str(ans)
    return app

app = create_app()

'''
if __name__ == '__main__':
    handler = logging.FileHandler('flask.log')
    app.logger.addHandler(handler)
    app.run(host='127.0.0.1', port=5786)


if __name__ == '__main__':
    while True:
        print(predict(input()))
'''

if __name__=="__main__":
    import pymysql
    coon = pymysql.connect(
            host = '10.15.82.50',user = 'root',passwd = '123',
            port = 3306,db = 'hjk_qa',charset = 'utf8'
            #port必须写int类型
            #charset必须写utf8，不能写utf-8
    )
    print(1)
    cur = coon.cursor() #建立游标
    cur.execute("select id,question,body from import_qa")  #查询数据
    res = cur.fetchall() #获取结果

    f=open('../tag_result.txt', 'a',encoding='utf-8')
    print(2)
    for single in res:
        ans = predict(single[1]+single[2])
        f.write(str(single[0])+'\t'+str(ans)+'\n')
    
    f.close()
    cur.close()     #关闭游标
    coon.close()    #关闭连接
