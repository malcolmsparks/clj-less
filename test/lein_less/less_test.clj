(ns lein-less.less-test
  (:require [lein-less.less :refer (run-compiler)]
            [lein-less.less.config :refer (config)]
            [clojure.test :refer :all]
            [clojure.repl :refer (apropos dir doc find-doc pst source)]))


(deftest test-compile-simple
  (testing "... "
    (let [data {:root "sample/example"
                :less {:source-paths ["resources" ]
                       :target-path "css"}}]
      (run-compiler (config data))
      )

    )
  )
(config {:root "sample/example"
                :less {:source-paths ["resources"]
                       :target-path "resources"}})
