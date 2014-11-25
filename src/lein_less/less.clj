(ns lein-less.less
  (:require
   (lein-less.less
    [compiler :as compiler]
    [nio :as nio])
   [lein-less.less.engine :as engine])
  (:import (lein_less.less LessError)))

(defn- report-error [^LessError error]
  (binding [*out* *err*]
    (println (.getMessage error))))


;;convert to schema
;;  (when (nil? (:less data))
;;    (assert false "ERROR: no :less entry found in project definition.")

(defn run-compiler
  "Run the lesscss compiler."
  [data]
  (let [{:keys [source-paths target-path]} (nio/adapt-paths (:less data))]
    (engine/with-engine (:engine data)
      (compiler/initialise)
      (println "Compiling {less} css:")
      (-> (nio/compilation-units source-paths target-path)
          (compiler/compile-project report-error))
      (println "Done."))))
