#!/bin/bash
echo "Port 80 to 8080"
sudo iptables -t nat -I PREROUTING -p tcp --dport 80 -j REDIRECT --to-port 8080