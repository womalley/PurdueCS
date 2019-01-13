echo exit | sqlplus womalley@csora/caddy1990 @drop.sql >/dev/null
echo exit | sqlplus womalley@csora/caddy1990 @create.sql >/dev/null
echo exit | sqlplus womalley@csora/caddy1990 @init.sql >/dev/null

javac -cp .:ojdbc8.jar Project3.java
java -cp .:ojdbc8.jar Project3 input.txt output.txt
