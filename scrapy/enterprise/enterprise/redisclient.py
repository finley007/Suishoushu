# -*- coding:utf-8 -*-
import redis

class RadisClient:

	def __init__(self):
		self.r = redis.StrictRedis(host='localhost',port=6379)

   	def set(self, key, value):
   		self.r.set(key, value) 

   	def get(self, key):
   		return self.r.get(key)
