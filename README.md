# resources_cn


> 对于IntelliJ IDEA 2017.2.4版本进行中文化,全平台支持.现在已基本完成汉化.

| 版本     | 是否支持    | 描述    |
| -       | :-:       |  :-:   |
|2017.2.3 | 支持       |        |
|2017.2.4 |  支持    |         |


新版的的idea逐渐抽取出资源,放在 '$IDEA_HOME$/lib/resources_en.jar' 中,这对进行中文化提供了优雅的方案.

本项目使用maven结构,按照官方的本地化指南进行翻译.可以自行编译或直接使用编译好的jar.
编译好的jar放在release目录下,下载需要的bao后,重命名为'resources_cn.jar',
再把resources_cn.jar放到 '$IDEA_HOME$/lib/resources_cn.jar' 位置,再次启动即可使用中文版的idea.

待完成本版本后,会跟随IDEA企业版的升级,增加新版本的支持.
本人日常发现翻译不准确的地方,会进行优化.
如果你使用中,发现一些语言,提示语存在问题的希望能提交问题,帮助我,帮助中文版idea变的更好

JavaErrorMessages.properties部分本地化,剩余200条.将在1-2天内完成.
关于ant的内容暂不翻译.