(ns leiningen.less
  (:require
   (leiningen.less
    [compiler :as compiler]
    [config :refer [config]]
    [nio :as nio])
   [leiningen.less.engine :as engine])
  (:import (leiningen.less LessError)))

(defn- report-error [^LessError error]
  (binding [*out* *err*]
    (println (.getMessage error))))

(defn- run-compiler
  "Run the lesscss compiler."
  [project {:keys [source-paths target-path] :as config}]
  (engine/with-engine "javascript"
    (compiler/initialise)
    (println "Compiling {less} css:")
    (let [units (nio/compilation-units source-paths target-path)
          compile (partial compiler/compile-project project units abort-on-error)]
      (println "Done."))))
