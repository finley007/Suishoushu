# -*- coding: utf-8 -*-
import random, base64
import logging
import uuid
import random
import sys
sys.path.append(r'../') 
from dbclient import DBClient
from config import Config

logger = logging.getLogger('DownloadMiddleware')

class DownloadMiddleware(object):

	RETRY_HTTP_CODES = [500, 503, 504, 400, 403, 404, 408]
	dbclient = DBClient()

	def process_response(self, request, response, spider):
		config = Config()
		if response.status in self.RETRY_HTTP_CODES:
			logger.info("Request: " + request.url + " failed for: " + str(response.status) + " and will be recorded in DB")
			record_id = uuid.uuid1()
			industry_code = request.meta['industry_code']
			provice_code = config.getCurrentProviceCode()
			updateSQL = "insert into ISSUE_URL values ('" + str(record_id)  + "', '" + request.url + "', '" + provice_code + "', '" + industry_code + "','0')"
			self.dbclient.updateDB(updateSQL)
		return response
