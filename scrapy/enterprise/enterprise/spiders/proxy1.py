# -*- coding: utf-8 -*-
import logging
import re
import sys
import scrapy
import requests
from scrapy import Selector
from scrapy import Request

logger = logging.getLogger('proxy1')

class Proxy1Spider(scrapy.Spider):
    name = "proxy1"
    allowed_domains = ["www.xicidaili.com"]
    start_urls = ['http://www.xicidaili.com/nn/']
    target_url = 'http://sx.tianyancha.com/search'
    #target_url = 'http://47.94.41.252:8080/suishoushu/inbound/detail'
    max_num = 3
    page = 1

    def parse(self, response):
        logger.info('Start to obtain proxy')
        items = response.xpath('//*[@id="ip_list"]/tr').extract()
        index = 0
        for item in items:
            if index == 0:
                index = index + 1
                continue
            sl = Selector(text = item)
            host = sl.xpath("//td[2]/text()").extract()[0]
            port = sl.xpath("//td[3]/text()").extract()[0]
            protocol = sl.xpath("//td[6]/text()").extract()[0]
            proxy = host + ':' + port
            logger.info('*****************************************' + proxy)
            try:
        		headers = {'user-agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36'}
        		r = requests.get(url=self.target_url, proxies={'http': proxy}, headers = headers, timeout = 10)    # 最基本的GET请求
        		logger.info('!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!' + str(r.status_code))
        		logger.info('!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!' + r.url)
        		if r.status_code == 200 and r.url == self.target_url and protocol == "HTTP":
        			self.writeToFile(proxy)
            except BaseException, e:
        		logger.info('***************************************** failed' + repr(e))
        if self.page <= self.max_num:
        	url = "http://www.xicidaili.com/nn/" + str(self.page)
        	yield Request(url=url, callback=self.parse)
        self.page = self.page + 1

    def writeToFile(self, host):
    	f = open('../output/proxy1','a')
    	f.write(host + "\n")
    	f.close()

    def solveHttps(self, url):
        if url and url.startswith("https"):
            return url.replace("https", "http")
        else:
            return url 