#!/usr/bin/env python

import sys

sys.path.append('gen-py')

from cse124 import Twitter
from cse124.ttypes import *

from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol

try:
  transport = TSocket.TSocket('localhost', 9090)

  # Buffering is critical. Raw sockets are very slow
  transport = TTransport.TBufferedTransport(transport)

  # Wrap in a protocol
  protocol = TBinaryProtocol.TBinaryProtocol(transport)

  # Create a client to use the protocol encoder
  client = Twitter.Client(protocol)

  # Connect!
  transport.open()

  client.ping()
  print 'ping()'

  client.createUser("dotcomXY")
  print 'Succefully created user dotcomXY'

  client.createUser("xcf")
  print 'Succefully created user xcf'

  client.createUser("ymq")
  print 'Succefully created user ymq'

  client.subscribe("dotcomXY", "xcf")
  print 'dotcomXY succefully subscribed to xcf account'

  client.unsubscribe("dotcomXY", "xcf")
  print 'dotcomXY succefully unsubscribed xcf account'

  client.subscribe("xcf", "dotcomXY")
  print 'xcf succefully subscribed to dotcomXY account'

  client.subscribe("xcf", "ymq")
  print 'xcf succefully subscribed to ymq account'

  client.post("dotcomXY", "1")
  print "dotcomXY succefully post a new tweet1"

  client.post("dotcomXY", "2")
  print "dotcomXY succefully post a new tweet2"

  client.post("dotcomXY", "3")
  print "dotcomXY succefully post a new tweet3"

  client.post("ymq", "4")
  print "ymq succefully post a new tweet11"

  client.post("ymq", "5")
  print "ymq succefully post a new tweet22"

  client.post("ymq", "6")
  print "ymq succefully post a new tweet33"

  client.post("dotcomXY", "7")
  print "dotcomXY succefully post a new tweet4"

  client.post("dotcomXY", "8")
  print "dotcomXY succefully post a new tweet5"

  client.post("ymq", "9")
  print "ymq succefully post a new tweet44"

  client.post("ymq", "10")
  print "ymq succefully post a new tweet55"

  res = client.readTweetsByUser("dotcomXY", 3);
  print res

  res = client.readTweetsBySubscription("xcf", 5);
  print res

  # Close!
  transport.close()

except Thrift.TException, tx:
  print '%s' % (tx.message)
