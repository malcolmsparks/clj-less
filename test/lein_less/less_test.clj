(ns lein-less.less-test
  (:require [lein-less.less :refer (run-compiler)]
            [clojure.test :refer :all]
            [clojure.repl :refer (apropos dir doc find-doc pst source)]))


(deftest test-compile-simple
  (testing " run-compiler "
    (run-compiler {:root "sample/example"
                   :less {:source-paths ["resources" ]
                          :target-path "css"}}))
  )
