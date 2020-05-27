# -*- coding: utf-8 -*-
import scrapy
import json
from DXY.items import DxyItem

class DxySpider(scrapy.Spider):
    name = 'dxy'
    allowed_domains = ['ask.dxy.com']
    start_urls = ['http://ask.dxy.com/']

    def start_requests(self):
        url = "https://ask.dxy.com/view/i/question/list/section?section_group_name=buxian&page_index={}"
        index = 3536
        while True:
            yield scrapy.Request(url.format(index), self.one_index)
            index+=1

    def one_index(self, response):
        page = json.loads(response.text)
        for tem in page['data']['items']:
            item = DxyItem()
            item['question'] = tem['dialogs'][0]['content'].replace("<br>","")
            item['answer'] = tem['dialogs'][1]['content'].replace("<br>", "")
            yield item
