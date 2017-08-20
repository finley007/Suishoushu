# -*- coding:utf-8 -*-

class Config:

   current_provice_code = "snx"
   default_industry_code = "oc01"
   default_page_code = ""
   allow_domains = [current_provice_code + ".tianyancha.com", "www.tianyancha.com"]
   start_urls = "https://" + current_provice_code + ".tianyancha.com/search/" + default_industry_code
   root_urls = "https://" + current_provice_code + ".tianyancha.com/search/"
   step = 6

   def getCurrentProviceCode(self):
      return self.current_provice_code

   def getDefaultIndustryCode(self):
      return self.default_industry_code

   def getDefaultPageCode(self):
      return self.default_page_code

   def getAllowDomains(self):
      return self.allow_domains

   def getStartUrls(self):
      return self.start_urls

   def getRootUrls(self):
      return self.root_urls

   def getStep(self):
      return self.step