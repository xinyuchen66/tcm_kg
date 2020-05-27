# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://doc.scrapy.org/en/latest/topics/item-pipeline.html

import json
import codecs


class BaiduPipeline(object):
    def process_item(self, item, spider):
        lines = json.dumps(dict(item), ensure_ascii=False)
        f = codecs.open('output/res.json', 'a', 'utf-8')
        f.write(lines + '\n')
        f.close()
        return item
