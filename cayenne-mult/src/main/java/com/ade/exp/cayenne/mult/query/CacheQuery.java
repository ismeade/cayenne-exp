package com.ade.exp.cayenne.mult.query;

/**
 * 缓存查询结果
 * 了缓存单个对象外，Cayenne还能缓存查询结果。就像对象缓存一样，实际的缓存发生在后台，
 * 用户仅需要去声明预期的缓存政策，无论是在代码中或通过Modeler声明都行：
 * electQuery query = new SelectQuery(Artist.class,  some qualifier )
 * query.setCacheStrategy(QueryCacheStrategy.LOCAL_CACHE);
 * query.setCacheGroups("artists", "recent_exhibits");
 * List<Artist> artists = context.performQuery(query);
 * 这个查询首次运行，如果以后这个查询（或其它具有相同参数的查询）再次运行，将从缓存中快
 * 速返回结果，而不执行数据库访问。
 * 接下来我们将讨论缓存策略和缓存缓存组的含义。
 *
 * 缓存策略
 * 下面的Cayenne栈结构中，查询缓存能被附加到一个ObjectContext或一个DataDomain上。在
 * 前面的案例中，它没有在语境（context）之间共享，因此被叫做"local（本地）"，在后面的
 * 案例中，缓存在一个给定的栈内被所有语境（context）共享，这样的缓存被称作"shared（共
 * 享）"。访问local缓存会更快一些，但需要权衡的是，如果有很多语境（context）使用local
 * 缓存，这要花费更多的内存，并且它也会更经常的在多语境（context）环境下命中数据库。
 * 正式的缓存策略定义在 QueryCacheStrategy 枚举中，枚举值包括NO_CACHE、LOCAL_CACHE、
 * LOCAL_CACHE_REFRESH、SHARED_CACHE、SHARED_CACHE_REFRESH。策略以"_REFRESH"结尾，
 * 实际上会运行数据库查询，但之后会缓存查询结果。这些都是不经常使用的，因为从查询代码（如
 * 下所讨论的）中分享缓存刷新逻辑是一个好主意。因此最常见的缓存策略是LOCAL_CACHE 和
 * SHARED_CACHE。
 *
 * 缓存组
 * 如上面的例子所示，每个查询能带有一个或多个"缓存组"。缓存组是符号名，一个用户为定义一
 * 个通用缓存政策的目的而分派一定的查询去组合它们。这些缓存组只是一些字符串，可以做为一
 * 个方法去表明当你想让数据被刷新时去刷新它。
 *
 * 查询缓存（QueryCache）管理
 * 查询缓存提供商通过org.apache.cayenne.cache.QueryCacheFactory被安装。工厂可以为
 * DataDomain而配置在Modeler中，或设置在代码中。Cayenne提供一些框架之外的工厂在大多
 * 数情况下应该是足够的。
 *
 * 简单缓存提供商 - LRUMap
 * 默认的工厂是 org.apache.cayenne.cache.MapQueryCacheFactory，它是一个简单的LRU映
 * 射。它们自己的缓存条目永远不会过期，但当缓存100%有能力操作时，不常被使用的条目将最后被
 * 置换。 查询缓存最简单的形式是，要求用户去直接实现自己的 org.apache.cayenne.cache.QueryCache
 * 接口的"active"失效策略访问方法。
 *
 * 高级缓存提供者 - OSCache
 * 一个更高级的缓存提供商可以通过 org.apache.cayenne.cache.OSQueryCacheFactory 被安装。
 * 实际上这个缓存是使用由OpenSymphony提供的 OSCache，所以 OSCache 相关jar文件 需要被加
 * 到应用程序的classpath中。缓存配置文件能用Cayenne之外的工具（例如，一个文本编辑器或
 * Eclipse）创建，并命名为 "oscache.properties"。这个文件应被放置到应用程序的classpath
 * 路径中。这个文件的格式遵循一个规范的java属性文件。这里有一个例子展示它的一些能力及演示怎
 * 样为每个缓存组配置缓存政策。更多标准属性的讨论请看 OSCache 文档。
 *
 * # OSCache standard configuration:
 * #cache.memory=true
 * #cache.blocking=false
 * cache.capacity=5000
 * cache.algorithm=com.opensymphony.oscache.base.algorithm.LRUCache

 * # Cayenne specific properties:

 * # Default refresh period in seconds
 * # (used for all cache groups not explicitly overriding it here)
 * cayenne.default.refresh = 60

 * # Default expiry specified as cron expressions per
 * #    http://www.opensymphony.com/oscache/wiki/Cron%20Expressions.html
 * # expire entries every hour on the 10's minute
 * cayenne.default.cron = 10 * * * *

 * # Same parameters can be defined per cache group, overriding the defaults
 * # cache group name is specified inside the property key. E.g. "artists" below
 * # is a cache group name
 * cayenne.group.artists.refresh = 120
 * cayenne.group.artists.cron = 10 1 * * *
 * 在这个例子中所展示的，你可以指定一个条目被创建以来的固定的时限或一个类似cron（定时
 * 任务）的缓存失效表达式。两者都可以被指定为条目缓存和/或一个或多个缓存组。如你所见，
 * 当OSCache被使用时，缓存组才成为真正有用的。你不需要在代码中指明缓存管理。它是100%
 * 完全的声明。当OSCache的可能潜在包含了数千个条目的组过期时，它本身是非常有效率的。
 * 而不是为所有需要使之过期的条目扫描条目缓存，它简单的标记条目组已过期，因此该方案的
 * 性能是相当好的。
 *
 * 即时缓存失效
 * 上述OSCache配置是非常灵活的，然而即使是这个设置，它也没有指示一个重要的场景－缓存
 * 组在需要时失效。这是经常需要的，当一个应用程序中的特定对象被更新，潜在渲染的一大堆
 * 先前缓存的查询结果则无效。
 * 第一步是要发现一个明确的缓存更新应被触发的地方。通常这是通过post commit callbacks
 * 来完成的。一个回调或侦听方法就能获得QueryCache实例并为所有需要设置为失效的组调用
 * "removeGroup"。 这就是小心选择查询缓存组的回报。例如，我们可能标记所有查询以
 * "classics"抓取已过时的artists和以"modern"抓取所有现代的artists。Artist实体可以
 * 映射一个回调方法，类似这个：
 * void onCommit() {
 * QueryCache cache = ((BaseContext) getObjectContext()).getQueryCache();
 * if(isModern()) {
 * cache.removeGroup("modern");
 * }
 * else {
 * cache.removeGroup("classic");
 * }
 * }
 * }这将确保后续"performQuery"调用将不使用陈旧数据，并缓存取得懒刷新。

 * 上述方法适用于LRU映射和OSCache。正如所料，OSCache在这方面给了我们额外的能力。正如我
 * 们已经提到的，在Cayenne栈之间（可能是远程的）发送对象变化通知在大多数时候是低效的。
 * 使用OSCache却不是这样的。它可以用简单的缓存组名称来发送远程失效通知，所以它们创建非
 * 常小的网络开销。在接收端也是懒处理，因此不会立即需要额外的CPU周期去为应用程序处理一个
 * 事件。OSCache支持JavaGroups和JMS通知。为了启用它，需要添加以下每个OSCache 集群指
 * 南条目中的一个到"oscache.properties"中：

 * cache.event.listeners=com.opensymphony.oscache.plugins.clustersupport.JMSBroadcastingListener
 * # other JMS paramaters go here...cache.event.listeners=com.opensymphony.oscache.plugins.clustersupport.JavaGroupsBroadcastingListener
 * # other JavaGroups paramaters go here...查询缓存结论
 * 始终使用缓存策略的后果和加大ache组集的合理性使许多应用程序不再以明确的规定缓存抓取到
 * 的列表。重新运行一个以缓存策略(特别是 "LOCAL_CACHE")之一成为一个极快速的操作的查询。
 * 也使代码变得更干净，其状态是储存在Cayenne中的，而不在应用程序代码中。当应用程序需要
 * 在请求之间访问相同的列表时，一个可能的异常从这个规则中抛出，不管它是否已经变味了。
 *
 * Created by LiYang on 2016/7/14.
 */
public class CacheQuery {
}
