import os
from keras import models
from position_embedding import PositionEmbedding #自定义层：Position Embedding
from attention import Attention #自定义层：Attention
from keras_layer_normalization import LayerNormalization
from keras.preprocessing import sequence
from keras.preprocessing.text import Tokenizer
from keras.preprocessing.sequence import pad_sequences
import jieba
import re
from tqdm import tqdm
from keras.utils import to_categorical

os.environ['CUDA_VISIBLE_DEVICES'] = '0'

transformer = models.load_model('trans.h5',custom_objects={'Attention':Attention,'LayerNormalization':LayerNormalization,'PositionEmbedding': PositionEmbedding})

regex = re.compile(u'[^\u4E00-\u9FA5|0-9a-zA-Z]')
def remove_punctuation(s):
    """
    文本标准化：仅保留中文字符、英文字符和数字字符
    :param s: 输入文本（中文文本要求进行分词处理）
    :return: s_：标准化的文本
    """
    s_ = regex.sub('', s)
    return s_

def text_tokenizer(texts):
    """
    文本分词+标准化
    :param texts: 输入文本list类型：[text_1,text_2,...,text_i,...]
    :return: 标准化后的分词文本
    """
    return [jieba.lcut(remove_punctuation(text)) for text in texts]

train=[]
with open('train.txt', encoding='utf-8') as f:
    for line in f.readlines():
        try:
            a,b = line.split('\t')
            train.append([a,b])
        except:
            pass
tqdm_train = tqdm(train)
tqdm_train.set_description("Train:")
train_label = []
train_data = []
try:
    for item in tqdm_train:
        data = item[0]
        label = item[1][:-1]
        train_label.append(label)
        train_data.append(text_tokenizer([data])[0])
except KeyboardInterrupt:
    tqdm.close()
    raise
    
label_set = []
for label in train_label:
    if label not in label_set:
        label_set.append(label)

label_convert = dict([[item,label_set.index(item)] for item in label_set])
#print(label_convert)
label_reconvert = dict([(label_convert[key],key) for key in label_convert])
#print(label_reconvert)

train_label = [label_convert[item] for item in train_label]
train_label = to_categorical(train_label)

num_words = 10000
tokenizer = Tokenizer(num_words=num_words)
tokenizer.fit_on_texts(train_data)

def trans_predict(new):
    new = text_tokenizer([new])
    new = tokenizer.texts_to_sequences(new)
    new = pad_sequences(new, maxlen=25)
    return label_reconvert[transformer.predict(new).argmax()]
    

if __name__=="__main__":
    import pymysql
    coon = pymysql.connect(
            host = '10.15.82.50',user = 'root',passwd = '123',
            port = 3306,db = 'hjk_qa',charset = 'utf8'
            #port必须写int类型
            #charset必须写utf8，不能写utf-8
    )

    cur = coon.cursor() #建立游标
    cur.execute("select id,question,body,dis_cate1 from import_qa")  #查询数据
    res = cur.fetchall() #获取结果

    f=open('../classifer_result.txt', 'a',encoding='utf-8')
    bias=open('../error.txt', 'a',encoding='utf-8')

    for single in res:
        ans = trans_predict(single[1]+single[2])
        f.write(str(single[0])+'\t'+ans+'\n')
        if ans!=single[3]:
            bias.write(str(single[0])+'\n')
    
    f.close()
    bias.close()
    cur.close()     #关闭游标
    coon.close()    #关闭连接
