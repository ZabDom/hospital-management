build:
	mvn clean package install -Dmaven.test.skip=true
dev:
	docker-compose up --build
run:
	docker-compose up --build -d
stop:
	docker-compose down
clear:
	docker-compose down --rmi all --remove-orphans --volumes