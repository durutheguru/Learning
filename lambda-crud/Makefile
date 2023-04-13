
COMMANDS = mvn clean package && \
           	mvn dependency:copy-dependencies -DexcludeScope=provided && \
           	rsync -av ./target/classes/ $(ARTIFACTS_DIR)/

# cp -rf --exclude='*.properties' ./target/classes/* $(ARTIFACTS_DIR)/
# rsync -av --exclude='*.properties' ./target/classes/ $(ARTIFACTS_DIR)/

build-SavePublisherHandlerFunction:
	$(COMMANDS)

build-FindPublishersHandlerFunction:
	$(COMMANDS)

build-SaveBookHandlerFunction:
	$(COMMANDS)

build-FindBooksHandlerFunction:
	$(COMMANDS)


all: \
	build-SavePublisherHandlerFunction \
	build-FindPublishersHandlerFunction \
	build-SaveBookHandlerFunction \
	build-FindBooksHandlerFunction \
