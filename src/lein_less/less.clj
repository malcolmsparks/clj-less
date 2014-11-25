(ns lein-less.less
  (:require
   (lein-less.less
    [compiler :as compiler]
    [config :refer [config]]
    [nio :as nio])
   [lein-less.less.engine :as engine])
  (:import (lein_less.less LessError)))

(defn- report-error [^LessError error]
  (binding [*out* *err*]
    (println (.getMessage error))))

(defn run-compiler
  "Run the lesscss compiler."
  [project {:keys [source-paths target-path] :as config}]
  (engine/with-engine "javascript"
    (compiler/initialise)
    (println "Compiling {less} css:")
    (let [units (nio/compilation-units source-paths target-path)
          compile (partial compiler/compile-project project units report-error)]
      (println "Done."))))
