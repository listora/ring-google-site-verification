(ns ring.middleware.google-site-verification-test
  (:require [clojure.test :refer :all]
            [ring.middleware.google-site-verification :refer :all]
            [ring.mock.request :refer [request]]
            [ring.util.response :refer [get-header]]))

(def ^:private default-response
  {:status 200 :headers {} :body "Body"})

(defn- make-handler [options]
  (wrap-google-site-verification
   (constantly default-response)
   options))

(defn- verification-body [filename]
  (str "google-site-verification: " filename))

(deftest ^:unit test-wrap-google-site-verification-with-one-filename
  (let [filename "googled123123.html"
        handler (make-handler {:filenames [filename]})]

    (testing "when requesting the site verification file"
      (let [response (handler (request :get (str "/" filename)))]
        (is (= (:status response) 200))
        (is (= (get-header response "content-type") "text/html"))
        (is (= (:body response) (verification-body filename)))))

    (testing "when requesting any other route"
      (let [response (handler (request :get "/"))]
        (is (= (:status response) 200))
        (is (= (:body response) "Body"))))))

(deftest ^:unit test-wrap-google-site-verification-with-many-filenames
  (let [filenames ["googled000.html" "googled111.html" "googled222.html"]
        handler (make-handler {:filenames filenames})]

    (doseq [filename filenames]
      (testing "when requesting the site verification file"
        (let [response (handler (request :get (str "/" filename)))]
          (is (= (:status response) 200))
          (is (= (get-header response "content-type") "text/html"))
          (is (= (:body response) (verification-body filename)))))

      (testing "when requesting any other route"
        (let [response (handler (request :get "/"))]
          (is (= (:status response) 200))
          (is (= (:body response) "Body")))))))
