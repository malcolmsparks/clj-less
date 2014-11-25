(ns lein-less.less
  (:require
   [lein-less.less.compiler :as compiler]
   [lein-less.less.engine :as engine]
   [lein-less.less.nio :as nio])
  (:import (lein_less.less LessError)))

(defn- report-error [^LessError error]
  (binding [*out* *err*]
    (println (.getMessage error))))

(defn run-compiler
  "Run the lesscss compiler."
  [engine data]
  (let [{:keys [source-paths target-path]} (nio/adapt-paths data)]
    (engine/with-engine (name engine)
      (compiler/initialise)
      (println "Compiling {less} css:")
      (-> (nio/compilation-units source-paths target-path)
          (compiler/compile-project report-error))
      (println "Done."))))
