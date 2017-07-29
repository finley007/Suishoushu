# -*- coding: utf-8 -*-
import os
import sys, getopt
import time
sys.path.append(r'../') 
from redisclient import RadisClient
from config import Config

def main(argv):
	redisclient = RadisClient()
	config = Config()
	industry_code = config.getDefaultIndustryCode()
	url = config.getRootUrls()
	step = config.getStep()
	try:
	  opts, args = getopt.getopt(argv,"hi:")
	except getopt.runner.py:
	  print 'runner.py -i <industry_code>'
	  sys.exit(2)
	for opt, arg in opts:
	  if opt == '-h':
	     print 'runner.py -i <industry_code>'
	     sys.exit()
	  elif opt in ("-i"):
	     industry_code  = arg

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