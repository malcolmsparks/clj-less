(ns lein-less.less.compiler
  (:refer-clojure :exclude [compile])
  (:require
   [clojure.java.io :as io]
   [lein-less.less.nio :as nio]
   [lein-less.less.engine :as engine])
  (:import
   (java.nio.file Path)
   (java.io IOException)
   (javax.script ScriptEngineManager ScriptEngine ScriptContext)
   (lein_less.less LessError)))

(def version "1.7.2")
(def less-js (format "lein_less/less/less-rhino-%s.js" version))
(def lessc-js (format "lein_less/less/lessc.js"))

(defn initialise
  "Load less compiler resources required to compile less files to css. Must be called before invoking compile."
  []
  (engine/eval! (io/resource less-js) less-js)
  (engine/eval! (io/resource lessc-js) lessc-js))

(defn compile-resource
  "Compile a single less resource."
  [src dst]
  (nio/create-directories (nio/parent dst))
  (engine/eval! (format "lessc.compile('%s', '%s');" src (nio/absolute dst))))

(defn compile-project
  "Take a sequence of src/dst pairs, compiles each pair."
  [units on-error]
  (doseq [{:keys [^Path src ^Path dst]} units]
    (println "Please wait, compiling: "(format "%s => %s" (nio/fstr  src) (nio/fstr  dst)))
    (try
      (compile-resource src dst)
      (catch LessError ex (on-error ex)))))
