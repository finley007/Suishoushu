# -*- coding:utf-8 -*-
import MySQLdb

class DBClient:

	def __init__(self):
		self.db = MySQLdb.connect("localhost", "root", "root", "crawler", charset='utf8')
		self.cursor = self.db.cursor()

   	def updateDB(self, sql):
   		try:
   			self.cursor.execute(sql)
   			self.db.commit()
   		except MySQLdb.Error, e:
   			print e
   			self.db.rollback()

   	def queryDB(self, sql):
   		self.cursor.execute(sql)
   		return self.cursor.fetchall()

   	def getRecord(self, prov, ind, page):
   		sql = "select id from crawler_record t where t.provice_code = '" + prov + "' and t.industry_code = '" + ind + "' and t.page_code = '" + page + "'"
   		return self.queryDB(sql)