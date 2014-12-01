# malcolmsparks/lein-less

This is a forked repo of [@montoux](https://github.com/montoux)/[lein-less](https://github.com/montoux/lein-less/)


Compile Less CSS files (see [lesscss.org](http://lesscss.org)) using the `less.js` compiler running on the JVM. `lein-less` is ideal for compiling less to CSS files during automated builds.


## Usage

Add `[malcolmsparks/lein-less "1.7.3"]` to your `project.clj` dependencies:

```
  :dependencies [[malcolmsparks/lein-less "1.7.3"]]
```


```
(ns your-ns
  (:require [lein-less.less :refer (run-compiler)]))

  (run-compiler  :javascript
                 {:project-root "sample/example"
                  :source-path "your-less-file.less"
                  :target-path "your-css-file.css"}))

```


Being first argument the selected compiler, availables are:   ```:javascript :rhino :nashorn ```




