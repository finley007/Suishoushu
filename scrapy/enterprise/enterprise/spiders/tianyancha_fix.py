# -*- coding: utf-8 -*-
import scrapy
import logging
import re
import sys
import redis
from scrapy import Selector
from scrapy import Request
from enterprise.items import EnterpriseItem
import MySQLdb
sys.path.append(r'../') 
from dbclient import DBClient
from redisclient import RadisClient
import uuid

logger = logging.getLogger('tianyancha_fix')

class TianyanchaFixSpider(scrapy.Spider):
    name = "tianyancha_fix"
    allowed_domains = ["www.tianyancha.com"]
    start_urls = ['https://www.tianyancha.com/']
    query = "select count(*), industry_code, page_code  from data_result group by provice_code, industry_code, page_code order by industry_code, page_code"
    dbclient = DBClient()
    redisclient = RadisClient()

    def parse(self, response):
    	logger.info("Start to fix data")
        result = self.dbclient.queryDB(self.query)
        logger.info("************************Total data number: " + str(len(result)))
        for record in result:
        	count = record[0]
        	industry_code = record[1]
        	page_code = record[2]
        	logger.info("Industry:" + industry_code + "|Page:" + page_code + "|Count:" + str(count))
        	if count != 20:
        		logger.info("Start to solve industry: " + industry_code + " and page: " + page_code)
        		os.system("python ../dataextractor.py -i " + industry_code + " -g p" + page_code)