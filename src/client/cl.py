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

  client.post("dotcomXY", "test1")
  print "dotcomXY succefully post a new tweet1"

  client.post("dotcomXY", "test2")
  print "dotcomXY succefully post a new tweet2"

  client.post("dotcomXY", "test3")
  print "dotcomXY succefully post a new tweet3"

  client.post("ymq", "test1q")
  print "ymq succefully post a new tweet11"

  client.post("ymq", "test22")
  print "ymq succefully post a new tweet22"

  client.post("ymq", "test33")
  print "ymq succefully post a new tweet33"

  client.post("dotcomXY", "test4")
  print "dotcomXY succefully post a new tweet4"

  client.post("dotcomXY", "test5")
  print "dotcomXY succefully post a new tweet5"

  client.post("ymq", "test44")
  print "ymq succefully post a new tweet44"

  client.post("ymq", "test55")
  print "ymq succefully post a new tweet55"

  client.readTweetsByUser("dotcomXY", 3);
  print "read dotcomXY's 3 posts"

  client.readTweetsBySubscription("xcf", 5);
  print "xcf read 5 subscription posts"

  # Close!
  transport.close()

except Thrift.TException, tx:
  print '%s' % (tx.message)
