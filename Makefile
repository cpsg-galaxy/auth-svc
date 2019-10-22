## display this help message
help:
	@echo ''
	@echo 'Management commands for Auth Service:'
	@echo
	@echo 'Usage:'
	@echo '  ## Develop / Test Commands'
	@echo '  build           Build auth-svc service.'
	@echo '  test            Run tests on project.'
	@echo '  db-start        Start Mongo DB container.'
	@echo '  db-stop         Stop Mongo DB container.'
	@echo '  db-remove       Remove/Delete Mongo DB container.'
	@echo '  push            Building and Pushing Image to Registry'
	@echo ''

## build auth-svc
build:
	@bash ./scripts/build.sh

## build and push image into registry
push:
	@bash ./scripts/push.sh

## run test and capture code coverage metrics on project
test: .pretest
	@bash ./scripts/test.sh
	@$(MAKE) db-remove

## stop db container
db-stop:
	@if [ -f tmp/db_cnt_id ]; then \
		$(eval DB_CNT_ID=$(shell cat tmp/db_cnt_id 2>/dev/null)) \
		docker stop $(DB_CNT_ID) > /dev/null 2>&1 || : ; \
	fi

## start db container
db-start: db-stop
	@mkdir -p tmp
	@docker run --name mongo-test -d mongo > tmp/db_cnt_id

## remove db container
db-remove: db-stop
	@if [ -f tmp/db_cnt_id ]; then \
		$(eval DB_CNT_ID=$(shell cat tmp/db_cnt_id 2>/dev/null)) \
		docker rm -f $(DB_CNT_ID) > /dev/null 2>&1 || : ; \
		rm -f tmp/db_cnt_id ; \
	fi

## making sure db container is up and started
.pretest: db-start
