#!/bin/bash
set -ex
gem install bundler
wget --header="Authorization: token ${GIT_TOKEN}" https://github.com/${GIT_LOGIN}/http_server_spec/archive/master.tar.gz
tar -xzvf master.tar.gz
cd http_server_spec-master && bundle install
bundle exec spinach --tags @simple-get,@simple-head,@not-found,@simple-post,@not-allowed,@simple-redirect