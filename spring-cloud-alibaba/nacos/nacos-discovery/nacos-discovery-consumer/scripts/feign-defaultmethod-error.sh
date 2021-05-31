#!/usr/bin/env bash
n=1
while [ $n -le 10 ]
do
  echo `curl http://172.16.20.4:18083/fegin-divide1?a=1`
  let n++
done
