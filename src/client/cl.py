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

  client.subscribe("dotcomXY", "xcf")
  print 'dotcomXY succefully subscribed to xcf account'

  client.unsubscribe("dotcomXY", "xcf")
  print 'dotcomXY succefully unsubscribed xcf account'

  client.post("dotcomXY", "test")
  print "dotcomXY succefully post a new tweet"

  # Close!
  transport.close()

except Thrift.TException, tx:
  print '%s' % (tx.message)
