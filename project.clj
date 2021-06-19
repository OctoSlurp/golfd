(defproject golfd "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.10.1"]]
  :main ^:skip-aot golfd.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
