(ns golfd.operators
  (:require [clojure.string :as str]))

(defn- vec-remove [pos coll]
  (into (subvec coll 0 pos) (subvec coll (inc pos))))

(defn- make-maths [func]
  (fn [stack]
    (conj
     (vec (drop-last 2 stack))
     (apply func (map #(Integer. %)
                      (take-last 2 stack))))))

(def op-add (make-maths +))
(def op-sub (make-maths -))
(def op-mult (make-maths *))
(def op-div (make-maths /))

(def op-inc #(op-add (conj % "1")))
(def op-dec #(op-sub (conj % "1")))

(defn op-inc-all [stack] (map #(inc (Integer. %)) stack))
(defn op-dec-all [stack] (map #(dec (Integer. %)) stack))

(defn op-sort-stack [stack]
  (vec (sort stack)))

(defn op-move-to-top [stack]
  (let [index (Integer. (last stack))]
    (conj (vec-remove index (vec (butlast stack)))
          (stack index))))

(defn op-sort-numbers [stack]
  (->> stack
       (map #(Integer. %))
       sort
       vec))

(defn op-print [stack]
  (println (last stack))
  (vec (drop-last stack)))

(defn op-print-all [stack]
  (println (apply str stack))
  [])

(defn op-print-spaced [stack]
  (println (str/join " " stack)))

(defn op-input [stack]
  (conj stack (read-line)))

(defn op-spread-last [stack]
  (vec (concat (butlast stack) (str/split (last stack) #" "))))

(def operators {\+ op-add
                \p op-print
                \P op-print-all
                \Ṗ op-print-spaced
                \. op-input
                \- op-sub
                \* op-mult
                \/ op-div
                \s op-sort-stack
                \S op-sort-numbers
                \i op-inc
                \d op-dec
                \I op-inc-all
                \D op-dec-all
                \~ op-spread-last
                \> op-move-to-top})
