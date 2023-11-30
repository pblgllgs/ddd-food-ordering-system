start: zookeeper_start kafka_start
	@echo Zookeeper and Kafka starting...

stop: zookeeper_stop kafka_stop init_data_delete
	@echo Stop Zookeeper and Kafka cluster

init:
	@echo Data loader
	docker compose -f .\common.yml -f .\init_kafka.yml up

zookeeper_start:
	@echo Zookeerper starting
	docker compose -f .\common.yml -f .\zookeeper.yml up -d

kafka_start:
	@echo Kafka cluster starting
	docker compose -f .\common.yml -f .\kafka_cluster.yml up -d


zookeeper_stop:
	@echo Cerrando Zookeeper
	docker compose -f .\common.yml -f .\zookeeper.yml down

kafka_stop:
	@echo Cerrando Kafka
	docker compose -f .\common.yml -f .\kafka_cluster.yml down

init_data:
	@echo Data loader
	docker compose -f .\common.yml -f .\init_kafka.yml up

init_data_delete:
	@echo Data loader
	docker compose -f .\common.yml -f .\init_kafka.yml down