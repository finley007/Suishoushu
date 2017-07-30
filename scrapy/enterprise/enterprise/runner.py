# -*- coding: utf-8 -*-
import os
import sys, getopt
import time
sys.path.append(r'../') 
from redisclient import RadisClient
from config import Config

redisclient = RadisClient()

def main(argv):
	config = Config()
	industry_code = ""
	is_run_single = ""
	url = config.getRootUrls()
	step = config.getStep()
	max_industry_code = 96
	try:
	  opts, args = getopt.getopt(argv,"hi:s:")
	except getopt.runner.py:
	  print 'runner.py -i <industry_code> -s <is_run_single>'
	  sys.exit(2)
	for opt, arg in opts:
	  if opt == '-h':
	     print 'runner.py -i <industry_code> -s <is_run_single>'
	     sys.exit()
	  elif opt in ("-i"):
	     industry_code  = arg
	  elif opt in ("-s"):
	     is_run_single  = arg

	if industry_code == "":
		industry_code = config.getDefaultIndustryCode()
	if is_run_single == "y":
		runDataExtractor(url, industry_code, step)
	else:
		index = int(industry_code[2:])
		for i in range(1, 96):
			if i >= index:
				runDataExtractor(url, "oc" + toStr(i), step)

def toStr(num):
	str_num = str(num)
	if len(str_num) == 1:
		return "0" + str_num
	else:
		return str_num

def runDataExtractor(url, industry_code, step):
	url = url + industry_code
	print(url)
	pageNum = int(redisclient.get(url))
	if (pageNum % step) != 0:
		pageNum = pageNum + (step - pageNum % step)
	for i in range(1, pageNum, step):
		#print(str(i))
		os.system("python ../dataextractor.py -i " + industry_code + " -g p" + str(i))
		#time.sleep(20)

if __name__ == "__main__":
   main(sys.argv[1:])