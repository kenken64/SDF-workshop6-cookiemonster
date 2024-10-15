SRC_DIR = src
OUT_DIR = classes
PORT_NO=3000
HOSTNAME=localhost
COOKIE_FILE_PATH=/home/kenneth/Projects/SDF-workshop-day6/cookie_file.txt
WORKSHOP_JAR = workshop6.jar

SOURCES = $(shell find $(SRC_DIR) -name '*.java')
JAVAC = javac
JFLAGS = -d $(OUT_DIR) -cp $(OUT_DIR)
JAVA = java

CLIENT_APP = workshop6.ClientApp
SERVER_APP = workshop6.ServerApp

all: ${OUT_DIR} compile

${OUT_DIR}:
	mkdir -p ${OUT_DIR}

compile: ${SOURCES}
	${JAVAC} ${} ${JFLAGS} ${SOURCES}

run-client: compile
	${JAVA} -cp ${OUT_DIR} ${CLIENT_APP} ${HOSTNAME}:${PORT_NO}  

run-server: compile
	${JAVA} -cp ${OUT_DIR} ${SERVER_APP} ${PORT_NO} ${COOKIE_FILE_PATH}

jar: compile
	jar cvf ${WORKSHOP_JAR} -C ${OUT_DIR} .

run-client-jar: jar
	${JAVA} -cp ${WORKSHOP_JAR} ${CLIENT_APP} ${HOSTNAME}:${PORT_NO}  

run-server-jar: jar
	${JAVA} -cp ${WORKSHOP_JAR} ${SERVER_APP} ${PORT_NO} ${COOKIE_FILE_PATH}

run-exception: jar
	${JAVA} -cp ${WORKSHOP_JAR} workshop6.ExceptionApp

gt:
	git add .
	git commit -m "update"
	git push origin main -u
	
clean:
	@rm -rf ${OUT_DIR}
	@rm -rf ${WORKSHOP_JAR}
	
