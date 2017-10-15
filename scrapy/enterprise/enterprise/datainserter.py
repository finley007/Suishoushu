# -*- coding: utf-8 -*-
'''
这个类是用来将数据文件导入数据库，
python datainserter.py -p ./output/ -f example
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
	path = ""
	file = ""
	dbclient = DBClient("fi_dev")
	try:
	  opts, args = getopt.getopt(argv,"hp:f:")
	except getopt.GetoptError:
	  print 'datainserter.py -p <url> -f <file>'
	  sys.exit(2)
	for opt, arg in opts:
	  if opt == '-h':
	     print 'datainserter.py -p <url> -f <file>'
	     sys.exit()
	  elif opt in ("-p"):
	     path = arg
	  elif opt in ("-f"):
	     file = arg

	if file != "":
		logging.info("************Start to insert data and file: " + file)
		solveFile(file, dbclient)
	else:
		logging.info("************Start to insert data and path: " + path)
		for parent,dirnames,filenames in os.walk(path):
			for filename in filenames:
				solveFile(filename, dbclient)

def solveFile(file, dbclient):
	logging.info("************Solve file: " + file)
	for line in open(file):
		logging.info(line)
		data = line.split("|")
		if len(data) <= 1 or data[3] == '未公开':
			logging.info("************Invalid record:" + line)
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
	insert = "insert into inc_enterprise (credit_code,name,legal_person,reg_capital,reg_authority,address,phone,biz_reg_num,org_code,taxpayer_code,industry,area, create_by"
	values = " values ('" + data[3] + "','" + data[4] + "','" + data[5] + "','" + str(regCapital) + "','" + data[9] + "','" + data[10] + "','" + data[11] + "','" + data[12] + "','" + data[13] + "','" + data[14] + "','" + data[15].replace("\n", "") + "','" + data[0] + "','system'"
	if bizPeriodStart != '':
		insert = insert + ",biz_period_start"
		values = values + ",'" + bizPeriodStart + "'"
	if bizPeriodEnd != '':
		insert = insert + ",biz_period_end"
		values = values + ",'" + bizPeriodEnd + "'"
	if establishDate != '':
		insert = insert + ",establish_date"
		values = values + ",'" + establishDate + "'"
	insert = insert + ")"
	values = values + ")"
	return insert + values

def createUpdateSql(data, bizPeriodStart, bizPeriodEnd, regCapital, establishDate):
	update = "update inc_enterprise t set t.legal_person = '" + data[5] + "', t.reg_capital = '" + str(regCapital) + "', t.reg_authority = '" + data[9] + "', t.address = '" + data[10] + "', t.phone = '" + data[11] + "', t.biz_reg_num = '" + data[12] + "', t.org_code = '" + data[13] + "', t.taxpayer_code = '" + data[14] + "', t.industry = '" + data[15] + "', t.area = '" + data[0]
	if bizPeriodStart != '':
		update = update + "', t.biz_period_start = '" + bizPeriodStart
	if bizPeriodEnd != '':
		update = update + "', t.biz_period_end = '" + bizPeriodEnd
	if establishDate != '':
		update = update + "', t.establish_date = '" + establishDate
	update = update + "' where t.credit_code = '" + data[3] + "'"
	return update

if __name__ == "__main__":
   main(sys.argv[1:])







