# flink-pg
[# local debugging](https://www.galiglobal.com/blog/2021/20210130-Flink-setup.html)
helm repo add flink-kubernetes-operator-1.7.0 https://archive.apache.org/dist/flink/flink-kubernetes-operator-1.7.0/


minikube start

helm search repo flink-kubernetes-operator-1.7.0

kubectl config set-context flink-operator --cluster=minikube --user=minikube --namespace=flink-operator
kubectl config set-context flink-jobs --cluster=minikube --user=minikube --namespace=flink-jobs

helm -n flink-operator install -f values.yaml flink-kubernetes-operator flink-kubernetes-operator-1.7.0/flink-kubernetes-operator --set webhook.create=false

kubectl apply -f job.yaml -n flink-jobs


##### to view flink dashboard
minikube service basic-example-rest --url -n flink-jobs 

###### working image
https://hub.docker.com/layers/manishankarbalu/flink-sales-job/v1.0.1/images/sha256-82bdd50156a29942d531b1083d60a6817982dfcacfda43d6d5eb62afee39706d?context=repo