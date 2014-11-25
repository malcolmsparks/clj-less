(ns lein-less.less-test
  (:require [lein-less.less :refer (run-compiler)]
            [lein-less.less.nio :refer (adapt-paths)]
            [clojure.test :refer :all]
            [clojure.repl :refer (apropos dir doc find-doc pst source)]))

;; todo: become it to unit test
(deftest test-compile-simple
  (testing " run-compiler "
    (run-compiler {:engine "javascript"
                   :less {:root "sample/example"
                          :source-paths ["resources"]
                          :target-path "css"}})
))



#_(adapt-paths {:less {:root "sample/error"
                     :source-paths ["resources"]
                     :target-path "compiled"}})
