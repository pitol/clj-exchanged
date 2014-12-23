(ns clj-exchanged.routes.home
  (:require [compojure.core :refer :all]
            [liberator.core :refer [defresource]]
            [clj-exchanged.views.layout :as layout]))

(defn currency-repr [currency-code media-type]
  (condp = media-type
    "text/plain" (str "Don't know anything about " currency-code)
    "text/html"  (str "<h1>Currency:</h1><p>Don't know anything about " currency-code "</p>")
    (str "Don't know anything about " currency-code " with media-type " media-type)))

(defresource currency [currency-code]
  :available-media-types ["text/plain" "text/html"]
  :handle-ok (fn [context] (currency-repr currency-code 
                                          (get-in context [:representation :media-type]))))

(defn home []
  (layout/common [:h1 "Hello World!"]))

(defroutes home-routes
  (ANY "/:currency-code" [currency-code] (currency currency-code))
  (GET "/" [] (home)))
