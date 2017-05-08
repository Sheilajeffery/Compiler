java -jar ./lib/jflex-1.6.0.jar -d ./src/ --nobak \
        ./src/lexer.flex
java -cp ./lib/ -jar ./lib/java-cup-11b.jar -destdir ./src/ \
        -parser Parser -expect 17 ./src/parser.cup
javac -cp ./lib/java-cup-11b-runtime.jar -g -sourcepath ./src/ \
        -d ./bin/ ./src/*.java
java -cp ./lib/java-cup-11b-runtime.jar:./bin/ Main src/input.txt
