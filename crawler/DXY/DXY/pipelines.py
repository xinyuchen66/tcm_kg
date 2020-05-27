# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://docs.scrapy.org/en/latest/topics/item-pipeline.html

import pymysql

class DxyPipeline(object):

    def __init__(self):
        self.db = pymysql.connect(
            host='10.15.82.50', user='root', passwd='123',
            port=3306, db='lsc', charset='utf8'
        )
        self.cursor = self.db.cursor()


    def process_item(self, item, spider):
        sql = "INSERT INTO QA(question,answer) VALUES(%s,%s)"
        self.cursor.execute(sql,(item['question'],item['answer']))
        self.db.commit()
