# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# http://doc.scrapy.org/en/latest/topics/items.html

import scrapy


class EnterpriseItem(scrapy.Item):
    # define the fields for your item here like:
    # name = scrapy.Field()
    credit_code = scrapy.Field()
    name = scrapy.Field()
    legal_person = scrapy.Field()
    reg_capital = scrapy.Field()
    establish_date = scrapy.Field()
    biz_period_start = scrapy.Field()
    biz_period_end = scrapy.Field()
    reg_authority = scrapy.Field()
    address = scrapy.Field()
    phone = scrapy.Field()
    bank = scrapy.Field()
    bank_acct = scrapy.Field()
    create_time = scrapy.Field()
    create_by = scrapy.Field()
    modify_time = scrapy.Field()
    modify_by = scrapy.Field()
    biz_reg_num = scrapy.Field()
    org_code = scrapy.Field()
    taxpayer_code = scrapy.Field()
    industry = scrapy.Field()
    province = scrapy.Field()
    industry_code = scrapy.Field()
    page = scrapy.Field()
