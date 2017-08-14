# -*- coding: utf-8 -*-
'''
这个类是用来将数据文件导入数据库
'''
import requests
import json
import sys, getopt
import os
import logging
import re
sys.path.append(r'./') 
from dbclient import DBClient

logging.basicConfig(level=logging.INFO,
                format='%(asctime)s %(filename)s[line:%(lineno)d] %(levelname)s %(message)s',
                datefmt='%a, %d %b %Y %H:%M:%S',
                filename='insert.log',
                filemode='w')

def main(argv):
	src_url = ""
	file = ""
	dbclient = DBClient("fi_dev")
	try:
	  opts, args = getopt.getopt(argv,"hu:f:")
	except getopt.GetoptError:
	  print 'datainserter.py -p <url> -f <file>'
	  sys.exit(2)
	for opt, arg in opts:
	  if opt == '-h':
	     print 'datainserter.py -p <url> -f <file>'
	     sys.exit()
	  elif opt in ("-p"):
	     src_url = arg
	  elif opt in ("-f"):
	     file = arg

	if file != "":
		logging.info("************Start to insert data and file: " + file)
		solveFile(file, dbclient)
	else:
		logging.info("************Start to insert data and path: " + path)
		for parent,dirnames,filenames in os.walk(src_url):
			for filename in filenames:
				solveFile(path, dbclient)

def solveFile(file, dbclient):
	logging.info("************Solve file: " + file)
	for line in open(file):
		logging.info(line)
		data = line.split("|")
		if len(data) <= 1:
			logging.info("************Error record:" + line)
			continue
		regCapital = getDigital(data[6])
		bizPeriodStart = ""
		bizPeriodEnd = ""
		bizPeriod = findAll(r'\d{4}-\d{2}-\d{2}', data[8])
		if len(bizPeriod) > 1:
			bizPeriodStart = bizPeriod[0]
		if len(bizPeriod) == 2:
			bizPeriodEnd = bizPeriod[1]
		establishDate = ""
		if len(findAll(r'\d{4}-\d{2}-\d{2}', data[7])) > 1:
			 establishDate = data[7]
		insertSQL = createInsertSql(data, bizPeriodStart, bizPeriodEnd, regCapital, establishDate)
		logging.info("Insert SQL: " + insertSQL)
		result = dbclient.updateDB(insertSQL)
		if result == -1:
			updateSQL =  createUpdateSql(data, bizPeriodStart, bizPeriodEnd, regCapital, establishDate)
			logging.info("Update SQL: " + updateSQL)
			result = dbclient.updateDB(updateSQL)
			if result == -1:
				logging.info("************Error for enterprise " + data[3] + "|" + data[4])

def getDigital(text):
	result = re.match(r'[\d.]*', text)
	if result.group(0) != "":
		return float(result.group(0))
	else:
		return 0

def findAll(regex, text):
	pattern = re.compile(regex)
	return pattern.findall(text)

def createInsertSql(data, bizPeriodStart, bizPeriodEnd, regCapital, establishDate):
	insert = "insert into ENTERPRISE (CREDIT_CODE,NAME,LEGAL_PERSON,REG_CAPITAL,REG_AUTHORITY,ADDRESS,PHONE,BIZ_REG_NUM,ORG_CODE,TAXPAYER_CODE,INDUSTRY,AREA, CREATE_BY"
	values = " values ('" + data[3] + "','" + data[4] + "','" + data[5] + "','" + str(regCapital) + "','" + data[9] + "','" + data[10] + "','" + data[11] + "','" + data[12] + "','" + data[13] + "','" + data[14] + "','" + data[15].replace("\n", "") + "','" + data[0] + "','system'"
	if bizPeriodStart != '':
		insert = insert + ",BIZ_PERIOD_START"
		values = values + ",'" + bizPeriodStart + "'"
	if bizPeriodEnd != '':
		insert = insert + ",BIZ_PERIOD_END"
		values = values + ",'" + bizPeriodEnd + "'"
	if establishDate != '':
		insert = insert + ",ESTABLISH_DATE"
		values = values + ",'" + establishDate + "'"
	insert = insert + ")"
	values = values + ")"
	return insert + values

def createUpdateSql(data, bizPeriodStart, bizPeriodEnd, regCapital, establishDate):
	update = "update ENTERPRISE t set t.LEGAL_PERSON = '" + data[5] + "', t.REG_CAPITAL = '" + str(regCapital) + "', t.REG_AUTHORITY = '" + data[9] + "', t.ADDRESS = '" + data[10] + "', t.PHONE = '" + data[11] + "', t.BIZ_REG_NUM = '" + data[12] + "', t.ORG_CODE = '" + data[13] + "', t.TAXPAYER_CODE = '" + data[14] + "', t.INDUSTRY = '" + data[15] + "', t.AREA = '" + data[0]
	if bizPeriodStart != '':
		update = update + "', t.BIZ_PERIOD_START = '" + bizPeriodStart
	if bizPeriodEnd != '':
		update = update + "', t.BIZ_PERIOD_END = '" + bizPeriodEnd
	if establishDate != '':
		update = update + "', t.ESTABLISH_DATE = '" + establishDate
	update = update + "' where t.CREDIT_CODE = '" + data[3] + "'"
	return update

if __name__ == "__main__":
   main(sys.argv[1:])







