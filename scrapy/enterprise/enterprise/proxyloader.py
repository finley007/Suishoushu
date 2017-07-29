# -*- coding:utf-8 -*-
import requests
import json
import os
import logging
import time
'''代理服务器更新，代理来自http://http.zhimaruanjian.com/getapi/'''

logger = logging.getLogger('ProxyLoader')

class ProxyLoader:

	template_file = "../proxymiddlewares.tmpl"
	target_file = "../proxymiddlewares.py" 
	url = "http://http-webapi.zhimaruanjian.com/getip?num=1&type=2&pro=&city=0&yys=0&port=1&pack=307&ts=0&ys=0&cs=0&lb=1&sb=0&pb=4&mr=2" 
	headers = {'user-agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36'}
	replacer = "######"
	protocal = "http://"
	delay = 5

   	def loadNewProxy(self):
		d1 = self.callAPI()
		returnCode = d1["success"]
		while not returnCode:
			time.sleep(self.delay)
			d1 = self.callAPI()
			returnCode = d1["success"]
		result = ""
		if d1["data"]:
			for item in d1["data"]:
				result += "'" + item["ip"] + ":" + str(item["port"]) + "',"
		result = result[0:len(result) - 1]
		logger.info("Use proxy: " + result)
		if result != '':
			s_file = file(self.template_file,'r+')
			d_file  = file(self.target_file + '.tmp','w')
			for line in s_file.readlines():
	 			d_file.writelines(line.replace(self.replacer, result))
			s_file.close()
			d_file.close()
			os.rename(self.target_file + '.tmp', self.target_file) 

	def getProxy(self):
		d1 = self.callAPI()
		returnCode = d1["success"]
		while not returnCode:
			time.sleep(self.delay)
			d1 = self.callAPI()
			returnCode = d1["success"]
		result = ""
		if d1["data"]:
			result = d1["data"][0]["ip"] + ":" + str(d1["data"][0]["port"])
		logger.info("Use proxy: " + result)
		return result

	def getProtocal(self):
		return self.protocal

	def callAPI(self):
		r = requests.get(url=self.url, headers=self.headers)
   		logger.info("Load proxy: " + r.text)
   		if r.text == "":
   			time.sleep(self.delay)
   			return self.callAPI();
		return json.loads(r.text)

