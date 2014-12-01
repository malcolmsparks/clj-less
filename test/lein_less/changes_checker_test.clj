(ns lein-less.changes-checker-test
  (:require [clojure.java.io :as io]
            [clojure.test :refer :all]
            [clojure.repl :refer (apropos dir doc find-doc pst source)])
  (:import [java.io File]))


(defn up-to-date?
  "returns true if targer has same last modified date as source"
  [^File src ^Long last-modification]
  ;;  (println [(.lastModified (io/file source)) (.lastModified (io/file target))])
  (>= (.lastModified (io/file src)) last-modification))

(defn synchronized?
  "returns true if targer has same last modified date as source"
  [^File target ^File source]
  ;;  (println [(.lastModified (io/file source)) (.lastModified (io/file target))])
  (up-to-date? source (.lastModified (io/file target))))


(def directory (clojure.java.io/file "sample"))
(def files (file-seq directory))


(.isDirectory(first (file-seq (io/file "sample/example"))))

(assert (complement (synchronized? "sample/.DS_Store" "sample/example/project.clj")))
(assert (synchronized? "sample/example/less/sample.less" "sample/example/project.clj"))


(defn synchronized-folder-source?
  "return the more recent time modification (greater lastModified value) of folder files.
  Returns long type"
  [source-folder time-target]
  (loop [d (next (file-seq source-folder))]
    (let [f (first d)]
      (if (.isDirectory f)
        (do
          (println f)
          (recur (next (file-seq  f))))
        (if (up-to-date? f time-target)
          (if (next d)
            (recur (next d))
            true)
          false)))))

(defn is-synchronized? [src-folder target-path]
  (synchronized-folder-source? (io/file src-folder) (.lastModified (io/file target-path))))

(is-synchronized? "sample/example" "sample/.DS_Store" )
(is-synchronized? "sample/example" "sample/example/project.clj")
