(ns golfd.core
  (:gen-class)
  (:require [clojure.string :as str])
  (:require [golfd.operators :as ops]))

(defn tokenize [expr]
  (->> (str/split expr #"'")
       (map-indexed #(if (even? %1) (seq %2) [%2]))
       (filter (partial not= nil))
       (apply concat)))

(defn evaluate [expr]
  (loop [[t & ts] (tokenize expr)
         stack []]
    (when t
      (if (char? t)
        (recur ts ((ops/operators t) stack))
        (recur ts (conj stack t))))))

(defn -main [& args]
  (if (first args)
    (doall (map evaluate (str/split-lines (slurp (first args)))))
    (while true
      (print "golfd> ")
      (flush)
      (evaluate (read-line)))))
