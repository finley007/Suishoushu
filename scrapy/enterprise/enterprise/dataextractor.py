# -*- coding: utf-8 -*-
'''
这个类是用来执行数据获取，指定省，行业和页面，每次获取一个新的ip执行爬虫从指定的省，行业，页面开始向下继续爬取
'''
import requests
import json
import sys, getopt
import os
sys.path.append(r'../') 
from config import Config

def main(argv):
	config = Config()
	provice_code = config.getCurrentProviceCode()
	industry_code = config.getDefaultIndustryCode()
	page_code = config.getDefaultPageCode()

	try:
	  opts, args = getopt.getopt(argv,"hp:i:g:")
	except getopt.GetoptError:
	  print 'ipextractor.py -p <provice> -i <industry> -g <page>'
	  sys.exit(2)
	for opt, arg in opts:
	  if opt == '-h':
	     print 'ipextractor.py -p <provice> -i <industry> -g <page>'
	     sys.exit()
	  elif opt in ("-p"):
	     provice_code = arg
	  elif opt in ("-i"):
	     industry_code = arg
	  elif opt in ("-g"):
	  	page_code = arg
	scrapyCmd = 'scrapy runspider tianyancha.py'
	if provice_code:
		scrapyCmd += ' -a prov=' + provice_code
	if industry_code:
		scrapyCmd += ' -a ind=' + industry_code
	if page_code:
		scrapyCmd += ' -a page=' + page_code
	os.system(scrapyCmd)

if __name__ == "__main__":
   main(sys.argv[1:])



