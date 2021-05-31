#!/usr/bin/env bash
n=1
while [ $n -le 10 ]
do
    echo `curl -s http://172.16.20.4:18083/sleep`
    let n++
done
