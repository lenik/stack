package com.bee32.plover.inject;

/**
 * 托管/可被注入的组件。
 * <p>
 * Plover 框架的依赖注入和Spring DI、 Google Guice以及其它常见的DI都不同， 框架既不要求 XML 配置，也不对任何标注类进行扫描。
 *
 * （这里扫描指的是类似职责链一样的行为，而不是对整个 JAR 包进行扫描，但是尽管如此，扫描还是很低效。）
 * <p>
 * 为什么呢？因为处理这些东西太慢了！ 想想看、JavaEE容器总是需要定期重启、并且前端已经提供了缓存优化， 因此，即使Spring DI 提供了标注类的缓存，也不会有多大的效益。
 * 而且，另一方面如果我们用 classwords 配置类装载器，或者在 EE 上启用 OSGi 框架，类似的扫描行为将会重复不止一次。
 *
 * 如果每次都要处理标注，加起来就是不小的开销。
 * <p>
 * 另一方面，Spring DI 之类的容器并不为定制作用域提供便利。 你只能说我这个类是 Request 作用域的，但是你无法针对 Request 作用域为类行为提供一定的重用。 当
 * Request 重新分配后，你不知道什么时候应该清理，等等。
 * <p>
 * Plover 组件要求你显示的实现 {@link IAware} 接口。 对，忘了什么 POJO，你必须显示定义你的类为 Plover 组件，判断派生关系比搜索标注更快。 如果你希望和
 * Plover 脱离关系，你就必须为你的愚蠢的 POJO 实现一个附加的适配器，并结合 Plover 提供的桥接工具，以便使它们能够被 Plover 容器正确注入。
 *
 * <p>
 * <strong>嵌套关系</strong>
 * <p>
 * 如果 A 含有 B，并且 B is-a IAware，那么 A.enter() 中应：完成 A 自身的配置，然后调用 B.enter()。
 *
 * 同理 A.leave() 中应：调用 B.leave()，然后完成 A 的环境清理事项。
 *
 * @see &#64;Component
 * @see com.bee32.plover.inject.bridge.JSR330Bridge
 * @see com.bee32.plover.inject.bridge.PlexusComponentBridge
 * @see com.bee32.plover.inject.bridge.SpringDIBridge
 */
public interface IAware {

    /**
     * 组件被添加到容器，此时组件应该和容器交互，以便获得所有必备的信息和资源。
     *
     * 组件可能从一个容器转移到另一个容器。在进入容器之后，组件可能被并发调用。但进入之前，应该只能被一个线程调用 {@link #enter(IContainer)}。
     *
     * 组件不能以交叉的结构进入和离开容器，即：如果组件进入容器1，又进入容器2，则必须以相反的次序离开容器。
     *
     * @param container
     *            容器对象，可以保存下来以便将来在具体的方法中使用。
     */
    void enter(IContainer container)
            throws ContextException;

    /**
     * 组件从容器中移除，此时组件应该清理残留的东西。
     *
     * 如果组件在前面的调用中保存了容器引用，那么必须在本方法中清除该引用，以便使没用的容器被垃圾回收。
     *
     * @param container
     *            容器对象，必须和最近一次调用 {@link #enter(IContainer)} 的容器对象相同。如果你之前已经保存了 {@link IContainer}
     *            的引用，那么你可以放心的忽略该参数。
     */
    void leave(IContainer container)
            throws ContextException;

}
