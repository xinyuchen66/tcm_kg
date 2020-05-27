# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# https://docs.scrapy.org/en/latest/topics/items.html

import scrapy


class DxyItem(scrapy.Item):
    question = scrapy.Field()
    answer = scrapy.Field()
