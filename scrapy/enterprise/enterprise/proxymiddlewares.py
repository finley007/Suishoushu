# -*- coding: utf-8 -*-
import random, base64
import logging

logger = logging.getLogger('ProxyMiddleware')

class ProxyMiddleware(object):

    proxyList = ['182.35.7.4:7889']

    def process_request(self, request, spider):
        # Set the location of the proxy
        proxy_address = random.choice(self.proxyList)
        logger.info("Use proxy -> " + proxy_address)
        request.meta['proxy'] = "http://" + proxy_address