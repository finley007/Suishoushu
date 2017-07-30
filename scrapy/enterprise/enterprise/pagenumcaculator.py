# -*- coding: utf-8 -*-
'''
计算每个行业页面数
'''
import requests
import sys, getopt
import json
sys.path.append(r'./') 
from redisclient import RadisClient
from config import Config
from proxyloader import ProxyLoader

max = 500
min = 0

start_index = 2
end_index = 96
proxyloader = ProxyLoader()
proxy = proxyloader.getProxy()

headers = {'user-agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36'}

def main(argv):
	redisclient = RadisClient()
	config = Config()
	start_index = 2
	end_index = 96

	try:
	  opts, args = getopt.getopt(argv,"hs:e:")
	except getopt.pagenumcaculator.py:
	  print 'pagenumcaculator.py -s <start> -e <end>'
	  sys.exit(2)
	for opt, arg in opts:
	  if opt == '-h':
	     print 'pagenumcaculator.py -s <start> -e <end>'
	     sys.exit()
	  elif opt in ("-s"):
	     start_index = arg
	  elif opt in ("-e"):
	     end_index = arg

	for num in range(int(start_index), int(end_index) + 1):
		url = "https://" + config.getCurrentProviceCode() + ".tianyancha.com/search/oc" + toStr(num)
	 	redisclient.set(url, getMaxPage(url, max, min))

def toStr(num):
	str_num = str(num)
	if len(str_num) == 1:
		return "0" + str_num
	else:
		return str_num

def getMaxPage(url, max, min):
	print(str(max) + "|" + str(min))
	if max == min:
		return max
	if max == min + 1:
		return min
	middle = (max + min) / 2
	if not loadPageSuccess(url + "/p" + str(middle)):
		max = middle
	else:
		min = middle
	return getMaxPage(url, max, min)


def loadPageSuccess(url):
	#print(result)
	print(proxy)
	r = requests.get(url=url, headers=headers, proxies={'https': proxy})    # 最基本的GET请求
	print(r.status_code)
	print(r.url)
	if 'login' in r.url:
		global proxy
		proxy = proxyloader.getProxy() 
		print('Use new proxy: ' + proxy)
		r = requests.get(url=url, headers=headers, proxies={'https': proxy})
		print(r.status_code)
		print(r.url)
	#print(r.text)
	return r.status_code == 200  and r.text.find("notFound-text.png") == -1# 获取返回状态
	#print(r.elapsed.microseconds)

if __name__ == "__main__":
   main(sys.argv[1:])