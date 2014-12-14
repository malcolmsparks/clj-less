(ns lein-less.changes-checker-test
  (:require [clojure.java.io :as io]
            [clojure.test :refer :all]
            [lein-less.less :as less]
            [clojure.repl :refer (apropos dir doc find-doc pst source)])
  (:import [java.io File]))




(def directory (clojure.java.io/file "sample"))
(def files (file-seq directory))


(.isDirectory(first (file-seq (io/file "sample/example"))))


(less/is-synchronized? "sample/bootstrap" "sample/css-target/bootstrap.css")

#_( (assert (complement (less/synchronized? "sample/.DS_Store" "sample/example/project.clj")))
     (assert (less/synchronized? "sample/example/less/sample.less" "sample/example/project.clj"))

     (assert (complement  (less/is-synchronized? "sample/example" "sample/.DS_Store" )))
     (assert (complement (less/is-synchronized? "sample/example" "sample/example/project.clj"))))
