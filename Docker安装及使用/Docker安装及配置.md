# Docker安装、配置及使用

##  安装Docker

### 卸载旧版本

* 清理资源

> ```sh
> docker system prune --all --volumes
> 
> # 输出如下
> WARNING! This will remove:
>   - all stopped containers
>   - all networks not used by at least one container
>   - all volumes not used by at least one container
>   - all images without at least one container associated to them
>   - all build cache
> 
> Are you sure you want to continue? [y/N]
> ```

* 卸载

```sh
apt remove docker.io
```

### 安装新版本

```sh
apt remove docker.io
```

### 验证安装

```sh
docker version

# 输出如下
Client:
 Version:           19.03.6
 API version:       1.40
 Go version:        go1.12.17
 Git commit:        369ce74a3c
 Built:             Fri Feb 28 23:45:43 2020
 OS/Arch:           linux/amd64
 Experimental:      false

Server:
 Engine:
  Version:          19.03.6
  API version:      1.40 (minimum version 1.12)
  Go version:       go1.12.17
  Git commit:       369ce74a3c
  Built:            Wed Feb 19 01:06:16 2020
  OS/Arch:          linux/amd64
  Experimental:     false
 containerd:
  Version:          1.3.3-0ubuntu1~18.04.2
  GitCommit:        
 runc:
  Version:          spec: 1.0.1-dev
  GitCommit:        
 docker-init:
  Version:          0.18.0
  GitCommit:
```

### 配置加速器

> **注意：** 国内镜像加速器可能会很卡，请替换成你自己阿里云镜像加速器，地址如：`https://yourself.mirror.aliyuncs.com`，在阿里云控制台的 **容器镜像服务 -> 镜像加速器** 菜单中可以找到

在 `/etc/docker/daemon.json` 中写入如下内容（以下配置修改 `cgroup` 驱动为 `systemd`，满足 K8S 建议）

```json
{
  "exec-opts": ["native.cgroupdriver=systemd"],
  "log-driver": "json-file",
  "log-opts": {
    "max-size": "100m"
  },
  "registry-mirrors": [
    "https://k7da99jp.mirror.aliyuncs.com/",
    "https://dockerhub.azk8s.cn",
    "https://registry.docker-cn.com"
  ],
  "storage-driver": "overlay2"
}
```

- 验证配置

```sh
docker info

# 看到这句即表示配置成功
Registry Mirrors:
  https://k7da99jp.mirror.aliyuncs.com/
  https://dockerhub.azk8s.cn/
  https://registry.docker-cn.com/
```

``` sh
# 若出现以下错误提示说明docker不属于当前用户
Got permission denied while trying to connect to the Docker daemon socket

# 使用以下命令可以解决
sudo groupadd docker #添加docker用户组
sudo gpasswd -a $XXX docker #检测当前用户是否已经在docker用户组中，其中XXX为用户名，例如我的，ubuntu
sudo gpasswd -a $USER docker #将当前用户添加至docker用户组
newgrp docker #更新docker用户组
```

## 安装Docker Compose

Linux 上我们可以从 Github 上下载它的二进制包来使用，最新发行的版本地址：https://github.com/docker/compose/releases。

### 下载 Docker Compose 的当前稳定版本：

```sh
$ sudo curl -L "https://github.com/docker/compose/releases/download/1.24.1/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
```

若从GitHub下载太慢可以使用下边命令下载

``` sh
sudo curl -L https://get.daocloud.io/docker/compose/releases/download/1.25.1/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose
```



要安装其他版本的 Compose，请替换 1.24.1。

### 将可执行权限应用于二进制文件：

```
$ sudo chmod +x /usr/local/bin/docker-compose
```

### 创建软链：

```
$ sudo ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose
```

### 测试是否安装成功：

```
$ docker-compose --version
cker-compose version 1.24.1, build 4667896b
```

## 使用Docker Compose

* 编写Dockerfile：docker-compose.yml

  ```yaml
  version: '3.1'
  services:
    db:
      image: mysql:8.0.20
      restart: always
      container_name: mysql
      environment:
        MYSQL_ROOT_PASSWORD: 123456
      command:
        --default-authentication-plugin=mysql_native_password
        --character-set-server=utf8mb4
        --collation-server=utf8mb4_general_ci
        --explicit_defaults_for_timestamp=true
        --lower_case_table_names=1
      ports:
        - 3306:3306
      volumes:
        - ./data:/var/lib/mysql
  ```

* 运行Docker容器

  ``` sh
  docker-compose up
  ```

  ``` sh
  docker-compose up -d
  ```

  使用 -d 参数进行后台运行