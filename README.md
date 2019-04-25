# Scrawler
Fake web crawler written in Scala. Written as an experiment in 2016, when I was still learning Scala. See [#3](https://github.com/adamkasztenny/Scrawler/issues/3) for a description of the many different things that need to be refactored in this project.

# Usage
Either do
```
sbt "run config.json"
```

or

```
sbt assembly
java -jar /home/adam/scrawler/target/scala-2.12/scrawler-assembly-1.0.0.jar config.json
```
