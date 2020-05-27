gongxiao=[]
dongwu=[]
huahewu=[]
fangji=[]
zhiwu=[]
jibin=[]
zhenzhuang=[]
kuangwu=[]
xuewei=[]
with open('功效.txt', encoding='utf-8') as f:
    for line in f.readlines():
        gongxiao.append(line[:-1])
with open('动物.txt', encoding='utf-8') as f:
    for line in f.readlines():
        dongwu.append(line[:-1])
with open('化合物.txt', encoding='utf-8') as f:
    for line in f.readlines():
        huahewu.append(line[:-1])
with open('方剂.txt', encoding='utf-8') as f:
    for line in f.readlines():
        fangji.append(line[:-1])
with open('植物.txt', encoding='utf-8') as f:
    for line in f.readlines():
        zhiwu.append(line[:-1])
with open('疾病.txt', encoding='utf-8') as f:
    for line in f.readlines():
        jibin.append(line[:-1])
with open('症状.txt', encoding='utf-8') as f:
    for line in f.readlines():
        zhenzhuang.append(line[:-1])
with open('矿物.txt', encoding='utf-8') as f:
    for line in f.readlines():
        kuangwu.append(line[:-1])
with open('穴位.txt', encoding='utf-8') as f:
    for line in f.readlines():
        xuewei.append(line[:-1])
        
def extract(question):
    clazz = []
    tag = []
    flag = 0
    for single in gongxiao:
        if single in question:
            if flag == 0:
                flag = 1
                clazz.append('功效')
                tag.append('功效')
            tag.append(single)
    flag = 0
    for single in dongwu:
        if single in question:
            if flag == 0:
                flag = 1
                clazz.append('动物')
                tag.append('动物')
            tag.append(single)
    flag = 0
    for single in huahewu:
        if single in question:
            if flag == 0:
                flag = 1
                clazz.append('化合物')
                tag.append('化合物')
            tag.append(single)
    flag = 0
    for single in fangji:
        if single in question:
            if flag == 0:
                flag = 1
                clazz.append('方剂')
                tag.append('方剂')
            tag.append(single)
    flag = 0
    for single in zhiwu:
        if single in question:
            if flag == 0:
                flag = 1
                clazz.append('植物')
                tag.append('植物')
            tag.append(single)
    flag = 0
    for single in jibin:
        if single in question:
            if flag == 0:
                flag = 1
                clazz.append('疾病')
                tag.append('疾病')
            tag.append(single)
    flag = 0
    for single in zhenzhuang:
        if single in question:
            if flag == 0:
                flag = 1
                clazz.append('症状')
                tag.append('症状')
            tag.append(single)
    flag = 0
    for single in kuangwu:
        if single in question:
            if flag == 0:
                flag = 1
                clazz.append('矿物')
                tag.append('矿物')
            tag.append(single)
    flag = 0
    for single in xuewei:
        if single in question:
            if flag == 0:
                flag = 1
                clazz.append('穴位')
                tag.append('穴位')
            tag.append(single)
    return list(set(clazz)), list(set(tag))

ff = open("res.txt",'a',encoding='utf-8')
with open('all.txt',encoding='utf-8') as f:
    for line in f.readlines():
        a=line.split('\t')
        id, question = a[0],a[1]
        c,t = extract(question)
        qwq = id + '\t'+ '#'.join(c) + '\t'+ '#'.join(t) + '\n'
        if len(c) > 0:
            ff.write(qwq)
ff.close()
