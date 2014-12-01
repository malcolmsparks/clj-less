(ns lein-less.changes-checker-test
  (:require [clojure.java.io :as io]
            [clojure.test :refer :all]
            [clojure.repl :refer (apropos dir doc find-doc pst source)]))


(defn synchronized-with-source?
  "returns true if targer has same last modified date as source"
  [target source]
  (println [(.lastModified (io/file source)) (.lastModified (io/file target))])
  (>= (.lastModified (io/file target)) (.lastModified (io/file source)) ))


(def directory (clojure.java.io/file "sample"))
(def files (file-seq directory))


(defn synchronized-folder-source?
  "return the more recent time modification (greater lastModified value) of folder files. Returns long type"
  [source-folder time-target]
  (let [d (io/file source-folder) ]
    (doseq [f (.listFiles d)]
      (if (.isDirectory f)
        (do
          ;; (println "dir: " (str source-folder "/"(.getName f)))
          (synchronized-folder-source? (str f "/" (str source-folder "/"(.getName f))) time-target))
        (do
          (println "file: " (str source-folder "/"(.getName f)))
          (when )
            )
        ))))

(defn is-synchronized? [src-folder target-path]
  (synchronized-folder-source? src-folder (.lastModified (io/file target-path))))

(is-synchronized? "sample/example" "sample/.DS_Store" )



(assert (synchronized-with-source? "sample/.DS_Store" "sample/example/project.clj"))
(assert (synchronized-with-source? "sample/example/less/sample.less" "sample/example/project.clj"))
