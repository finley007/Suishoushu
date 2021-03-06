# -*- coding:utf-8 -*-
import MySQLdb

class DBClient:

   def __init__(self, db = "crawler"):
      self.db = db
      self.connect = MySQLdb.connect("localhost", "root", "root", self.db, charset='utf8')
      self.cursor = self.connect.cursor()

   def updateDB(self, sql):
      try:
         self.cursor.execute(sql)
         self.connect.commit()
      except MySQLdb.Error, e:
         print e
         self.connect.rollback()
         return -1
      return 0

   def queryDB(self, sql):
		self.cursor.execute(sql)
		return self.cursor.fetchall()

   def getRecord(self, prov, ind, page):
		sql = "select id from crawler_record t where t.provice_code = '" + prov + "' and t.industry_code = '" + ind + "' and t.page_code = '" + page + "'"
		return self.queryDB(sql)

   def getIssueUrlNum(self, prov):
      sql = "select count(*) from issue_url t where t.status = 0 and t.provice_code = '" + prov + "'"
      return self.queryDB(sql)