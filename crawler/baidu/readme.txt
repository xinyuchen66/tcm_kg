scrapy startproject NAME
cd NAME
scrapy genspider SPIDER_NAME www.xxx.com
scrapy crawl zhihu_spider

修改items.py成为自己的字典
进入spiders文件夹修改SPIDER_NAME.py

保存数据
	scrapy crawl quotes -o quotes.json
	scrapy crawl quotes -o quotes.jl

css选择器
	response.css('a[href="image.html"]::text').extract_first()
	#找内部节点 再加一个空格 获取属性::attr(属性名)
	response.css('a[href="image.html"] img::attr(src)').extract_first()

	可使用
	from scrapy import Selector
	body="<h1 href='baidu.com'>hello</h1>"
	s = Selector(text=body)
	s.css('h1::text')


	正则re/re_first
	s.css('h1::text').re_first('.*')
	全文正则
	s.xpath('.').re()

scrapy shell http://quotes.toscrape.com