# -*- coding: utf-8 -*-
import logging
import re
import sys
import scrapy
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
import random

logger = logging.getLogger('tianyancha')

class TianyanchaSpider(scrapy.Spider):
    name = "tianyancha"
    config = Config()
    allowed_domains = config.getAllowDomains()
    start_urls = config.getStartUrls()
    #start_urls = ['http://snx.tianyancha.com/search/oc26']
    #start_urls = ['https://www.tianyancha.com/company/644630399']
    provice_code = config.getCurrentProviceCode()
    industry_code = config.getDefaultIndustryCode()
    start_page_code = config.getDefaultPageCode()
    page_count = config.getStep()
    dbclient = DBClient()
    redisclient = RadisClient()
    proxyloader = ProxyLoader()
    start_url = ""
    proxy_url = ""

    def __init__(self, prov=None, ind=None, page=None, count=None, *args, **kwargs):
        super(TianyanchaSpider, self).__init__(*args, **kwargs)
        if (prov):
            self.provice_code = prov
        if (ind):
            self.industry_code = ind
        if (page and page != 'p1'):
            self.start_page_code = page
        if (count):
            self.page_count = int(count)
        self.start_url = 'https://' + self.provice_code + '.tianyancha.com/search/' + self.industry_code + '/' + self.start_page_code
        #print("******************************" + start_url)
        self.start_urls = [self.start_url]
    
    def parse(self, response):
        logger.info('Start to parse industry info: ' + self.industry_code)
        logger.info(response)
        try:
            url = response.meta['url']
        except BaseException, e:
            url = self.start_url
        try:
            current_url = response.url
            page = current_url[current_url.rfind("/")+1:]
            items = response.xpath("/html/body/div[2]/div[1]/div/div/div[1]/div[3]/div").extract()
            logger.info("***************Items length: " + str(len(items)))
            if len(items) == 0:
                raise Exception()

            for item in items:
                sl = Selector(text = item)
                corp_urls = sl.xpath("//div[2]/div[1]/div[1]/a/@href").extract()
                for corp_url in corp_urls:
                    corp_url = self.solveHttps(corp_url)
                    logger.info('Corporation URL: ' + corp_url)
                    corp_code = corp_url[corp_url.rfind("/")+1:]
                    if not self.redisclient.get(corp_code):
                        if self.proxy_url == "":
                            yield Request(url=corp_url, meta={'page': page, 'corp_url': corp_url, 'corp_code': corp_code}, callback=self.parse_corp)
                        else:
                            yield Request(url=corp_url, meta={'proxy': self.proxy_url, 'page': page, 'corp_url': corp_url, 'corp_code': corp_code}, callback=self.parse_corp)
            
            nextPage = response.xpath("/html/body/div[2]/div[1]/div/div/div[1]/div[4]/ul/li[14]/a/@href").extract()
            if not nextPage:
                nextPage = response.xpath("/html/body/div[2]/div[1]/div/div/div[1]/div[4]/ul/li[13]/a/@href").extract()
            if nextPage and nextPage[0] and self.page_count > 0:
                logger.info('***************Next url: ' + nextPage[0])
                self.page_count = self.page_count - 1
                if self.proxy_url == "":
                    yield Request(url=nextPage[0], meta={'url': nextPage[0]}, callback=self.parse)
                else:
                    yield Request(url=nextPage[0], meta={'proxy': self.proxy_url, 'url': nextPage[0]}, callback=self.parse)

        except BaseException, e:
            logger.info('***************Error parsing url: ' + url + ' and reload by using new proxy')
            #重启一个代理
            proxy = self.proxyloader.getProxy()
            if proxy != "":
                self.proxy_url = self.proxyloader.getProtocal() + proxy
                yield Request(url=url, meta={'url': url, 'proxy': self.proxy_url}, callback=self.parse, dont_filter=True)

    def parse_corp(self, response):
        try:
            page = response.meta['page']
            corp_code = response.meta['corp_code']
            corp_url = response.meta['corp_url']
            logger.info('****************Handle corperation info for industry: ' + self.industry_code + ' and page: ' + page)
            item = EnterpriseItem()
            item['name'] = response.xpath("/html/body/div[2]/div[1]/div/div/div/div[1]/div[2]/div[2]/div/span/text()").extract()[0]
            phone = response.xpath("/html/body/div[2]/div[1]/div/div/div/div[1]/div[2]/div[2]/div/div[2]/div[1]/span[2]/text()").extract()
            if phone:
                item['phone'] = phone[0]
            else:
                item['phone'] = response.xpath("/html/body/div[2]/div[1]/div/div[2]/div/div[1]/div[2]/div[2]/div/div[3]/div[1]/span[2]/text()").extract()[0]
            address = response.xpath("/html/body/div[2]/div[1]/div/div/div/div[1]/div[2]/div[2]/div/div[3]/div[2]/span[2]/text()").extract()
            if address:
                item['address'] = address[0]
            else:
                item['address'] = response.xpath("/html/body/div[2]/div[1]/div/div[2]/div/div[1]/div[2]/div[2]/div/div[4]/div[2]/span[2]").extract()[0]
            item['province'] = self.provice_code
            item['industry_code'] = self.industry_code
            item['page'] = page
            if response.xpath("//*[@id='nav-main-shangshiitem']/span").extract():
                item['legal_person'] = response.xpath("/html/body/div[2]/div[1]/div/div/div/div[2]/div/div[2]/div[3]/div[2]/table/tbody/tr/td[1]/div/div[1]/div[2]/div/a/text()").extract()[0]
                item['credit_code'] = response.xpath("/html/body/div[2]/div[1]/div/div/div/div[2]/div/div[2]/div[3]/div[3]/table/tbody/tr[2]/td[1]/div/span/text()").extract()[0]
                item['reg_capital'] = response.xpath("/html/body/div[2]/div[1]/div/div/div/div[2]/div/div[2]/div[3]/div[2]/table/tbody/tr/td[2]/div[1]/div[2]/div/text()").extract()[0].strip()
                item['establish_date'] = response.xpath("/html/body/div[2]/div[1]/div/div/div/div[2]/div/div[2]/div[3]/div[2]/table/tbody/tr/td[2]/div[2]/div[2]/div/text()").extract()[0].strip()
                item['biz_period_start'] = response.xpath("/html/body/div[2]/div[1]/div/div/div/div[2]/div/div[2]/div[3]/div[3]/table/tbody/tr[4]/td[2]/div/span/text()").extract()[0].replace("\n", "").replace(' ', '')
                item['reg_authority'] = response.xpath("/html/body/div[2]/div[1]/div/div/div/div[2]/div/div[2]/div[3]/div[3]/table/tbody/tr[5]/td[2]/div/span/text()").extract()[0]
                item['biz_reg_num'] = response.xpath("/html/body/div[2]/div[1]/div/div/div/div[2]/div/div[2]/div[3]/div[3]/table/tbody/tr[1]/td[1]/div/span/text()").extract()[0]
                item['org_code'] = response.xpath("/html/body/div[2]/div[1]/div/div/div/div[2]/div/div[2]/div[3]/div[3]/table/tbody/tr[1]/td[2]/div/span/text()").extract()[0]
                item['taxpayer_code'] = response.xpath("/html/body/div[2]/div[1]/div/div/div/div[2]/div/div[2]/div[3]/div[3]/table/tbody/tr[3]/td/div/span/text()").extract()[0]
                item['industry'] = response.xpath("/html/body/div[2]/div[1]/div/div/div/div[2]/div/div[2]/div[3]/div[3]/table/tbody/tr[4]/td[1]/div/span/text()").extract()[0]
            else:
                item['legal_person'] = response.xpath("/html/body/div[2]/div[1]/div/div/div/div[2]/div/div[2]/div[2]/div[2]/table/tbody/tr/td[1]/div/div[1]/div[2]/div/a/text()").extract()[0]
                item['credit_code'] = response.xpath("/html/body/div[2]/div[1]/div/div/div/div[2]/div/div[2]/div[2]/div[3]/table/tbody/tr[2]/td[1]/div/span/text()").extract()[0]
                item['reg_capital'] = response.xpath("/html/body/div[2]/div[1]/div/div/div/div[2]/div/div[2]/div[2]/div[2]/table/tbody/tr/td[2]/div[1]/div[2]/div/text()").extract()[0].strip()
                item['establish_date'] = response.xpath("/html/body/div[2]/div[1]/div/div/div/div[2]/div/div[2]/div[2]/div[2]/table/tbody/tr/td[2]/div[2]/div[2]/div/text()").extract()[0].strip()
                item['biz_period_start'] = response.xpath("/html/body/div[2]/div[1]/div/div/div/div[2]/div/div[2]/div[2]/div[3]/table/tbody/tr[4]/td[2]/div/span/text()").extract()[0].replace("\n", "").replace(' ', '')
                item['reg_authority'] = response.xpath("/html/body/div[2]/div[1]/div/div/div/div[2]/div/div[2]/div[2]/div[3]/table/tbody/tr[5]/td[2]/div/span/text()").extract()[0]
                item['biz_reg_num'] = response.xpath("/html/body/div[2]/div[1]/div/div/div/div[2]/div/div[2]/div[2]/div[3]/table/tbody/tr[1]/td[1]/div/span/text()").extract()[0]
                item['org_code'] = response.xpath("/html/body/div[2]/div[1]/div/div/div/div[2]/div/div[2]/div[2]/div[3]/table/tbody/tr[1]/td[2]/div/span/text()").extract()[0]
                item['taxpayer_code'] = response.xpath("/html/body/div[2]/div[1]/div/div/div/div[2]/div/div[2]/div[2]/div[3]/table/tbody/tr[3]/td/div/span/text()").extract()[0]
                item['industry'] = response.xpath("/html/body/div[2]/div[1]/div/div/div/div[2]/div/div[2]/div[2]/div[3]/table/tbody/tr[4]/td[1]/div/span/text()").extract()[0]
            self.redisclient.set(corp_code, corp_code)
            yield item
        except BaseException, e:
            rdm = int(random.random() * 100)
            logger.info('***************Error parsing url:' + corp_url + ' and get random: ' + str(rdm))
            if rdm % 17 >= 0:
                #当前ip被禁止访问了则记录下来，用于以后重发
                self.handleCorpError(corp_url)
            else:
                #重启一个代理
                proxy = self.proxyloader.getProxy()
                if proxy != "":
                    self.proxy_url = self.proxyloader.getProtocal() + proxy
                    yield Request(url=corp_url, meta={'page': page, 'corp_url': corp_url, 'corp_code': corp_code, 'proxy': self.proxy_url}, callback=self.parse_corp, dont_filter=True)

    def handleCorpError(self, url):
        record_id = uuid.uuid1()
        updateSQL = "insert into ISSUE_URL values ('" + str(record_id)  + "', '" + url + "')"
        self.dbclient.updateDB(updateSQL)

    def solveHttps(self, url):
        if url and url.startswith("https"):
            return url.replace("https", "http")
        else:
            return url    