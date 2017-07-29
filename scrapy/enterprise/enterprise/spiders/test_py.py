# -*- coding: utf-8 -*-
import logging
import re
import sys
import scrapy
from scrapy import Selector
from scrapy import Request

logger = logging.getLogger('test')

class TestPySpider(scrapy.Spider):
    name = "test.py"
    allowed_domains = ["47.94.41.252"]
    start_urls = ['http://47.94.41.252:8080/suishoushu/endpoints']
    call_times = 10

    def parse(self, response):
        if self.call_times > 0:
        	self.call_times = self.call_times - 1
        	logger.info("Call times: " + str(self.call_times))
        	yield Request(url=self.start_urls[0], callback=self.parse)
