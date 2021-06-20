(ns golfd.helpers
  (:require [clojure.string :as str]))

(defn read-until [str chr]
  [(->> str
       (take-while (partial not= chr))
       str/join)
   (->> str
        (drop-while (partial not= chr))
        (drop 1)
        str/join)])
