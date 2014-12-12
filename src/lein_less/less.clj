(ns lein-less.less
  (:require
   [lein-less.less.compiler :as compiler]
   [lein-less.less.engine :as engine]
   [lein-less.less.nio :as nio]
   [clojure.java.io :as io]
   [clojure.string :as st])

  (:import [lein_less.less LessError]
           [java.io File]))

(defn- report-error [^LessError error]
  (binding [*out* *err*]
    (println (.getMessage error))))

(defn up-to-date?
  "returns true if src is up to date with long last-modification"
  [^File src ^Long last-modification]
;;  (println [(.lastModified (io/file src)) last-modification])
  (<= (.lastModified (io/file src)) last-modification))

(defn synchronized?
  [^File target ^File source]
  (up-to-date? source (.lastModified (io/file target))))


(defn synchronized-folder-source?
  "return the more recent time modification (greater lastModified value) of folder files.
  Returns long type"
  [source-folder time-target]
  (loop [d (next (file-seq source-folder))]
    (if-let [f (first d)]
      (if (.isDirectory f)
        (recur (next (file-seq f)))
        (if (up-to-date? f time-target)
          (if (next d)
            (recur (next d))
            true)
          false))
      true)))

(defn synchronized-folder-source?-shortcut [source-folder time-target]
  (up-to-date? source-folder time-target))

(defn is-synchronized? [src-folder target-path]
  (synchronized-folder-source?-shortcut (io/file src-folder) (.lastModified (io/file target-path))))


(defn adapt-paths [{:keys [project-root source-path target-path] :as less-data}]
  (let [^Path root (nio/as-path project-root)]
    (assert (nio/exists? root))
    {:source-path (->> source-path
                       (nio/resolve root)
                       (nio/absolute))
     :target-path (->> target-path
                       (nio/resolve root)
                       (nio/absolute))}))


(defn check-file-extension [path extension]
  (let [base (st/split path #"\.")]
    (when (> (count base ) 1)
      (= (last (st/split path #"\.")) (name extension)))))

(defn run-compiler
  "Run the lesscss compiler."
  [engine {:keys [project-root source-path target-path] :as data}]

  (assert (check-file-extension source-path :less) "your source path has to be a .less file path")

  (assert (check-file-extension target-path :css) "your target path has to be a .css file path ")

  (let [{:keys [source-path target-path]} (adapt-paths data)]
    (assert (nio/exists? source-path) "your source file doesn't exists")
    (if (is-synchronized? project-root (str target-path))
      (println (format "Less file %s is synchronized with target compilation %s, then avoiding compilation ..." (:source-path data) (:target-path data)))
      (do
        (println "Compiling {less} css ...")
        (engine/with-engine (name engine)
          (compiler/initialise)
          (-> (nio/compilation-units source-path target-path)
              (compiler/compile-project report-error))
          (println "Compilation done!"))))))
