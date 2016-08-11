## 1.获取终端信息

+++
post:/terminal
*regionId#区域ID
*name#终端名称
*type#终端类型
*pageNumber#页码
*pageSize#每页数量
<<<
success
{
"pageNumber": 0,
  "pageSize": 10,
  "total": 9,
  "list": [
    {
      "id": "000000001147",
      "name": "ter1"
    },
    {
"id ": "000000001090",
"name ": "ter2"
    },
...
  ]
}

<<<
error
{
    "errorMsg ": "错误信息"
}
+++
