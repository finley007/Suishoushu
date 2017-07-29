# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: http://doc.scrapy.org/en/latest/topics/item-pipeline.html
import logging
import codecs

logger = logging.getLogger('EnterprisePipeline')

class EnterprisePipeline(object):
    def process_item(self, item, spider):
    	result = item['province'] + '|'
    	result += item['industry_code'] + '|'
    	result += item['page'] + '|'
    	result += item['credit_code'] + '|'
    	result += item['name'] + '|'
    	result += item['legal_person'] + '|'
    	result += item['reg_capital'] + '|'
    	result += item['establish_date'] + '|'
    	result += item['biz_period_start'] + '|'
    	result += item['reg_authority'] + '|'
    	result += item['address'] + '|'
    	result += item['phone'] + '|'
    	result += item['biz_reg_num'] + '|'
    	result += item['org_code'] + '|'
    	result += item['taxpayer_code'] + '|'
    	result += item['industry']
    	self.f.write(result + "\n")
        return item

    def open_spider(self, spider):
    	logger.info("Create result file")
        self.f = codecs.open('../output/result','a','utf-8')

    def close_spider(self, spider):
    	logger.info("Close result file")
        self.f.close()