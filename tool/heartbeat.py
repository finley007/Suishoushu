# -*- coding: utf-8 -*-

import sys, getopt
import urllib
import urllib2
import json
import MySQLdb
import redis
import logging
import logging.config
import os
import datetime
import time
import smtplib  
from email.mime.text import MIMEText  
from email.header import Header 
import traceback
import re
import chardet

EMAIL_NOTIFY_SWITCH = True

#LOG_DIR = '/Users/finley/Finley/workspace/java/Suishoushu/log'
LOG_DIR = '/home/suishoushu/log'
SESSION_KEY = 'session_map_111111'
EMAIL_SERVER = 'smtp.163.com'
EMAIL_SENDER = 'changyi_688@163.com'  
EMAIL_RECEIVER = 'finley007@163.com; 147699362@qq.com'  
EMAIL_SUBJECT = u'发票闪开系统告警'  
EMAIL_SMTPSERVER = 'smtp.163.com'  
EMAIL_PASSWORD = 'changyi2017'  

#15分钟
TIME_UNIT = 15 * 60
TIME_FORMAT = '%Y-%m-%d %H:%M:%S'

ZH_PATTERN = re.compile(u'[\u4e00-\u9fa5]+')

reload(sys)
sys.setdefaultencoding('utf-8')

conn = MySQLdb.connect('localhost', 'root', 'root', 'fi', charset='utf8')
cursor = conn.cursor()
redis_client = redis.StrictRedis(host='localhost',port=6380)


smtp = smtplib.SMTP()  
smtp.connect(EMAIL_SERVER)  
smtp.login(EMAIL_SENDER, EMAIL_PASSWORD) 

def main(argv):
	logging.info('************************Start heartbeat check')
	getSession()
	cursor.execute("select * from sys_heartbeat_config where status = '0'")
	heartbeat_items = cursor.fetchall()
	for item in heartbeat_items:
		cid = item[0]
		url = item[1]
		data = item[3]
		logging.info('-----------Check link: ' + url + ' data: ' + data)
		method = item[2]
		ntime = item[6]
		is_auth = item[7]
		interval = item[5]
		#配置了区间
		isCheck = True
		if not interval is None and interval > 0:
			current_time = datetime.datetime.now()
			time_stamp = time.mktime(ntime.timetuple())
			current_time_stamp = time.mktime(current_time.timetuple())
			logging.info('Interval is ' + str(interval) 
			+ ' and next time: ' + ntime.strftime(TIME_FORMAT) 
			+ ' and current time: ' + current_time.strftime(TIME_FORMAT)
			+ ' and delta: ' + str(time_stamp - current_time_stamp))
			if time_stamp - current_time_stamp <= 10:
				delta = TIME_UNIT * interval
				next_time = current_time + datetime.timedelta(seconds=delta)
				logging.info('Set next time: ' + next_time.strftime(TIME_FORMAT))
				update_next_time = "update sys_heartbeat_config set next_time = '" + next_time.strftime(TIME_FORMAT) + "' where id = '" + str(cid) + "'"
				cursor.execute(update_next_time)
				conn.commit()
			else:
				isCheck = False
				logging.info("Next time not reach")
		if isCheck:
			logging.info('Execute heartbeat')
			try: 
				if method == 'GET':
					res = doGet(url, data, is_auth)
				else:
					data_type = item[4]
					res = doPost(url, data, data_type, is_auth)
			except BaseException as e:
				msg = traceback.format_exc()
				sendEmail(u'心跳探测出错，服务链接：' + url + u'，参数：' + data + u"\n，异常信息：" + msg)
				logging.info('Heartbeat check error: ' + url + ' and data: ' + data + '\n' + msg)
				continue
			checkResponse(url, data, res)


#这个方法有待于扩展
def checkResponse(url, data, res):
	logging.info('Response: ' + res)
	jsonRes = json.loads(res)
	if 'returnCode' in jsonRes:
		if jsonRes['returnCode'] != '0':
			sendEmail(u'心跳测试失败，服务链接： ' + url + u'，参数：' + data + u'，错误码：' + jsonRes['returnCode'])
			logging.info('Heartbeat check failed: ' + url + ' and data: ' + data + ' for return code: ' + jsonRes['returnCode'])
			return
	if 'content' in jsonRes:
		content = jsonRes['content']
		if 'count' in content and content['count'] == 0:
			endEmail(u'心跳测试失败，服务链接：' + url + u'，参数：' + data + u'，报文信息：' + content)
			logging.info('Heartbeat check failed: ' + url + ' and data: ' + data + ' for content: ' + content)


def doGet(url, data, is_auth):
	url = unicode(url)
	if is_auth == 'true':
		session = getSession()
		headers = {'content-type': 'application/json', 'auth-token' : session}
	else:
		headers = {'content-type': 'application/json'}
	if data is not None and data != '':
		params = data.split('|')
		if len(params) > 0:
			url += '?'
		for param in params:
			kv = param.split(':')
			url += kv[0] + '='
			value = kv[1]
			if ZH_PATTERN.search(value):
				value = urllib2.quote(value.encode('utf-8'))
				url += value + '&'
			else:
				url += value + '&'
	req = urllib2.Request(url = url, headers = headers)
	res = urllib2.urlopen(req)
	res_msg = res.read()
	return res_msg

def doPost(url, data, data_type, is_auth):
	if is_auth == 'true':
		session = getSession()
		headers = {'content-type': 'application/json', 'auth-token' : session}
	else:
		headers = {'content-type': 'application/json'}
	if data_type == 1:
		req_data = json.dumps(data)
	else:
		req_data = data
	req = urllib2.Request(url = url, headers = headers, data = req_data)
	res = urllib2.urlopen(req)
	res_msg = res.read()
	return res_msg

def getSession():
	session_list = redis_client.smembers(SESSION_KEY)
	for session in session_list:
		return session

def sendEmail(content):
	if EMAIL_NOTIFY_SWITCH:
		msg = MIMEText(content,'plain','utf-8')
		msg['subject'] = Header(EMAIL_SUBJECT, 'utf-8')  
		msg['from'] = EMAIL_SENDER
		msg['to'] = EMAIL_RECEIVER
		smtp.sendmail(EMAIL_SENDER, EMAIL_RECEIVER, msg.as_string())

def initLogging():
    if not os.path.exists(LOG_DIR):
        os.makedirs(LOG_DIR)  # 创建路径

    LOG_FILE = 'heartbeat_' + datetime.datetime.now().strftime('%Y-%m-%d') + '.log'

    LOGGING = {
        'version': 1,
        'disable_existing_loggers': False,
        'formatters': {
            'simple': {
                'format': '%(asctime)s [%(name)s:%(lineno)d] [%(levelname)s]- %(message)s'
            },
            'standard': {
                'format': '%(asctime)s [%(threadName)s:%(thread)d] [%(name)s:%(lineno)d] [%(levelname)s]- %(message)s'
            },
        },

        'handlers': {
            'console': {
                'class': 'logging.StreamHandler',
                'level': 'DEBUG',
                'formatter': 'simple',
                'stream': 'ext://sys.stdout'
            },

            'default': {
                'class': 'logging.handlers.RotatingFileHandler',
                'level': 'INFO',
                'formatter': 'simple',
                'filename': os.path.join(LOG_DIR, LOG_FILE),
                'mode': 'w+',
                'maxBytes': 1024*1024*5,  # 5 MB
                'backupCount': 20,
                'encoding': 'utf8'
            },
        },

        'root': {
            'handlers': ['default'],
            'level': 'INFO',
            'propagate': False
        }
    }
    logging.config.dictConfig(LOGGING)

if __name__ == '__main__':
	initLogging()
   	main(sys.argv[1:])

