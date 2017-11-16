require 'rubygems'
require 'net/ssh'
require 'net/scp'

#gem install net-ssh

@hostname = "35.188.131.223"
@username = "rajsurabhi"
@cmd = "ls -al"
@password = "oddTulip@123"
begin
  system("wget -O ~/Downloads/calculator-unit-test-example-java-1.0-20171030.100532-2.jar http://10.71.160.27:8081/repository/nexus-snapshots/com/github/stokito/calculator-unit-test-example-java/1.0-SNAPSHOT/calculator-unit-test-example-java-1.0-20171030.100532-1.jar")
  Net::SCP.start("35.188.131.223", "rajsurabhi") do |scp|
    # upload a file to a remote server
    scp.upload! "/home/rajsurabhi/Downloads/calculator-unit-test-example-java-1.0-20171030.100532-2.jar", "/home/rajsurabhi"
  end
  ssh = Net::SSH.start(@hostname, @username,:host_key=>"ssh-rsa",:keys => [ "~/.ssh/rajutest.pub"], :forward_agent=>true)
  res = ssh.exec!(@cmd)
  ssh.exec!("sudo cp /home/rajsurabhi/calculator-unit-test-example-java-1.0-20171030.100532-2.jar /var/lib/tomcat8/webapps/")
  ssh.exec!("sudo service tomcat8 restart")
  ssh.close
  puts res
rescue Exception => e
  puts e.message
end

