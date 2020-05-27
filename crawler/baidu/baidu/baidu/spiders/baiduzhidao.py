# -*- coding: utf-8 -*-
import scrapy
import re
import json
from baidu.items import BaiduItem


class BaiduzhidaoSpider(scrapy.Spider):
    name = 'baiduzhidao'
    allowed_domains = ['zhidao.baidu.com']
    start_urls = ['http://zhidao.baidu.com/']

    def __init__(self):
        self.collect_set = set()

    def start_requests(self):
        base_url = self.settings.get("INIT_URL")
        for url in base_url:
            self.collect_set.add(url)
            yield scrapy.Request(url, self.parse)

    def parse(self, response):
        QAitem = BaiduItem()
        QAitem['question'] = response.css('span[class="ask-title"]::text').extract_first()
        QAitem['description'] = response.css('span[class="con-all"]::text').extract_first()
        QAitem['answer'] = ''.join(response.css('div[accuse="aContent"]::text').extract()).replace('\n', '')
        if QAitem['question'] is not None:
            yield QAitem
        for single_url in response.css('span[class="related-restrict-title grid"]').xpath('..').css("::attr('href')").extract():
            if single_url[1] == 'q':
                self.collect_set.add(single_url)
                yield scrapy.Request("https://zhidao.baidu.com"+single_url, self.parse)



