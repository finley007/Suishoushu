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
import smtplib  
from email.mime.text import MIMEText  
from email.header import Header 
import traceback

#LOG_DIR = '/Users/finley/Finley/workspace/java/Suishoushu/log'
LOG_DIR = '/home/suishoushu/log'
SESSION_KEY = 'session_map_111111'
EMAIL_SERVER = 'smtp.163.com'
EMAIL_SENDER = 'changyi_688@163.com'  
EMAIL_RECEIVER = 'finley007@163.com; 147699362@qq.com'  
EMAIL_SUBJECT = 'Suishoushu heartbeat check failed'  
EMAIL_SMTPSERVER = 'smtp.163.com'  
EMAIL_PASSWORD = 'changyi2017'  

conn = MySQLdb.connect('localhost', 'root', 'root', 'fi_dev', charset='utf8')
cursor = conn.cursor()
redis_client = redis.StrictRedis(host='localhost',port=6379)


smtp = smtplib.SMTP()  
smtp.connect(EMAIL_SERVER)  
smtp.login(EMAIL_SENDER, EMAIL_PASSWORD) 

def main(argv):
	logging.info('************************Start heartbeat check')
	getSession()
	cursor.execute('select * from sys_heartbeat_config')
	heartbeat_items = cursor.fetchall()
	for item in heartbeat_items:
		url = item[1]
		logging.info('-----------Check link: ' + url)
		method = item[2]
		data = item[3]
		is_auth = item[7]
		try: 
			if method == 'GET':
				res = doGet(url, data, is_auth)
			else:
				data_type = item[4]
				res = doPost(url, data, data_type, is_auth)
		except BaseException as e:
			msg = traceback.format_exc()
			sendEmail("Heartbeat check error: " + url + "\n" + msg)
			logging.info("Heartbeat check error: " + url + "\n" + msg)
			continue
		checkResponse(url, res)


#这个方法有待于扩展
def checkResponse(url, res):
	jsonRes = json.loads(res)
	if 'returnCode' in jsonRes:
		if jsonRes['returnCode'] != '0':
			sendEmail("Heartbeat check failed: " + url + " for return code: " + jsonRes['returnCode'])
			logging.info("Heartbeat check failed: " + url + " for return code: " + jsonRes['returnCode'])
			return
	if 'content' in jsonRes:
		content = jsonRes['content']
		if 'count' in content and content['count'] == 0:
			endEmail("Heartbeat check failed: " + url + " for content: " + content)
			logging.info("Heartbeat check failed: " + url + " for content: " + content)


def doGet(url, data, is_auth):
	if is_auth == 'true':
		session = getSession()
		headers = {'content-type': 'application/json', 'auth-token' : session}
	else:
		headers = {'content-type': 'application/json'}
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

