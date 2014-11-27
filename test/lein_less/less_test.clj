(ns lein-less.less-test
  (:require [lein-less.less :refer (run-compiler)]
            [clojure.test :refer :all]
            [clojure.repl :refer (apropos dir doc find-doc pst source)]))

;; todo: become it to unit test
(deftest test-compile-simple
  (testing " run-compiler "
    (run-compiler  :javascript
                   {:project-root "sample/example"
                    :source-paths ["resources"]
                    :target-path "css"})))

;; using a source dir and a target dir
(run-compiler  :javascript
                   {:project-root "sample/example"
                    :source-paths ["less"]
                    :target-path "css"})

;; using a source file and a target file only works with one source
(run-compiler  :javascript
                   {:project-root "sample/bootstrap"
                    :source-paths ["less/bootstrap.less"]
                    :target-path "../css-target/bootstrap.css"})
