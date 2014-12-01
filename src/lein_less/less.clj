(ns lein-less.less
  (:require
   [lein-less.less.compiler :as compiler]
   [lein-less.less.engine :as engine]
   [lein-less.less.nio :as nio]
   [clojure.java.io :as io])
  (:import [lein_less.less LessError]
           [java.io File]))

(defn- report-error [^LessError error]
  (binding [*out* *err*]
    (println (.getMessage error))))


(defn up-to-date?
  "returns true if src is up to date with long last-modification"
  [^File src ^Long last-modification]
  (>= (.lastModified (io/file src)) last-modification))

(defn synchronized?
  [^File target ^File source]
  (up-to-date? source (.lastModified (io/file target))))


(defn synchronized-folder-source?
  "return the more recent time modification (greater lastModified value) of folder files.
  Returns long type"
  [source-folder time-target]
  (loop [d (next (file-seq source-folder))]
    (let [f (first d)]
      (if (.isDirectory f)
        (recur (next (file-seq  f)))
        (if (up-to-date? f time-target)
          (if (next d)
            (recur (next d))
            true)
          false)))))

(defn is-synchronized? [src-folder target-path]
  (synchronized-folder-source? (io/file src-folder) (.lastModified (io/file target-path))))


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
