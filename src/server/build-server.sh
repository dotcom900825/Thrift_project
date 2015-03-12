#!/bin/bash

DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
thrift -o "$DIR" --gen java "$DIR"/../thrift/Twitter.thrift
ant -buildfile  "$DIR"/build.xml