(ns ring.middleware.google-site-verification
  "This middleware will return a site verification file for each of the given
  filenames."
  (:require [ring.util.response :refer [content-type response]]))

(defn- get? [req]
  (= (:request-method req) :get))

(defn- route? [routes req]
  (routes (:uri req)))

(defn filenames->routes [filenames]
  (->> filenames (map #(str "/" %)) set))

(defn- verification-body [req]
  (str "google-site-verification: " (subs (:uri req) 1)))

(defn- verification-response [req]
  (-> req
      verification-body
      response
      (content-type "text/html")))

(defn wrap-google-site-verification
  "Adds a route for each verification filename to enable site verification with
  Google."
  [handler {:keys [filenames]}]
  {:pre [(seq filenames)]}
  (let [routes (filenames->routes filenames)
        gate #(and (get? %) (route? routes %))]
    (fn google-site-verification-handler [req]
      (if (gate req)
        (verification-response req)
        (handler req)))))
