# Nimble -- 一个简单的游戏服务器

## 启动

1. 现在本地启动mongodb和redis，建议直接用docker启动，方便快捷，不用修改默认的端口配置。
2. 运行NimbleApplication即可启动，默认监听8080端口。

## 开发

1. 本项目使用了lombok，需要安装lombok插件(IDEA好像是自带这个插件)，否则会报错，lombok能提供很多便利也很简单，建议了解一下。
2. 可以参考UserController，这是个简单的例子，简单开发参考updateProfile这个api即可。
3. 在application.yml中可以定义自定义的配置，参考NumericProperties类，另外mongodb和redis的配置都可以在这里修改。
4. springboot可以通过profile来切换配置，如果需要在不同环境使用不同配置可以了解一下。
5. 如果必要可以写单测，参考UserControllerTest。