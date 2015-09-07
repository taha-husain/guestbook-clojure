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


(defn signup-page [{:keys [flash]}]
  (layout/render
    "signup.html"))

(defn validate-user [params]
  (first
    (b/validate
      params
      :username v/required
      :password [v/required [v/min-count 8]]
      :confirm-password [v/required [v/min-count 8]]
      :first v/required
      :last v/required)))

(defn register! [{:keys [params]}]
  (timbre/info "inside register! method")
  (timbre/info params)
  (if-let [errors (validate-user params)]
    (-> (redirect "/signup")
        (assoc :flash (assoc params :errors errors)))
    (do
      (def user-id (db/create-user! params))
      (redirect (str "/user/" user-id)))))

(defn login-page [{:keys [flash]}]
  (layout/render
    "login.html"))

;;(defn set-user! [user-id {session :session}]
(defn set-user! [{:keys [params flash]
                  session :session}]
  (timbre/info (:id params))
  (timbre/info session)
  (timbre/info flash)
  (timbre/info (db/find-user params))
  ;;(-> (str "User set to id" user-id)
  ;;    response
  (assoc session :user (:id params))
  (timbre/info session)
  (layout/render
   "users/show.html"
   (merge {:user (db/find-user params)}
          (select-keys flash [:first_name :last_name :username :errors :session]))))

(defroutes login-routes
  (GET "/signup" request (signup-page request))
  (GET "/login" request (login-page request))
  (POST "/register" request (register! request))
  (GET "/admin" request (friend/authorize #{::admin}
                      "admin.html"))
  ;;(GET "/user/:id" [id :as req] (set-user! id req))
  (GET "/user/:id" request (set-user! request))
  (friend/logout (ANY "/logout" request (ring.util.response/redirect "/"))))
