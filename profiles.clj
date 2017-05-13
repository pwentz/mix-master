{:spec {:env {:database-url "postgresql://localhost:5432/mix-master-test"}}
 :dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                      [speclj "3.3.2"]
                      [ring/ring-mock "0.3.0"]]
       :env {:database-url "postgresql://localhost:5432/mix-master"}}}
