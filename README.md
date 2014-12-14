# malcolmsparks/clj-lessc

This is a forked repo of [@montoux](https://github.com/montoux)/[lein-less](https://github.com/montoux/lein-less/)

Compile [Less](http://lesscss.org) to CSS.

## Usage

Add `[malcolmsparks/clj-lessc "1.7.3"]` to your `project.clj` dependencies:

```
  :dependencies [[malcolmsparks/clj-lessc "1.7.3"]]
```


```
(ns your-ns
  (:require [clj-lessc.less :refer (run-compiler)]))

  (run-compiler :javascript
                {:project-root "sample/example"
                 :source-path "your-less-file.less"
                 :target-path "your-css-file.css"}))

```

Being first argument the selected compiler, availables are:   ```:javascript :rhino :nashorn ```

## References

http://www.smashingmagazine.com/2013/03/12/customizing-bootstrap/
