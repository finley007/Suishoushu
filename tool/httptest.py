# -*- coding: utf-8 -*-

import urllib
import urllib2

content = '易事特'
url = 'https://www.suishoushu.com/suishoushu/enterprise/heartbeat?key=' + urllib2.quote(content) + '&api=qcc'
req = urllib2.Request(url = url)
res = urllib2.urlopen(req)
res_msg = res.read()
print res_msg