import pymysql

drug_plant_relation = []
tot_disease = []

with open("drug_plant_relation.txt",encoding='utf-8') as f:
    for line in f.readlines():
        a,b,c = line.split('\t')
        tem = []
        tem.append(a) #药方
        tem.append(b) #药材组成
        tem.append(c) #病
        drug_plant_relation.append(tem)
        
with open("tot_disease.txt", encoding='utf-8') as f:
    for line in f.readlines():
        tot_disease.append(line[:-1])

def bansui(a, b):
    res = []
    for single in drug_plant_relation:
        if a in single[1] and b in single[1]:
            res.append(single[0])
    return res

def zucheng(drug, plant):
    res = []
    for single in drug_plant_relation:
        if drug in single[0] and plant in single[1]:
            res.append(single[0])
            break
    return res

def zhiliao(drug, disease):
    res = []
    all_disease = [disease]
    for line in tot_disease:
        if disease in line:
            all_disease.append(line.split())


    for single in drug_plant_relation:
        for single_disease in all_disease:
            if (drug in single[0] or drug in single[1]) and single_disease in single[2]:
                res.append(single[0])
    return res



def find_plant(name):
    coon = pymysql.connect(
    host = '10.15.82.50',user = 'root',passwd = '123',
    port = 3306,db = 'tcm',charset = 'utf8'
    #port必须写int类型
    #charset必须写utf8，不能写utf-8
    )
    ret = None
    cur = coon.cursor() #建立游标
    cur.execute("select med_id from med_basic where med_name_zh='"+name+"'")  #查询数据
    res = cur.fetchall() #获取结果
    
    for single in res:
        ret = single[0]
    cur.close()     #关闭游标
    coon.close()    #关闭连接
    return ret

def find_drug(name):
    coon = pymysql.connect(
    host = '10.15.82.50',user = 'root',passwd = '123',
    port = 3306,db = 'tcm',charset = 'utf8'
    #port必须写int类型
    #charset必须写utf8，不能写utf-8
    )
    ret = None
    cur = coon.cursor() #建立游标
    cur.execute("select prescription_id from prescription where pre_name_zh='"+name+"'")  #查询数据
    res = cur.fetchall() #获取结果
    
    for single in res:
        ret = single[0]
    cur.close()     #关闭游标
    coon.close()    #关闭连接
    return ret

def find_disease(name):
    ret = None
    return ret



def printqwq(qwq):
    f = open('../network2sql.txt','a',encoding='utf-8')
    f.write(qwq+'\n')
    f.close()

with open('input.txt', encoding='utf-8') as f:
    for line in f.readlines():
        try:
            entity1, rela, entity2 = line.split()
            if rela=='伴随':
                en1_num = find_plant(entity1)
                en2_num = find_plant(entity2)
                if en1_num != None and en2_num != None:
                    kb = bansui(entity1, entity2)
                    try:
                        drug_num = find_drug(kb[0])
                    except:
                        pass
                    if len(kb) == 0 or drug_num is None:
                        tem = [entity1,rela,entity2,'search/med?medname='+entity1+'&medid='+str(en1_num),'search/med?medname='+entity2+'&medid='+str(en2_num),'null']
                        qwq = '\t'.join(tem)
                    else:
                        kb_link = 'search/predetail?prename='+kb[0]+'&preid='+str(drug_num)+'&source=all#composition'
                        tem = [entity1,rela,entity2,'search/med?medname='+entity1+'&medid='+str(en1_num),'search/med?medname='+entity2+'&medid='+str(en2_num),kb_link]
                        qwq = '\t'.join(tem)
                    printqwq(qwq)
                    
            elif rela=='组成':
                en1_num = find_plant(entity1)
                en2_num = find_drug(entity2)
                if en1_num != None and en2_num != None:
                    kb = zucheng(entity2, entity1)
                    try:
                        drug_num = find_drug(kb[0])
                    except:
                        pass
                    if len(kb) == 0 or drug_num is None:
                        tem = [entity1,rela,entity2,'search/med?medname='+entity1+'&medid='+str(en1_num),'search/pre?prename='+entity2+'&preid='+str(en2_num),'null']
                        qwq = '\t'.join(tem)
                    else:
                        kb_link = 'search/predetail?prename='+kb[0]+'&preid='+str(drug_num)+'&source=all#composition'
                        tem = [entity1,rela,entity2,'search/med?medname='+entity1+'&medid='+str(en1_num),'search/pre?prename='+entity2+'&preid='+str(en2_num),kb_link]
                        qwq = '\t'.join(tem)
                    printqwq(qwq)
                
            elif rela=='治疗':
                drug_flag = 0
                en1_num = find_plant(entity1)
                if en1_num is None:
                    drug_flag = 1
                    en1_num = find_drug(entity1)
                #en2_num = find_disease(entity2)
                if en1_num != None:
                    kb = zhiliao(entity1, entity2)
                    try:
                        drug_num = find_drug(kb[0])
                    except:
                        pass
                    if len(kb) == 0 or drug_num is None:
                        kb_link = 'null'
                    else:
                        kb_link = 'search/predetail?prename='+kb[0]+'&preid='+str(drug_num)+'&source=all#treat'
                    if drug_flag == 0:
                        tem = [entity1,rela,entity2,'search/med?medname='+entity1+'&medid='+str(en1_num),'null',kb_link]
                        qwq = '\t'.join(tem)
                    else:
                        tem = [entity1,rela,entity2,'search/pre?prename='+entity1+'&preid='+str(en1_num),'null',kb_link]
                        qwq = '\t'.join(tem)
                    printqwq(qwq)
                
        except:
            pass
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            

            
            
            
            
