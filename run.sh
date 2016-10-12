inputFile=./inputfiles/debug.csv

if [ -n "$1" ] ; then
    inputFile=$1
fi

if [ "$1" == "--help" ] || [ "$1" == "-h" ]; then
  echo "\nUsage: ./run <command>\n"
  echo "Where <command> is:"
  echo "\tinputfile (default = ./inputfiles/debug.csv)"
  exit
fi

./compile.sh
./unittest.sh

echo "\n------ OUTPUT ------"
java -cp ./bin ltu.Main $inputFile
echo "\n"

