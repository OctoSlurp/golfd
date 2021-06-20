(ns golfd.core
  (:gen-class)
  (:require [clojure.string :as str])
  (:require [golfd.operators :as ops])
  (:require [golfd.helpers :as helpers]))

(defn tokenize [expr]
  (->> (str/split expr #"'")
       (map-indexed #(if (even? %1) (seq %2) [%2]))
       (filter (partial not= nil))
       (apply concat)))

(defn tokenize [expr]
  (loop [[chr & rest] expr
         tokens []]
    (if chr
      (case chr
        \' (let [[t rest] (helpers/read-until rest \')]
             (recur rest (conj tokens [:group t])))
        \[ (let [[t rest] (helpers/read-until rest \])]
             (recur rest (conj tokens [:loop (tokenize t)])))
        (recur rest (conj tokens [:operator chr])))
      tokens)))

(defn evaluate [tokens]
  (loop [[t & ts] tokens
         stack []]
    (when t
      (case (first t)
        :operator (recur ts ((ops/operators (second t)) stack))
        :group (recur ts (conj stack (second t)))
        :loop (recur ts stack)))))

(defn -main [& args]
  (if (first args)
    (doall (map evaluate (tokenize
                          (str/split-lines (slurp (first args))))))
    (while true
      (print "golfd> ")
      (flush)
      (evaluate (read-line)))))
