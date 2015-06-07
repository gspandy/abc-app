#!/bin/sh
PID_FILE=bks-rest.pid
# resolve links - $0 may be a softlink
PRG="$0"

while [ -h "$PRG" ]; do
  ls='ls -ld "$PRG"'
  link='expr "$ls" : '.*-> \(.*\)$''
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG='dirname "$PRG"'/"$link"
  fi
done

# Get standard environment variables
KBS_HOME="./"
PRGDIR='dirname "$PRG"'

# Only set CATALINA_HOME if not already set
[ -z "$KBS_HOME" ] && KBS_HOME=' cd "$PRGDIR" >/dev/null; pwd '

KBS_CP=.:$KBS_HOME/conf

export KBS_CP
#echo "KBS_HOME: $KBS_HOME"
	

for file in $(find $KBS_HOME/lib -name *jar | sort) ;
do
        KBS_CP=$KBS_CP:$file;
done;

#echo "KBS_CP: $KBS_CP"
export KBS_CP

shift
mv kbs-rest.log kbs-rest.`date +%Y-%m-%d-%H-%M` 2> /dev/null
java -Xms2048m -Xmx2048m -XX:NewRatio=3 -XX:SurvivorRatio=4 -XX:MaxTenuringThreshold=4 -XX:TargetSurvivorRatio=90 -XX:+PrintTenuringDistribution -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -cp $KBS_CP com.kingteller.bs.rest.setup.AppRestSetup > kbs-rest.log 2>&1 &
echo $! > $PID_FILE

exit 0;
