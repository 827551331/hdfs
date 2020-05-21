@echo off

::启动项目包名
set application=../hdfs.jar

::启动项目环境
set active=dev

echo 项目包名：%application%；运行环境：%active%； 项目启动时间：%date% >> log.txt

::测试环境配置
if %active% == dev (
java -XX:+UseG1GC -XX:+PrintGC -Xms512m -Xmx512m -server  -Dproject.dir="$PROJECTDIR" -jar %application% --spring.profiles.active=%active%
)

::生产环境配置
if %active% == prod (
java -XX:+UseG1GC -XX:+PrintGC -Xms1024m -Xmx1024m -server -Dproject.dir="$PROJECTDIR" -jar %application% --spring.profiles.active=%active%
)
pause