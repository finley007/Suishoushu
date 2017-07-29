# -*- coding: utf-8 -*-
import logging
import re
import sys
import scrapy
import requests
from scrapy import Selector
from scrapy import Request

logger = logging.getLogger('proxy')

class ProxySpider(scrapy.Spider):
    name = "proxy"
    allowed_domains = ["www.goubanjia.com"]
    start_urls = ['http://www.goubanjia.com/free/gngn/index1.shtml']
    page = 1
    finalPage = 0
    target_url = 'http://sx.tianyancha.com/search'

    def parse(self, response):
    	if self.page == 1:
	        self.finalPage = int(response.xpath('//*[@id="list"]/div[2]/a[9]/text()').extract()[0])
        logger.info('Start to obtain proxy for page: ' + str(self.page))
        self.page = self.page + 1
        items = response.xpath('//*[@id="list"]/table/tbody/tr').extract()
        for item in items:
            sl = Selector(text = item)
            type = ''
            if sl.xpath("//td[2]/a/text()").extract():
            	type = sl.xpath("//td[2]/a/text()").extract()[0].encode('utf-8')
            if type == '高匿':
            	contents = sl.xpath("//td[@class='ip']/*").extract()
            	host = ''
            	last = ''
            	for content in contents:
            		s = Selector(text = content)
            		if (not s.xpath("//@style").extract() or s.xpath("//@style").extract()[0].find('none') == -1) and s.xpath("//text()"):
            			host += s.xpath("//text()").extract()[0]
            			last = s.xpath("//text()").extract()[0]
            	pos = len(host) - len(last)
            	host = host[0:pos] + ':' + last
            	logger.info('*****************************************' + host)
            	try:
            		headers = {'user-agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36'}
            		r = requests.get(url=self.target_url, proxies={'http': host}, headers = headers, timeout = 10)    # 最基本的GET请求
            		logger.info('!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!' + str(r.status_code))
            		logger.info('!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!' + r.url)
            		if r.status_code == 200 and r.url == self.target_url:
        				self.writeToFile(proxy)
            	except BaseException, e:
            		logger.info('***************************************** failed' + repr(e))
        if self.page <= self.finalPage:
        		url = "http://www.goubanjia.com/free/gngn/index" + str(self.page) + ".shtml"
        		yield Request(url=url, callback=self.parse)

    def writeToFile(self, host):
    	f = open('../output/proxy','a')
    	f.write(host + "\n")
    	f.close()
