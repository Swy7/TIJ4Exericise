import os
import re
import json
import requests
from bs4 import BeautifulSoup

def validateTitle(title):
    rstr = r"[\/\\\:\*\?\"\<\>\|]"  # '/ \ : * ? " < > |'
    new_title = re.sub(rstr, "", title)  # 替换为下划线
    return new_title

def getUrlContent(host,headers):
  with open('allUrl.txt',"r") as f:
    allUrl = f.read()
  allUrl = json.loads(allUrl)
  cwd = os.getcwd()
  for k,v in allUrl.items():
    k = validateTitle(k)
    path = os.path.join(cwd,k)
    print("开始文件夹"+k)
    if not os.path.exists(path):
      os.mkdir(path)
    for i in v:
      if i:
        *_,fileName = i.split('/')
        url = host+i
        filePath = os.path.join(path,fileName)
        if not os.path.isfile(filePath):
          print("开始请求"+fileName)
          r = requests.get(url,headers=headers)
          print("开始写入"+fileName)
          with open(filePath,'w') as f:
            f.write(r.text)
        else:
          print(filePath+"文件存在")

if __name__ == '__main__':
  host = "http://greggordon.org/"
  headers = {'Host':"greggordon.org",
           "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
           "Accept-Encoding": "gzip, deflate",
           "Accept-Language": "zh-CN,zh;q=0.9",
           "Connection": "keep-alive",
           'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36'
           }
  getUrlContent(host,headers)