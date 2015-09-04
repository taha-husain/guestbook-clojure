(ns guestbook.routes.login
  (:require [guestbook.layout :as layout]
            [compojure.core :refer [defroutes GET POST DELETE ANY]]
            [ring.util.http-response :refer [ok]]
            [clojure.java.io :as io]
            [guestbook.db.core :as db]
            [bouncer.core :as b]
            [bouncer.validators :as v]
            [ring.util.response :refer [redirect response status]]
            [taoensso.timbre :as timbre]
            [cemerick.friend :as friend]))


(defn login-page [{:keys [flash]}]
  (layout/render
    "login.html"))

(defn login! [{:keys [params]}]
  (timbre/info params))

(defroutes login-routes
  (GET "/login" request (login-page request))
  (GET "/admin" request (friend/authorize #{::admin}
                      "admin.html"))
  (friend/logout (ANY "/logout" request (ring.util.response/redirect "/")))
  )
