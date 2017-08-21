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
from config import Config
from proxyloader import ProxyLoader
import uuid
import time

logger = logging.getLogger('tianyancha_fix')

class TianyanchaFixSpider(scrapy.Spider):

    name = "tianyancha_fix"
    allowed_domains = ["www.tianyancha.com"]
    start_urls = ['https://www.tianyancha.com/']
    dbclient = DBClient()
    redisclient = RadisClient()
    proxyloader = ProxyLoader()
    config = Config()
    count = 0
    proxy_url = ""
    interval = 25
    records_to_fix = []
    cur_index = 0
    step = 25

    def __init__(self, step=None, *args, **kwargs):
        super(TianyanchaFixSpider, self).__init__(*args, **kwargs)
        if (step):
            self.step = int(step)
        logger.info("Start to fix data")
        query = "select * from ISSUE_URL t where t.provice_code = '" + self.config.getCurrentProviceCode() + "' and t.status = '0'"
        self.records_to_fix = self.dbclient.queryDB(query)
        logger.info("************************Total data number: " + str(len(self.records_to_fix)))

    def parse(self, response):
        while self.cur_index < self.step:
            record = self.records_to_fix[self.cur_index]
            uuid = record[0]
            url = record[1]
            if self.count == 0:
                self.proxy_url = self.proxyloader.getProtocal() + self.proxyloader.getProxy()
                self.count = self.interval
                if self.cur_index != 0:
                    yield Request(url = self.start_urls[0], callback = self.parse)
            corp_code = url[url.rfind("/")+1:]
            self.cur_index = self.cur_index + 1
            if not self.redisclient.get(corp_code):
                provice_code = record[2]
                industry_code = record[3]
                logger.info("URL: " + url + " to be solved")
                self.count = self.count - 1
                yield Request(url = url, meta = {'proxy': self.proxy_url, 'provice_code': provice_code, 'industry_code': industry_code, 'corp_code': corp_code, 'uuid': uuid}, callback=self.parse_corp)
            else:
                logger.info('****************Corp: ' + corp_code + ' has been solved')
                updateSQL = "update ISSUE_URL t set t.status = '2' where t.id = '" + uuid +"'"
                self.dbclient.updateDB(updateSQL)

    def parse_corp(self, response):
        try:
            page = ""
            corp_code = response.meta['corp_code']
            provice_code = response.meta['provice_code']
            industry_code = response.meta['industry_code']
            uuid = response.meta['uuid']
            logger.info('****************Handle corperation info for industry: ' + industry_code + ' and page: ' + page)
            item = EnterpriseItem()
            item['province'] = provice_code
            item['industry_code'] = industry_code
            item['page'] = page
            item['name'] = response.xpath("//*[@id='company_web_top']/div[2]/div[2]/div/span/text()").extract()[0]
            item['phone'] = response.xpath("//*[@id='company_web_top']/div[2]/div[2]/div/div[2]/div[1]/span[2]/text()").extract()[0]
            item['address'] = response.xpath("//*[@id='company_web_top']/div[2]/div[2]/div/div[3]/div[2]/span[2]/text()").extract()[0]
            item['legal_person'] = response.xpath("//*[@id='_container_baseInfo']/div/div[1]/table/tbody/tr/td[1]/div/div[1]/div[2]/div/a/text()").extract()[0]
            item['credit_code'] = response.xpath("//*[@id='_container_baseInfo']/div/div[2]/table/tbody/tr[2]/td[1]/div/span/text()").extract()[0]
            item['reg_capital'] = response.xpath("//*[@id='_container_baseInfo']/div/div[1]/table/tbody/tr/td[2]/div[1]/div[2]/div/text()").extract()[0].strip()
            item['establish_date'] = response.xpath("//*[@id='_container_baseInfo']/div/div[1]/table/tbody/tr/td[2]/div[2]/div[2]/div/text()").extract()[0].strip()
            item['biz_period_start'] = response.xpath("//*[@id='_container_baseInfo']/div/div[2]/table/tbody/tr[4]/td[2]/div/span/text()").extract()[0].replace("\n", "").replace(' ', '')
            item['reg_authority'] = response.xpath("//*[@id='_container_baseInfo']/div/div[2]/table/tbody/tr[5]/td[2]/div/span/text()").extract()[0]
            item['biz_reg_num'] = response.xpath("//*[@id='_container_baseInfo']/div/div[2]/table/tbody/tr[1]/td[1]/div/span/text()").extract()[0]
            item['org_code'] = response.xpath("//*[@id='_container_baseInfo']/div/div[2]/table/tbody/tr[1]/td[2]/div/span/text()").extract()[0]
            item['taxpayer_code'] = response.xpath("//*[@id='_container_baseInfo']/div/div[2]/table/tbody/tr[3]/td/div/span/text()").extract()[0]
            item['industry'] = response.xpath("//*[@id='_container_baseInfo']/div/div[2]/table/tbody/tr[4]/td[1]/div/span/text()").extract()[0]
            self.redisclient.set(corp_code, corp_code)
            updateSQL = "update ISSUE_URL t set t.status = '1' where t.id = '" + uuid +"'"
            self.dbclient.updateDB(updateSQL)
            yield item
        except BaseException, e:
            logger.exception("Solve corp exception")
            updateSQL = "update ISSUE_URL t set t.status = '-1' where t.id = '" + uuid +"'"
            self.dbclient.updateDB(updateSQL)