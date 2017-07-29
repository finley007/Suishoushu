# -*- coding: utf-8 -*-
import scrapy
import logging
import re
import sys
import scrapy
import requests
from scrapy import Selector
from scrapy import Request

logger = logging.getLogger('proxy4')

class Proxy4Spider(scrapy.Spider):
    name = "proxy4"
    allowed_domains = ["www.xdaili.cn"]
    start_urls = ['http://www.xdaili.cn/freeproxy.html']
    target_url = 'http://sx.tianyancha.com/search'

    def parse(self, response):
        logger.info('Start to obtain proxy')
        items = response.xpath('//*').extract()[0].encode('utf-8')
        logger.info(items)
        for item in items:
        	sl = Selector(text = item)
        	host = sl.xpath("//td[1]/text()").extract()[0]
        	port = sl.xpath("//td[2]/text()").extract()[0]
        	proxy = host + ':' + port
        	logger.info('*****************************************' + proxy)
        	try:
        		headers = {'user-agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36'}
        		r = requests.get(url=self.target_url, proxies={'http': proxy}, headers = headers, timeout = 10)    # 最基本的GET请求
        		logger.info('!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!' + str(r.status_code))
        		logger.info('!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!' + r.url)
        		if r.status_code == 200 and r.url == self.target_url:
        			self.writeToFile(proxy)
        	except BaseException, e:
        		logger.info('***************************************** failed' + repr(e))


    def writeToFile(self, host):
    	f = open('../output/proxy4','a')
    	f.write(host + "\n")
    	f.close()