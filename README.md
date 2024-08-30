# flink-pg
[# local debugging](https://www.galiglobal.com/blog/2021/20210130-Flink-setup.html)
helm repo add flink-kubernetes-operator-1.7.0 https://archive.apache.org/dist/flink/flink-kubernetes-operator-1.7.0/

helm search repo flink-kubernetes-operator-1.7.0

kubectl config set-context flink-operator --cluster=minikube --user=minikube --namespace=flink-operator
kubectl config set-context flink-jobs --cluster=minikube --user=minikube --namespace=flink-jobs

helm -n flink-operator install -f values.yaml flink-kubernetes-operator flink-kubernetes-operator-1.7.0/flink-kubernetes-operator --set webhook.create=false

kubectl apply -f job.yaml -n flink-jobs