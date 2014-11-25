(ns lein-less.less.config-test
  (:require [lein-less.less.config :refer :all]
            [clojure.test :refer :all]))


(config  {:root "sample/error"
          :less {:source-paths ["resources"]
                 :target-path "compiled"}})
