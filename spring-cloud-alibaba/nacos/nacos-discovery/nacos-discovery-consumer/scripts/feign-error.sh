#!/usr/bin/env bash
n=1
while [ $n -le 10 ]
do
  echo `curl -s http://172.16.20.4:18083/fegin-divide?a=1\&b=0`
  let n++
done
