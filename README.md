# confusion_matrix

Implements metrics from [Confusion Matrix](https://en.wikipedia.org/wiki/Confusion_matrix)

## Usage

``` shell
git clone https://github.com/chief/confusion_matrix

cd confusion_matrix

lein run -f <path>
```

## File format

File should be .csv. Each row must have 2 values, the first one is the actual
observed category and the last is the predicted category. For example a valid
file could be the following:

``` csv
cat,cat
cat,cat
cat,cat
cat,dog
cat,dog
cat,cat
cat,dog
cat,cat
dog,dog
dog,dog
dog,dog
dog,cat
dog,cat
dog,rabbit
rabbit,rabbit
rabbit,rabbit
rabbit,rabbit
rabbit,rabbit
rabbit,rabbit
rabbit,rabbit
rabbit,rabbit
rabbit,rabbit
rabbit,rabbit
rabbit,rabbit
rabbit,rabbit
rabbit,dog
rabbit,dog
```

## License

Distributed under the Eclipse Public License, the same as Clojure.
