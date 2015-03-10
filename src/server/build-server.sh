#!/bin/bash

thrift --gen java ../thrift/Twitter.thrift
ant
