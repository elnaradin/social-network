# Конфигурирование CI/CD (gitlab-runner) в Minikube

1. Установить Kubernetes и Minikube по [гайду](../kibernates-minikube/README.md)
2. Запустить Minikube
    ```shell
    minikube start
    ```
3. Также, для удобства, можно запустить дашборд
    ```shell
   minikube dashboard
    ```
4. Установить Helm.
    1. [Скачать тут](https://helm.sh/docs/intro/install/).
    2. Распаковать архив в удобной для вас директории.
    3. Добавить переменную среды.
        1. Нажимаем Этот компьютер (правая кнопка мыши) -> Свойства -> Дополнительные параметры системы -> Вкладка
           дополнительно -> Переменные среды.
        2. В переменную Path добавить путь к директории.
5. Добавить репозиторий gitlab'а в helm и обновить индексы
    ```shell
    helm repo add gitlab https://charts.gitlab.io/
    helm repo update
    ```
6. Скачать в текущую директорию файл `values.yaml`. Его можно взять
   из [официального репозитория](https://gitlab.com/gitlab-org/charts/gitlab-runner/blob/main/values.yaml), либо
   же [приложенный к проекту]()
7. Установить GitLab в кластере Minikube, используя Helm
    ```shell
    helm install gitlab-runner -n gitlab -f .\values.yaml gitlab/gitlab-runner
    ```
8. Обновить конфигурацию раннера. В данной команде необходимо обратить внимание на 2 аргумента:
    1. `gitlabUrl=https://gitlab.skillbox.ru/`, где `https://gitlab.skillbox.ru/` - это домен используемого gitlab'а. В
       нашем случае он именно такой.
    2. `runnerRegistrationToken=${TOKEN}`, где вместо `${TOKEN}` необходимо подставить регистрационный токен, который
       берется в gitlab. Нужно зайти в репозиторий проекта нажать `Settings -> CI/CD -> Runners` и в
       колонке `Specific runners` будет указан заветный токен.
    ```shell
    helm upgrade gitlab-runner --set gitlabUrl=https://gitlab.skillbox.ru/,runnerRegistrationToken=${TOKEN} gitlab/gitlab-runner
    ```
9. Зарегистрировать раннер. Здесь так же как и в предыдущем шаге следует обратить внимание на опции `--url`
   и `--registration-token`. Они будут те же, что и в предыдущем шаге.
    ```shell
    gitlab-runner register --non-interactive --url "https://gitlab.skillbox.ru/" --registration-token "${TOKEN}" --executor kubernetes --kubernetes-namespace "gitlab" --kubernetes-image "docker:latest" --kubernetes-privileged=true --kubernetes-namespace-overwrite-existing=true --kubernetes-service-account=gitlab-runner --kubernetes-host "https://$(minikube ip):8443" --kubernetes-helper-image="alpine:latest" --tag-list "kubernetes,gitlab-runner" --run-untagged --locked=false
    ```
