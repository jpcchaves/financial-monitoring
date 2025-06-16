import os
import threading

threads = []
modules_threads = []
applications_to_build = ["service-registry", "api-gateway", "fraud-service", "notification-service", "transaction-service", "user-service"]
modules_to_build = ["common-lib"]

def stop_and_remove_containers():
  print("Stopping and removing containers...")
  os.system("cd deploy/docker && docker-compose down")
  containers = os.popen('docker ps -aq').read().split('\n')
  containers.remove('')
  if len(containers) > 0:
      print("There are still {} containers created".format(containers))
      for container in containers:
          print("Stopping container {}".format(container))
          os.system("cd deploy/docker && docker container stop {}".format(container))
      os.system("docker container prune -f")

def build_application(appName):
  threads.append(appName)
  print(f"Building application {appName}")
  os.system(f"cd {appName} && ./gradlew clean build -x test")
  print(f"Application {appName} finished building!")
  threads.remove(appName)

def build_all_applications():
  for app in applications_to_build:
    threading.Thread(target=build_application, args={app}).start()

def build_module(moduleName):
  modules_threads.append(moduleName)
  print(f"Building module {moduleName}")
  os.system(f"cd {moduleName} && ./gradlew clean build -x test")
  print(f"Module {moduleName} finished building!")
  modules_threads.remove(moduleName)

def build_modules():
  for module in modules_to_build:
    threading.Thread(target=build_module, args={module}).start()

def docker_compose_up():
  os.system("cd deploy/docker && docker-compose up -d --build")

if __name__ == "__main__":
  print("Build pipeline started!")

  build_modules()
  while len(modules_threads) > 0:
    pass

  stop_and_remove_containers()
  build_all_applications()

  while len(threads) > 0:
    pass

  threading.Thread(target=docker_compose_up).start()

  print("Pipeline finished!")
