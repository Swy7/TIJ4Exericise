import json
import requests
from bs4 import BeautifulSoup

def getAllUrl(headers):
  print("开始请求")
  r = requests.get(url,headers=headers)
  print("开始解析")
  soup = BeautifulSoup(r.text,'html.parser')
  leftDiv = soup.find("div",align="left")
  td = leftDiv.find_all("td",valign = "top")
  res = {}
  for i in range(len(td)):
    if i%2 == 0:
      k = td[i].getText(strip=True)
      v = []
      alist = td[i+1].find_all("a")
      for j in alist:
        v.append(j.get("href"))
      res[k] = v
  with open('allUrl.txt',"w") as f:
    f.write(json.dumps(res))
  print("解析完成")


if __name__ == '__main__':
  url = 'http://greggordon.org/java/tij4/solutions.htm'
  headers = {'Host':"greggordon.org",
           "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
           "Accept-Encoding": "gzip, deflate",
           "Accept-Language": "zh-CN,zh;q=0.9",
           "Connection": "keep-alive",
           'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36'
           }
  getAllUrl(url,headers)
  