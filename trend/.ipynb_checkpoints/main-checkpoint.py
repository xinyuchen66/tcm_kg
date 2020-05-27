ii=0
gongxiao={}
dongwu={}
huahewu={}
fangji={}
zhiwu={}
jibin={}
zhenzhuang={}
kuangwu={}
xuewei={}
with open('功效.txt', encoding='utf-8') as f:
    for line in f.readlines():
        tem = line[:-1]
        gongxiao[tem] = 0
with open('动物.txt', encoding='utf-8') as f:
    for line in f.readlines():
        tem = line[:-1]
        dongwu[tem] = 0
with open('化合物.txt', encoding='utf-8') as f:
    for line in f.readlines():
        tem = line[:-1]
        huahewu[tem] = 0
with open('方剂.txt', encoding='utf-8') as f:
    for line in f.readlines():
        tem = line[:-1]
        fangji[tem] = 0
with open('植物.txt', encoding='utf-8') as f:
    for line in f.readlines():
        tem = line[:-1]
        zhiwu[tem] = 0
with open('疾病.txt', encoding='utf-8') as f:
    for line in f.readlines():
        tem = line[:-1]
        jibin[tem] = 0
with open('症状.txt', encoding='utf-8') as f:
    for line in f.readlines():
        tem = line[:-1]
        zhenzhuang[tem] = 0
with open('矿物.txt', encoding='utf-8') as f:
    for line in f.readlines():
        tem = line[:-1]
        kuangwu[tem] = 0
with open('穴位.txt', encoding='utf-8') as f:
    for line in f.readlines():
        tem = line[:-1]
        xuewei[tem] = 0
        


ff = open("res.txt",'a',encoding='utf-8')
with open('all.txt',encoding='utf-8') as f:
    for line in f.readlines():
        ii+=1
        if(ii%1000==0):
            print(ii)
        if ii==500000:
            break
        a = line.split('\t')
        id, question = a[0],a[1]
        for i in gongxiao:
            if i in question:
                gongxiao[i]+=1
        for i in dongwu:
            if i in question:
                dongwu[i]+=1
        for i in huahewu:
            if i in question:
                huahewu[i]+=1
        for i in fangji:
            if i in question:
                fangji[i]+=1
        for i in zhiwu:
            if i in question:
                zhiwu[i]+=1
        for i in jibin:
            if i in question:
                jibin[i]+=1
        for i in zhenzhuang:
            if i in question:
                zhenzhuang[i]+=1
        for i in kuangwu:
            if i in question:
                kuangwu[i]+=1
        for i in xuewei:
            if i in question:
                xuewei[i]+=1
ff.write(str(gongxiao)+'\n')
ff.write(str(dongwu)+'\n')
ff.write(str(huahewu)+'\n')
ff.write(str(fangji)+'\n')
ff.write(str(zhiwu)+'\n')
ff.write(str(jibin)+'\n')
ff.write(str(zhenzhuang)+'\n')
ff.write(str(kuangwu)+'\n')
ff.write(str(xuewei)+'\n')
ff.close()
