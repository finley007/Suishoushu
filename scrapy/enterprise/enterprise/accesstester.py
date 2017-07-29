# -*- coding: utf-8 -*-
'''
用来测试限制ip访问次数
'''
import requests
 
headers = {'user-agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36'}
isLimit = False
count = 0
while not isLimit:
	r = requests.get(url='https://snx.tianyancha.com/search', headers=headers, proxies={'https': '49.71.106.216:6882'})    # 最基本的GET请求
	print(r.status_code)    # 获取返回状态
	print(r.url)
	print(r.elapsed.microseconds)
	#print(r.text)
	if r.status_code == 200 and r.url == 'https://snx.tianyancha.com/search':
		count = count + 1
	else:
		isLimit = True
print(count)