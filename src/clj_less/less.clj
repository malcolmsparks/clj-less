(ns clj-less.less
  (:require
   [clj-less.compiler :as compiler]
   [clj-less.engine :as engine]
   [clj-less.nio :as nio]
   [clojure.java.io :as io]
   [clojure.string :as st])
  (:import
   (clj_less LessError)
   (java.io File)))

(defn- report-error [^LessError error]
  (binding [*out* *err*]
    (println (.getMessage error))))

(defn adapt-path [project-root path]
  (let [^Path root (nio/as-path project-root)]
    (assert (nio/exists? root))
    (->> path
         (nio/resolve root)
         (nio/absolute))))

(defn check-file-extension [path extension]
  (let [base (st/split path #"\.")]
    (when (> (count base ) 1)
      (= (last (st/split path #"\.")) (name extension)))))

(defn run-compiler
  "Run the lesscss compiler."
  [engine {:keys [project-root source-path target-path] :as data}]

  (let [target-path (adapt-path project-root target-path)]
    (do
      (println "Compiling {less} css ...")
      (println "Compilation unit is" {:src source-path :dst target-path})
      (engine/with-engine (name engine)
        (compiler/initialise)
        (-> [{:src source-path :dst target-path}]
            (compiler/compile-project report-error))
        (println "Compilation done!")))))
