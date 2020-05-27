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
            print(j+'\t治疗\t'+i)
    for i in cur_disease:
        for j in cur_drug:
            print(j+'\t治疗\t'+i)
    for i in cur_plant:
        for j in cur_drug:
            print(i+'\t组成\t'+j)
    for i in range(len(cur_plant)):
        for j in cur_plant[i+1:]:
            print(cur_plant[i]+'\t伴随\t'+j)




if __name__ == "__main__":
    while True:
        """
        example:
        我肚子疼，耳痛，还有点近视，能不能吃建兰做成的曼宁治疗？
        难产造成的龋齿能吃去痛片吗？
        """
        extract(input('>>>'))