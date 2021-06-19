(ns golfd.operators)

(defn op-add [stack]
  (conj (vec (drop-last 2 stack)) (apply + (map #(Integer. %) (take-last 2 stack)))))

(defn op-print [stack]
  (println (last stack))
  (vec (drop-last stack)))

(defn op-printall [stack]
  (println (apply str stack))
  [])

(defn op-input [stack]
  (conj stack (read-line)))

(def operators {\+ op-add
                \p op-print
                \P op-printall
                \. op-input})
