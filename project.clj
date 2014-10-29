(defproject listora/ring-google-site-verification "0.1.0"
  :description "Ring middleware for returning site verification files"
  :url "https://github.com/listora/ring-google-site-verification"
  :license {:name "The MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [ring/ring-core "1.3.0-RC1"]]
  :deploy-repositories [["releases" :clojars]]
  :plugins [[codox "0.8.5"]]
  :codox {:project {:name "Ring-Google-Site-Verification"}}
  :profiles {:dev {:dependencies [[ring-mock "0.1.5"]]}})
