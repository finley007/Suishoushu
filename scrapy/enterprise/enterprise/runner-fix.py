# -*- coding: utf-8 -*-
'''
这个类是用来执行数据获取，指定省，行业和页面，每次获取一个新的ip执行爬虫从指定的省，行业，页面开始向下继续爬取
'''
import requests
import json
import sys, getopt
import os
sys.path.append(r'./') 
from dbclient import DBClient
from config import Config

def main(argv):
	step = 25
	dbclient = DBClient()
	config = Config()
	try:
	  opts, args = getopt.getopt(argv,"hs:")
	except getopt.GetoptError:
	  print 'runner-fix.py -s <step>'
	  sys.exit(2)
	for opt, arg in opts:
	  if opt == '-h':
	     print 'runner-fix.py -s <step>'
	     sys.exit()
	  elif opt in ("-s"):
	     step = int(arg)
	while (dbclient.getIssueUrlNum(config.getCurrentProviceCode()) > 0):
		scrapyCmd = 'scrapy runspider tianyancha_fix.py -a step=' + str(step)
		os.system(scrapyCmd)

if __name__ == "__main__":
   main(sys.argv[1:])



